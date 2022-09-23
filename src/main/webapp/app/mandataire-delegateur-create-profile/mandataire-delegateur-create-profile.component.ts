import { Component, OnDestroy, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  MandataireDelegateurFormGroup,
  MandataireDelegateurFormService,
} from '../entities/mandataire-delegateur/update/mandataire-delegateur-form.service';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';
import { Sexe } from '../entities/enumerations/sexe.model';
import { EtatCompte } from '../entities/enumerations/etat-compte.model';
import { SituationMatrimoniale } from '../entities/enumerations/situation-matrimoniale.model';
import { MandataireDelegateurService } from '../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EventManager, EventWithContent } from '../core/util/event-manager.service';
import { DataUtils, FileLoadError } from '../core/util/data-util.service';
import { AlertError } from '../shared/alert/alert-error.model';
import { Observable, Subject } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { finalize, takeUntil } from 'rxjs/operators';
import { AccountService } from '../core/auth/account.service';
import { Account } from '../core/auth/account.model';
import { ITEM_DELETED_EVENT } from '../config/navigation.constants';
import Swal from 'sweetalert2';

@Component({
  selector: 'jhi-mandataire-delegateur-create-profile',
  templateUrl: './mandataire-delegateur-create-profile.component.html',
  styleUrls: ['./mandataire-delegateur-create-profile.component.scss'],
})
export class MandataireDelegateurCreateProfileComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  closeResult = '';
  isSaving = false;
  mandataireDelegateur: IMandataireDelegateur | null = null;
  sexeValues = Object.keys(Sexe);
  etatCompteValues = Object.keys(EtatCompte);
  situationMatrimonialeValues = Object.keys(SituationMatrimoniale);

  editForm: MandataireDelegateurFormGroup = this.mandataireDelegateurFormService.createMandataireDelegateurFormGroup();

  private readonly destroy$ = new Subject<void>();

  constructor(
    private modalService: NgbModal,
    public activeModal: NgbActiveModal,
    private accountService: AccountService,
    protected mandataireDelegateurService: MandataireDelegateurService,
    protected mandataireDelegateurFormService: MandataireDelegateurFormService,
    protected activatedRoute: ActivatedRoute,
    protected eventManager: EventManager,
    protected dataUtils: DataUtils,
    private router: Router
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
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => {
        this.account = account;
      });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('evaHackathonApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  ngOnDestroy(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    this.isSaving = true;
    const mandataireDelegateur = this.mandataireDelegateurFormService.getMandataireDelegateur(this.editForm);
    if (mandataireDelegateur.id !== null) {
      this.subscribeToSaveResponse(this.mandataireDelegateurService.update(mandataireDelegateur));
    } else {
      mandataireDelegateur.email = this.account?.email;
      mandataireDelegateur.nomDeFamille = this.account?.lastName ? this.account?.lastName : 'Sans nom';
      mandataireDelegateur.prenom = this.account?.firstName ? this.account?.firstName : 'Sans Prenom';

      this.subscribeToSaveResponse(this.mandataireDelegateurService.create(mandataireDelegateur));
    }
  }

  makeNotification(): void {
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 4000,
      timerProgressBar: true,
      didOpen: toast => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });

    Toast.fire({
      icon: 'success',
      title: 'Vous avez finalisé votre profile EVA',
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMandataireDelegateur>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.activeModal.dismiss();
    this.router.navigateByUrl('/user-profile');
    this.makeNotification();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(mandataireDelegateur: IMandataireDelegateur): void {
    this.mandataireDelegateur = mandataireDelegateur;
    this.mandataireDelegateurFormService.resetForm(this.editForm, mandataireDelegateur);
  }
}
