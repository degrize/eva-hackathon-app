import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { finalize, map, takeUntil } from 'rxjs/operators';

import { AnnonceFormService, AnnonceFormGroup } from './annonce-form.service';
import { IAnnonce } from '../annonce.model';
import { AnnonceService } from '../service/annonce.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { IMandataireDelegateur } from 'app/entities/mandataire-delegateur/mandataire-delegateur.model';
import { MandataireDelegateurService } from 'app/entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { AccountService } from '../../../core/auth/account.service';
import { Account } from '../../../core/auth/account.model';
import { EtatCompte } from '../../enumerations/etat-compte.model';

@Component({
  selector: 'jhi-annonce-update',
  templateUrl: './annonce-update.component.html',
  styleUrls: ['./annonce-update.component.scss'],
})
export class AnnonceUpdateComponent implements OnInit {
  isSaving = false;
  annonce: IAnnonce | null = null;

  categoriesSharedCollection: ICategorie[] = [];
  mandataireDelegateursSharedCollection: IMandataireDelegateur[] = [];

  editForm: AnnonceFormGroup = this.annonceFormService.createAnnonceFormGroup();

  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;

  private readonly destroy$ = new Subject<void>();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected annonceService: AnnonceService,
    protected annonceFormService: AnnonceFormService,
    protected categorieService: CategorieService,
    protected mandataireDelegateurService: MandataireDelegateurService,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {}

  compareCategorie = (o1: ICategorie | null, o2: ICategorie | null): boolean => this.categorieService.compareCategorie(o1, o2);

  compareMandataireDelegateur = (o1: IMandataireDelegateur | null, o2: IMandataireDelegateur | null): boolean =>
    this.mandataireDelegateurService.compareMandataireDelegateur(o1, o2);

  ngOnInit(): void {
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

    this.activatedRoute.data.subscribe(({ annonce }) => {
      this.annonce = annonce;
      if (annonce) {
        this.updateForm(annonce);
      }

      this.loadRelationshipsOptions();
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

  save(): void {
    this.isSaving = true;
    const annonce = this.annonceFormService.getAnnonce(this.editForm);
    annonce.dateDeDelais = annonce.dateDeDelegation;
    if (annonce.id !== null) {
      this.subscribeToSaveResponse(this.annonceService.update(annonce));
    } else {
      annonce.mandataireDelegateur = this.mandataireDelegateur;
      this.subscribeToSaveResponse(this.annonceService.create(annonce));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnnonce>>): void {
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

  protected updateForm(annonce: IAnnonce): void {
    this.annonce = annonce;
    this.annonceFormService.resetForm(this.editForm, annonce);

    this.categoriesSharedCollection = this.categorieService.addCategorieToCollectionIfMissing<ICategorie>(
      this.categoriesSharedCollection,
      ...(annonce.categories ?? [])
    );
    this.mandataireDelegateursSharedCollection =
      this.mandataireDelegateurService.addMandataireDelegateurToCollectionIfMissing<IMandataireDelegateur>(
        this.mandataireDelegateursSharedCollection,
        annonce.mandataireDelegateur
      );
  }

  protected loadRelationshipsOptions(): void {
    this.categorieService
      .query()
      .pipe(map((res: HttpResponse<ICategorie[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategorie[]) =>
          this.categorieService.addCategorieToCollectionIfMissing<ICategorie>(categories, ...(this.annonce?.categories ?? []))
        )
      )
      .subscribe((categories: ICategorie[]) => (this.categoriesSharedCollection = categories));

    this.mandataireDelegateurService
      .query()
      .pipe(map((res: HttpResponse<IMandataireDelegateur[]>) => res.body ?? []))
      .pipe(
        map((mandataireDelegateurs: IMandataireDelegateur[]) =>
          this.mandataireDelegateurService.addMandataireDelegateurToCollectionIfMissing<IMandataireDelegateur>(
            mandataireDelegateurs,
            this.annonce?.mandataireDelegateur
          )
        )
      )
      .subscribe((mandataireDelegateurs: IMandataireDelegateur[]) => (this.mandataireDelegateursSharedCollection = mandataireDelegateurs));
  }

  protected onError(): void {
    console.log('Erreur find user all informations');
  }

  protected onSucessUser(data: IMandataireDelegateur | null): void {
    if (data) {
      this.mandataireDelegateur = data;
      console.log('DATA USER MANDATAIRE DELEGATEUR');
    }
  }
}
