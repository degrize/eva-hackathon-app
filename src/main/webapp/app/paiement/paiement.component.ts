import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { finalize, takeUntil } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';
import { AccountService } from '../core/auth/account.service';
import { MandataireDelegateurService } from '../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { Observable, Subject } from 'rxjs';
import { IAnnonce } from '../entities/annonce/annonce.model';
import { Account } from '../core/auth/account.model';
import Swal from 'sweetalert2';
import { ITransaction } from '../entities/transaction/transaction.model';
import { Devise } from '../entities/enumerations/devise.model';
import { IPostulant } from '../entities/postulant/postulant.model';
import { TransactionFormGroup, TransactionFormService } from '../entities/transaction/update/transaction-form.service';
import { TransactionService } from '../entities/transaction/service/transaction.service';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { numeroMoMoValidator } from '../shared/validators/valid.validator';
import { PostulantFormGroup, PostulantFormService } from '../entities/postulant/update/postulant-form.service';
import { PostulantService } from '../entities/postulant/service/postulant.service';
import { AnnonceService } from '../entities/annonce/service/annonce.service';

@Component({
  selector: 'jhi-paiement',
  templateUrl: './paiement.component.html',
  styleUrls: ['./paiement.component.scss'],
})
export class PaiementComponent implements OnInit {
  fraisEVa = 200;
  numeroMTN = '';
  precisionInput = '';
  params: any;
  annonceId: any;
  mandataireDelegateurId: any;

  account: Account | null = null;
  annonce: IAnnonce | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;

  isSaving = false;
  transaction: ITransaction | null = null;
  deviseValues = Object.keys(Devise);

  annoncesCollection: IAnnonce[] = [];
  postulantsCollection: IPostulant[] = [];

  mainForm!: FormGroup;
  numeroMomo!: FormControl;
  precision!: FormControl;

  tarif = 200;

  editForm: TransactionFormGroup = this.transactionFormService.createTransactionFormGroup();
  editFormPostulant: PostulantFormGroup = this.postulantFormService.createPostulantFormGroup();

  private readonly destroy$ = new Subject<void>();

  constructor(
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService,
    protected transactionService: TransactionService,
    protected transactionFormService: TransactionFormService,
    private formBuilder: FormBuilder,
    protected postulantService: PostulantService,
    protected postulantFormService: PostulantFormService,
    protected annonceService: AnnonceService
  ) {}

  ngOnInit(): void {
    this.initFormControls();
    this.initMainForm();

    this.loadQueryParams();
    this.loadProfileMandataire();
  }

  previousState(): void {
    window.history.back();
  }

  initMainForm(): void {
    this.mainForm = this.formBuilder.group({
      numeroMomo: this.numeroMomo,
      precision: this.precision,
    });
  }

  private initFormControls(): void {
    this.numeroMomo = this.formBuilder.control('', {
      validators: [Validators.required, Validators.maxLength(10), Validators.minLength(10), numeroMoMoValidator()],
    });
    this.precision = this.formBuilder.control('');
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.transactionFormService.getTransaction(this.editForm);
    transaction.annonce = this.annonce;
    transaction.devise = Devise.FCFA;
    transaction.montant = parseInt(this.annonce?.tarif + '');

    let inputMomoNumber = document.getElementsByTagName('input');
    for (let i = 0; i < inputMomoNumber.length; i++) {
      if (inputMomoNumber[i].id === 'inputMoMo') {
        this.numeroMTN = inputMomoNumber[i].value;
      }
    }
    transaction.numeroMtn = this.numeroMTN;

    let inputPrecision = document.getElementsByTagName('textarea');
    for (let i = 0; i < inputPrecision.length; i++) {
      if (inputPrecision[i].id === 'inputPrecision') {
        this.precisionInput = inputPrecision[i].value;
      }
    }
    transaction.precision = this.precisionInput;
    if (transaction.id !== null) {
      //
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  protected subscribeToSaveResponseAnnonce(result: Observable<HttpResponse<IAnnonce>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  private loadQueryParams(): void {
    this.activatedRoute.data.subscribe(({ annonce }) => {
      this.annonce = annonce;
      if (this.annonce?.tarif) this.tarif = parseInt(this.annonce?.tarif);
    });
  }

  private loadProfileMandataire(): void {
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

  protected onSaveSuccessTransaction(): void {
    if (this.annonce) {
      this.startAbonnement(this.annonce);
      this.annonce.postulantRetenu = this.mandataireDelegateur?.id;
      this.subscribeToSaveResponseAnnonce(this.annonceService.update(this.annonce));
    }

    this.notification('Paiement Effectué avec succès', 'success');
    this.previousState();
  }

  protected onSaveSuccess(): void {
    //
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => {
        this.onSaveSuccessTransaction();
      },
      error: () => this.onSaveError(),
    });
  }

  startAbonnement(annonce: IAnnonce): void {
    const postulant = this.postulantFormService.getPostulant(this.editFormPostulant);
    postulant.mandataireDelegateur = this.mandataireDelegateur;
    postulant.numeroMomo = this.mandataireDelegateur?.numeroMomo;
    postulant.annonces?.push(annonce);
    postulant.observation = 'RAS';

    if (postulant?.id !== null) {
      this.subscribeToSaveResponsePostulant(this.postulantService.update(postulant));
    } else {
      this.subscribeToSaveResponsePostulant(this.postulantService.create(postulant));
    }
  }

  protected subscribeToSaveResponsePostulant(result: Observable<HttpResponse<IPostulant>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onError(): void {
    this.notification('Aucun mandataire trouvé', 'warning');
  }

  protected onSucessUser(data: IMandataireDelegateur | null): void {
    if (data) {
      this.mandataireDelegateur = data;
      this.notification('verifications correctes', 'success');
      this.updateForm(this.mandataireDelegateur);
    }
  }

  protected updateForm(mandataireDelegateur: IMandataireDelegateur): void {
    this.numeroMTN = mandataireDelegateur?.numeroMomo + '';
    let inputMomoNumber = document.getElementsByTagName('input');
    for (let i = 0; i < inputMomoNumber.length; i++) {
      if (inputMomoNumber[i].id === 'inputMoMo') {
        inputMomoNumber[i].value = mandataireDelegateur?.numeroMomo + '';
      }
    }
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
