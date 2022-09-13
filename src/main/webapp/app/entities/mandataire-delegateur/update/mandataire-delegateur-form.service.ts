import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMandataireDelegateur, NewMandataireDelegateur } from '../mandataire-delegateur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMandataireDelegateur for edit and NewMandataireDelegateurFormGroupInput for create.
 */
type MandataireDelegateurFormGroupInput = IMandataireDelegateur | PartialWithRequiredKeyOf<NewMandataireDelegateur>;

type MandataireDelegateurFormDefaults = Pick<NewMandataireDelegateur, 'id' | 'souscriptions'>;

type MandataireDelegateurFormGroupContent = {
  id: FormControl<IMandataireDelegateur['id'] | NewMandataireDelegateur['id']>;
  nomDeFamille: FormControl<IMandataireDelegateur['nomDeFamille']>;
  prenom: FormControl<IMandataireDelegateur['prenom']>;
  contact: FormControl<IMandataireDelegateur['contact']>;
  email: FormControl<IMandataireDelegateur['email']>;
  numeroMomo: FormControl<IMandataireDelegateur['numeroMomo']>;
  sexe: FormControl<IMandataireDelegateur['sexe']>;
  pays: FormControl<IMandataireDelegateur['pays']>;
  ville: FormControl<IMandataireDelegateur['ville']>;
  adresse: FormControl<IMandataireDelegateur['adresse']>;
  etatCompte: FormControl<IMandataireDelegateur['etatCompte']>;
  situationMatrimoniale: FormControl<IMandataireDelegateur['situationMatrimoniale']>;
  souscriptions: FormControl<IMandataireDelegateur['souscriptions']>;
  photo: FormControl<IMandataireDelegateur['photo']>;
  photoContentType: FormControl<IMandataireDelegateur['photoContentType']>;
};

export type MandataireDelegateurFormGroup = FormGroup<MandataireDelegateurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MandataireDelegateurFormService {
  createMandataireDelegateurFormGroup(
    mandataireDelegateur: MandataireDelegateurFormGroupInput = { id: null }
  ): MandataireDelegateurFormGroup {
    const mandataireDelegateurRawValue = {
      ...this.getFormDefaults(),
      ...mandataireDelegateur,
    };
    return new FormGroup<MandataireDelegateurFormGroupContent>({
      id: new FormControl(
        { value: mandataireDelegateurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nomDeFamille: new FormControl(mandataireDelegateurRawValue.nomDeFamille, {
        validators: [Validators.required],
      }),
      prenom: new FormControl(mandataireDelegateurRawValue.prenom, {
        validators: [Validators.required],
      }),
      contact: new FormControl(mandataireDelegateurRawValue.contact, {
        validators: [Validators.required],
      }),
      email: new FormControl(mandataireDelegateurRawValue.email, {
        validators: [Validators.required],
      }),
      numeroMomo: new FormControl(mandataireDelegateurRawValue.numeroMomo, {
        validators: [Validators.required],
      }),
      sexe: new FormControl(mandataireDelegateurRawValue.sexe, {
        validators: [Validators.required],
      }),
      pays: new FormControl(mandataireDelegateurRawValue.pays),
      ville: new FormControl(mandataireDelegateurRawValue.ville),
      adresse: new FormControl(mandataireDelegateurRawValue.adresse),
      etatCompte: new FormControl(mandataireDelegateurRawValue.etatCompte),
      situationMatrimoniale: new FormControl(mandataireDelegateurRawValue.situationMatrimoniale),
      souscriptions: new FormControl(mandataireDelegateurRawValue.souscriptions ?? []),
      photo: new FormControl(mandataireDelegateurRawValue.photo),
      photoContentType: new FormControl(mandataireDelegateurRawValue.photoContentType),
    });
  }

  getMandataireDelegateur(form: MandataireDelegateurFormGroup): IMandataireDelegateur | NewMandataireDelegateur {
    return form.getRawValue() as IMandataireDelegateur | NewMandataireDelegateur;
  }

  resetForm(form: MandataireDelegateurFormGroup, mandataireDelegateur: MandataireDelegateurFormGroupInput): void {
    const mandataireDelegateurRawValue = { ...this.getFormDefaults(), ...mandataireDelegateur };
    form.reset(
      {
        ...mandataireDelegateurRawValue,
        id: { value: mandataireDelegateurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MandataireDelegateurFormDefaults {
    return {
      id: null,
      souscriptions: [],
    };
  }
}
