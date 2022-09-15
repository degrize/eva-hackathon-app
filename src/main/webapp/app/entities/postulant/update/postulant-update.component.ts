import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PostulantFormService, PostulantFormGroup } from './postulant-form.service';
import { IPostulant } from '../postulant.model';
import { PostulantService } from '../service/postulant.service';
import { IAnnonce } from 'app/entities/annonce/annonce.model';
import { AnnonceService } from 'app/entities/annonce/service/annonce.service';
import { IMandataireDelegateur } from '../../mandataire-delegateur/mandataire-delegateur.model';
import { MandataireDelegateurService } from '../../mandataire-delegateur/service/mandataire-delegateur.service';

@Component({
  selector: 'jhi-postulant-update',
  templateUrl: './postulant-update.component.html',
})
export class PostulantUpdateComponent implements OnInit {
  isSaving = false;
  postulant: IPostulant | null = null;

  annoncesSharedCollection: IAnnonce[] = [];

  editForm: PostulantFormGroup = this.postulantFormService.createPostulantFormGroup();

  mandataireDelegateursSharedCollection: IMandataireDelegateur[] = [];

  constructor(
    protected postulantService: PostulantService,
    protected postulantFormService: PostulantFormService,
    protected annonceService: AnnonceService,
    protected activatedRoute: ActivatedRoute,
    protected mandataireDelegateurService: MandataireDelegateurService
  ) {}

  compareAnnonce = (o1: IAnnonce | null, o2: IAnnonce | null): boolean => this.annonceService.compareAnnonce(o1, o2);

  compareMandataireDelegateur = (o1: IMandataireDelegateur | null, o2: IMandataireDelegateur | null): boolean =>
    this.mandataireDelegateurService.compareMandataireDelegateur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postulant }) => {
      this.postulant = postulant;
      if (postulant) {
        this.updateForm(postulant);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const postulant = this.postulantFormService.getPostulant(this.editForm);
    if (postulant.id !== null) {
      this.subscribeToSaveResponse(this.postulantService.update(postulant));
    } else {
      this.subscribeToSaveResponse(this.postulantService.create(postulant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostulant>>): void {
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

  protected updateForm(postulant: IPostulant): void {
    this.postulant = postulant;
    this.postulantFormService.resetForm(this.editForm, postulant);

    this.annoncesSharedCollection = this.annonceService.addAnnonceToCollectionIfMissing<IAnnonce>(
      this.annoncesSharedCollection,
      ...(postulant.annonces ?? [])
    );

    this.mandataireDelegateursSharedCollection =
      this.mandataireDelegateurService.addMandataireDelegateurToCollectionIfMissing<IMandataireDelegateur>(
        this.mandataireDelegateursSharedCollection,
        postulant.mandataireDelegateur
      );
  }

  protected loadRelationshipsOptions(): void {
    this.annonceService
      .query()
      .pipe(map((res: HttpResponse<IAnnonce[]>) => res.body ?? []))
      .pipe(
        map((annonces: IAnnonce[]) =>
          this.annonceService.addAnnonceToCollectionIfMissing<IAnnonce>(annonces, ...(this.postulant?.annonces ?? []))
        )
      )
      .subscribe((annonces: IAnnonce[]) => (this.annoncesSharedCollection = annonces));

    this.mandataireDelegateurService
      .query()
      .pipe(map((res: HttpResponse<IMandataireDelegateur[]>) => res.body ?? []))
      .pipe(
        map((mandataireDelegateurs: IMandataireDelegateur[]) =>
          this.mandataireDelegateurService.addMandataireDelegateurToCollectionIfMissing<IMandataireDelegateur>(
            mandataireDelegateurs,
            this.postulant?.mandataireDelegateur
          )
        )
      )
      .subscribe((mandataireDelegateurs: IMandataireDelegateur[]) => (this.mandataireDelegateursSharedCollection = mandataireDelegateurs));
  }
}
