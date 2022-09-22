import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  animate,
  animateChild,
  group,
  query,
  sequence,
  stagger,
  state,
  style,
  transition,
  trigger,
  useAnimation,
} from '@angular/animations';
import { flashAnimation } from '../animations/flash.animation';
import { slideAndFadeAnimation } from '../animations/slide-and-fade.animation';
import { ICommentaire } from '../models/commentaire.model';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { finalize, takeUntil } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { IMandataireDelegateur } from '../../entities/mandataire-delegateur/mandataire-delegateur.model';
import { AccountService } from '../../core/auth/account.service';
import { MandataireDelegateurService } from '../../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { Account } from '../../core/auth/account.model';
import { Observable, Subject } from 'rxjs';
import Swal from 'sweetalert2';
import { numeroMoMoValidator } from '../validators/valid.validator';
import { CommentaireFormService } from './commentaires-form.service';
import { CommentairesService } from './services/commentaires.service';
import { IAnnonce } from '../../entities/annonce/annonce.model';

@Component({
  selector: 'jhi-commentaires',
  templateUrl: './commentaires.component.html',
  styleUrls: ['./commentaires.component.scss'],
  animations: [
    trigger('list', [transition(':enter', [query('@listItem', [stagger(50, [animateChild()])])])]),
    trigger('listItem', [
      state(
        'default',
        style({
          transform: 'scale(1)',
          'background-color': 'white',
          'z-index': 1,
        })
      ),
      state(
        'active',
        style({
          transform: 'scale(1.05)',
          'background-color': '#99BE0EFF',
          'z-index': 2,
        })
      ),
      transition('default => active', [animate('100ms ease-in-out')]),
      transition('active => default', [animate('500ms ease-in-out')]),
      //animation pour l'arrivé des commentaires
      transition('void => *', [
        query('.comment-text, .comment-date', [
          style({
            opacity: 0,
          }),
        ]),
        useAnimation(slideAndFadeAnimation, {
          params: {
            time: '504ms',
            startColor: '#99BE0EFF',
          },
        }),
        group([
          useAnimation(flashAnimation, {
            params: {
              time: '250ms',
              flashColor: 'rgb(182,249,111)',
            },
          }),
          query('.comment-text', [
            animate(
              '250ms',
              style({
                opacity: 1,
              })
            ),
          ]),
          query('.comment-date', [
            animate(
              '500ms',
              style({
                opacity: 1,
              })
            ),
          ]),
        ]),
      ]),
    ]),
  ],
})
export class CommentairesComponent implements OnInit {
  @Input() commentaires?: ICommentaire[];
  @Output() newComment = new EventEmitter<ICommentaire>();

  messageCtrl!: FormControl;
  emailCtrl!: FormControl;
  nomCommentateurCtrl!: FormControl;

  editCommentForm!: FormGroup;
  isSaving = true;

  animationStates: { [key: number]: 'default' | 'active' } = {};
  listItemAnimationState: 'default' | 'active' = 'default'; // pour les animatoins

  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;
  private readonly destroy$ = new Subject<void>();

  constructor(
    private formBuilder: FormBuilder,
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService,
    private commentaireFormService: CommentaireFormService,
    private commentaireService: CommentairesService
  ) {}

  ngOnInit(): void {
    this.initFormControls();
    this.initCommentForm();

    if (this.commentaires) {
      for (let index in this.commentaires) {
        this.animationStates[index] = 'default';
        console.log(this.commentaires);
      }
    }

    this.loadProfileMandataire();
  }

  private initFormControls(): void {
    this.messageCtrl = this.formBuilder.control('', [Validators.required, Validators.minLength(10)]);
    this.emailCtrl = this.formBuilder.control('', [Validators.required, Validators.email]);
    this.nomCommentateurCtrl = this.formBuilder.control('', [Validators.required, Validators.minLength(2)]);
  }

  initCommentForm(): void {
    this.editCommentForm = this.formBuilder.group({
      nomCommentateur: this.nomCommentateurCtrl,
      email: this.emailCtrl,
      message: this.messageCtrl,
    });
  }

  onLeaveComment() {
    if (this.messageCtrl.invalid || this.emailCtrl.invalid || this.nomCommentateurCtrl.invalid) {
      return;
    }
    if (this.commentaires) {
      const maxId = Math.max(...this.commentaires.map(message => message.id)); // l'Id la plus élevé
      this.commentaires?.unshift({
        id: maxId + 1,
        message: this.messageCtrl.value,
        email: this.emailCtrl.value,
        nomCommentateur: this.nomCommentateurCtrl.value,
        dateDeMessage: new Date().toISOString(),
        mandataireDelegateur: this.mandataireDelegateur,
      });
    }

    const comment = this.commentaireFormService.getCommentaire(this.editCommentForm);
    comment.message = this.messageCtrl.value;
    comment.mandataireDelegateur = this.mandataireDelegateur;
    comment.email = this.emailCtrl.value;
    comment.nomCommentateur = this.nomCommentateurCtrl.value;
    comment.dateDeMessage = new Date().toISOString();
    comment.id = null;

    console.log(comment);

    if (this.commentaires) {
      console.log(this.commentaires[0]);
      this.newComment.emit(this.commentaires[0]);
      if (comment.id !== null) {
        //
      } else {
        console.log('ici');
        this.subscribeToSaveResponse(this.commentaireService.create(comment));
      }
    }
    this.messageCtrl.reset();
  }

  onListItemMouseEnter(index: number) {
    this.animationStates[index] = 'active';
  }

  onListItemMouseLeave(index: number) {
    this.animationStates[index] = 'default';
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

  protected onError(): void {
    this.notification('Aucun user trouvé', 'warning');
  }
  protected onSucessUser(data: IMandataireDelegateur | null): void {
    if (data) {
      this.mandataireDelegateur = data;
      console.log('DATA USERIO MANDATAIRE DELEGATEUR');
      console.log(this.mandataireDelegateur);
    }
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected onSaveSuccess(): void {
    //
  }

  protected onSaveError(): void {
    this.notification('commentaire non envoyé', 'warning');
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnnonce>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected notification(message: string, type: string): void {
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: toast => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });

    if (type === 'success') {
      Toast.fire({
        icon: 'success',
        title: message,
      });
    }
    if (type === 'warning') {
      Toast.fire({
        icon: 'warning',
        title: message,
      });
    }
  }
}
