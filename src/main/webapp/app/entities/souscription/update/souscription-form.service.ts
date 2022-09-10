import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISouscription, NewSouscription } from '../souscription.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISouscription for edit and NewSouscriptionFormGroupInput for create.
 */
type SouscriptionFormGroupInput = ISouscription | PartialWithRequiredKeyOf<NewSouscription>;

type SouscriptionFormDefaults = Pick<NewSouscription, 'id' | 'mandataireDelegateurs'>;

type SouscriptionFormGroupContent = {
  id: FormControl<ISouscription['id'] | NewSouscription['id']>;
  etat: FormControl<ISouscription['etat']>;
  montant: FormControl<ISouscription['montant']>;
  pourcentageDuDon: FormControl<ISouscription['pourcentageDuDon']>;
  mandataireDelegateurs: FormControl<ISouscription['mandataireDelegateurs']>;
};

export type SouscriptionFormGroup = FormGroup<SouscriptionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SouscriptionFormService {
  createSouscriptionFormGroup(souscription: SouscriptionFormGroupInput = { id: null }): SouscriptionFormGroup {
    const souscriptionRawValue = {
      ...this.getFormDefaults(),
      ...souscription,
    };
    return new FormGroup<SouscriptionFormGroupContent>({
      id: new FormControl(
        { value: souscriptionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      etat: new FormControl(souscriptionRawValue.etat, {
        validators: [Validators.required],
      }),
      montant: new FormControl(souscriptionRawValue.montant, {
        validators: [Validators.required],
      }),
      pourcentageDuDon: new FormControl(souscriptionRawValue.pourcentageDuDon, {
        validators: [Validators.required],
      }),
      mandataireDelegateurs: new FormControl(souscriptionRawValue.mandataireDelegateurs ?? []),
    });
  }

  getSouscription(form: SouscriptionFormGroup): ISouscription | NewSouscription {
    return form.getRawValue() as ISouscription | NewSouscription;
  }

  resetForm(form: SouscriptionFormGroup, souscription: SouscriptionFormGroupInput): void {
    const souscriptionRawValue = { ...this.getFormDefaults(), ...souscription };
    form.reset(
      {
        ...souscriptionRawValue,
        id: { value: souscriptionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SouscriptionFormDefaults {
    return {
      id: null,
      mandataireDelegateurs: [],
    };
  }
}
