import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MandataireDelegateurFormService, MandataireDelegateurFormGroup } from './mandataire-delegateur-form.service';
import { IMandataireDelegateur } from '../mandataire-delegateur.model';
import { MandataireDelegateurService } from '../service/mandataire-delegateur.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { EtatCompte } from 'app/entities/enumerations/etat-compte.model';
import { SituationMatrimoniale } from 'app/entities/enumerations/situation-matrimoniale.model';
import { DataUtils, FileLoadError } from '../../../core/util/data-util.service';
import { EventManager, EventWithContent } from '../../../core/util/event-manager.service';
import { AlertError } from '../../../shared/alert/alert-error.model';

import countries from '../../../../content/country.json';
import { AbstractControl } from '@angular/forms';

@Component({
  selector: 'jhi-mandataire-delegateur-update',
  templateUrl: './mandataire-delegateur-update.component.html',
})
export class MandataireDelegateurUpdateComponent implements OnInit {
  isSaving = false;
  mandataireDelegateur: IMandataireDelegateur | null = null;
  sexeValues = Object.keys(Sexe);
  etatCompteValues = Object.keys(EtatCompte);
  situationMatrimonialeValues = Object.keys(SituationMatrimoniale);

  editForm: MandataireDelegateurFormGroup = this.mandataireDelegateurFormService.createMandataireDelegateurFormGroup();

  countryList: { country: string; dial_code: string }[] = countries;

  constructor(
    protected mandataireDelegateurService: MandataireDelegateurService,
    protected mandataireDelegateurFormService: MandataireDelegateurFormService,
    protected activatedRoute: ActivatedRoute,
    protected eventManager: EventManager,
    protected dataUtils: DataUtils
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mandataireDelegateur }) => {
      this.mandataireDelegateur = mandataireDelegateur;
      if (mandataireDelegateur) {
        this.updateForm(mandataireDelegateur);
      }
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
    const mandataireDelegateur = this.mandataireDelegateurFormService.getMandataireDelegateur(this.editForm);

    // tout en miniscule pour la recherche
    mandataireDelegateur.nomDeFamille = mandataireDelegateur.nomDeFamille?.toLowerCase();
    mandataireDelegateur.prenom = mandataireDelegateur.prenom?.toLowerCase();
    if (mandataireDelegateur.id !== null) {
      this.subscribeToSaveResponse(this.mandataireDelegateurService.update(mandataireDelegateur));
    } else {
      this.subscribeToSaveResponse(this.mandataireDelegateurService.create(mandataireDelegateur));
    }
  }

  getFormControlErrorText(ctrl: AbstractControl) {
    if (ctrl.hasError('required')) {
      return 'Ce champ est requis';
    } else if (ctrl.hasError('email')) {
      return "Merci d'entrer une adresse mail valide";
    } else if (ctrl.hasError('minlength')) {
      return 'Ce numéro de téléphone ne contient pas assez de chiffres';
    } else if (ctrl.hasError('maxlength')) {
      return 'Ce numéro de téléphone contient trop de chiffres';
    } else {
      return 'Ce champ contient une erreur';
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMandataireDelegateur>>): void {
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

  protected updateForm(mandataireDelegateur: IMandataireDelegateur): void {
    this.mandataireDelegateur = mandataireDelegateur;
    this.mandataireDelegateurFormService.resetForm(this.editForm, mandataireDelegateur);
  }
}
