import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TransactionFormService, TransactionFormGroup } from './transaction-form.service';
import { ITransaction } from '../transaction.model';
import { TransactionService } from '../service/transaction.service';
import { IAnnonce } from 'app/entities/annonce/annonce.model';
import { AnnonceService } from 'app/entities/annonce/service/annonce.service';
import { IPostulant } from 'app/entities/postulant/postulant.model';
import { PostulantService } from 'app/entities/postulant/service/postulant.service';
import { Devise } from 'app/entities/enumerations/devise.model';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html',
})
export class TransactionUpdateComponent implements OnInit {
  isSaving = false;
  transaction: ITransaction | null = null;
  deviseValues = Object.keys(Devise);

  annoncesCollection: IAnnonce[] = [];
  postulantsCollection: IPostulant[] = [];

  editForm: TransactionFormGroup = this.transactionFormService.createTransactionFormGroup();

  constructor(
    protected transactionService: TransactionService,
    protected transactionFormService: TransactionFormService,
    protected annonceService: AnnonceService,
    protected postulantService: PostulantService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAnnonce = (o1: IAnnonce | null, o2: IAnnonce | null): boolean => this.annonceService.compareAnnonce(o1, o2);

  comparePostulant = (o1: IPostulant | null, o2: IPostulant | null): boolean => this.postulantService.comparePostulant(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.transaction = transaction;
      if (transaction) {
        this.updateForm(transaction);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.transactionFormService.getTransaction(this.editForm);
    if (transaction.id !== null) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(transaction: ITransaction): void {
    this.transaction = transaction;
    this.transactionFormService.resetForm(this.editForm, transaction);

    this.annoncesCollection = this.annonceService.addAnnonceToCollectionIfMissing<IAnnonce>(this.annoncesCollection, transaction.annonce);
    this.postulantsCollection = this.postulantService.addPostulantToCollectionIfMissing<IPostulant>(
      this.postulantsCollection,
      transaction.postulant
    );
  }

  protected loadRelationshipsOptions(): void {
    this.annonceService
      .query({ filter: 'transaction-is-null' })
      .pipe(map((res: HttpResponse<IAnnonce[]>) => res.body ?? []))
      .pipe(
        map((annonces: IAnnonce[]) => this.annonceService.addAnnonceToCollectionIfMissing<IAnnonce>(annonces, this.transaction?.annonce))
      )
      .subscribe((annonces: IAnnonce[]) => (this.annoncesCollection = annonces));

    this.postulantService
      .query({ filter: 'transaction-is-null' })
      .pipe(map((res: HttpResponse<IPostulant[]>) => res.body ?? []))
      .pipe(
        map((postulants: IPostulant[]) =>
          this.postulantService.addPostulantToCollectionIfMissing<IPostulant>(postulants, this.transaction?.postulant)
        )
      )
      .subscribe((postulants: IPostulant[]) => (this.postulantsCollection = postulants));
  }
}
