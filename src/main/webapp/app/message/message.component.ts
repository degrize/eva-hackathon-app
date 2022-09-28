import { Component, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { IMessage } from '../entities/message/message.model';
import { MessageService } from '../entities/message/service/message.service';
import { DataUtils, FileLoadError } from '../core/util/data-util.service';
import { EventManager, EventWithContent } from '../core/util/event-manager.service';
import { AlertError } from '../shared/alert/alert-error.model';
import { AnnonceService } from '../entities/annonce/service/annonce.service';
import { ActivatedRoute } from '@angular/router';
import { MessageFormGroup, MessageFormService } from '../entities/message/update/message-form.service';
import { Observable, Subject } from 'rxjs';
import { finalize, takeUntil } from 'rxjs/operators';
import { IAnnonce } from '../entities/annonce/annonce.model';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';
import { Account } from '../core/auth/account.model';
import { AccountService } from '../core/auth/account.service';
import { MandataireDelegateurService } from '../entities/mandataire-delegateur/service/mandataire-delegateur.service';

@Component({
  selector: 'jhi-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss'],
})
export class MessageComponent implements OnInit {
  params: any;
  annonceId: any;
  annonce: IAnnonce | undefined;
  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;
  private readonly destroy$ = new Subject<void>();

  files: File[] = [];
  messages?: IMessage[];
  isSaving = false;

  closeResult: string | undefined;

  editForm: MessageFormGroup = this.messageFormService.createMessageFormGroup();

  constructor(
    private modalService: NgbModal,
    private messageService: MessageService,
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected annonceService: AnnonceService,
    protected activatedRoute: ActivatedRoute,
    protected messageFormService: MessageFormService,
    public activeModal: NgbActiveModal,
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService
  ) {}

  open(content: any, type: string, modalDimension: string) {
    if (modalDimension === 'sm' && type === 'modal_mini') {
      this.modalService.open(content, { windowClass: 'modal-mini', size: 'sm', centered: true }).result.then(
        result => {
          this.closeResult = 'Closed with: $result';
        },
        reason => {
          this.closeResult = 'Dismissed $this.getDismissReason(reason)';
        }
      );
    } else if (modalDimension === '' && type === 'Notification') {
      this.modalService.open(content, { windowClass: 'modal-danger', centered: true }).result.then(
        result => {
          this.closeResult = 'Closed with: $result';
        },
        reason => {
          this.closeResult = 'Dismissed $this.getDismissReason(reason)';
        }
      );
    } else {
      this.modalService.open(content, { centered: true }).result.then(
        result => {
          this.closeResult = 'Closed with: $result';
        },
        reason => {
          this.closeResult = 'Dismissed $this.getDismissReason(reason)';
        }
      );
    }
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return 'with: $reason';
    }
  }

  ngOnInit() {
    this.loadAnnonceWithResolver();
    this.loadMessageList();
  }

  onSelect(event: any) {
    console.log(event);
    this.files.push(...event.addedFiles);
  }

  onRemove(event: File) {
    console.log(event);
    this.files.splice(this.files.indexOf(event), 1);
  }

  loadAnnonceWithResolver(): void {
    this.activatedRoute.data.subscribe(({ annonce }) => {
      this.annonce = annonce;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('evaHackathonApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  save(): void {
    this.isSaving = true;
    const message = this.messageFormService.getMessage(this.editForm);
    message.annonce = this.annonce;
    message.nomTransmeteur = (this.mandataireDelegateur?.nomDeFamille + ' ').toUpperCase() + this.mandataireDelegateur?.prenom;
    message.dateEnvoie = new Date().toISOString();
    console.log(message);

    if (this.messages) {
      const maxId = Math.max(...this.messages.map(message => message.id)); // l'Id la plus élevé
      this.messages?.unshift({
        id: maxId + 1,
        texte: message.texte,
        nomTransmeteur: message.nomTransmeteur,
        dateEnvoie: message.dateEnvoie,
        annonce: message.annonce,
        fichierJoin: message.fichierJoin,
        fichierJoinContentType: message.fichierJoinContentType,
      });
    }

    if (message.id !== null) {
      //
    } else {
      this.subscribeToSaveResponse(this.messageService.create(message));
    }
  }

  loadProfileMandataire(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => {
        this.account = account;
        if (account !== null) {
          this.mandataireDelegateurService.findByJhiUserId({ login: this.account?.login }).subscribe(
            (res: HttpResponse<IMandataireDelegateur>) => this.onSucessUser(res.body),
            (res: HttpResponse<any>) => this.onError()
          );
        }
      });
  }

  public clearForm(): void {
    let inputMessage = document.getElementsByTagName('textarea');
    for (let i = 0; i < inputMessage.length; i++) {
      inputMessage[i].value = '';
    }

    let inputNomAndEmail = document.getElementsByTagName('input');
    for (let i = 0; i < inputNomAndEmail.length; i++) {
      inputNomAndEmail[i].value = '';
    }
  }

  public onSaveSuccess(): void {
    this.clearForm();
    this.activeModal.dismiss();
  }

  protected onSucessUser(data: IMandataireDelegateur | null): void {
    if (data) {
      this.mandataireDelegateur = data;
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessage>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  loadMessageList(): void {
    this.messageService.getMessageList().subscribe(
      (res: HttpResponse<IMessage[]>) => {
        this.messages = res.body ?? [];
        this.onSuccess();
      },
      () => {
        this.onError();
      }
    );
  }

  protected onSuccess(): void {
    //
  }

  protected onError(): void {
    //
  }
}
