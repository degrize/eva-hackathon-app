import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAide, NewAide } from './aide.model';
import { ICategorie, NewCategorie } from '../entities/categorie/categorie.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAide for edit and NewAideFormGroupInput for create.
 */
type AideFormGroupInput = IAide | PartialWithRequiredKeyOf<NewAide>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAide | NewAide> = Omit<T, 'dateDeDelegation'> & {
  dateDeDelegation?: string | null;
};

type AideFormRawValue = FormValueOf<IAide>;

type NewAideFormRawValue = FormValueOf<NewAide>;

type AideFormDefaults = Pick<NewAide, 'id'>;

type AideFormGroupContent = {
  id: FormControl<AideFormRawValue['id'] | NewAide['id']>;
  message: FormControl<AideFormRawValue['message']>;
  nom: FormControl<AideFormRawValue['nom']>;
  email: FormControl<AideFormRawValue['email']>;
};

export type AideFormGroup = FormGroup<AideFormGroupContent>;
@Injectable({
  providedIn: 'root',
})
export class AideFormService {
  createAideFormGroup(aide: AideFormGroupInput = { id: null }): AideFormGroup {
    const aideRawValue = this.convertAideToAideRawValue({
      ...this.getFormDefaults(),
      ...aide,
    });
    return new FormGroup<AideFormGroupContent>({
      id: new FormControl(
        { value: aideRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      message: new FormControl(aideRawValue.message, {
        validators: [Validators.required],
      }),
      nom: new FormControl(aideRawValue.nom),
      email: new FormControl(aideRawValue.email, {
        validators: [Validators.required],
      }),
    });
  }

  getAide(form: AideFormGroup): IAide | NewAide {
    return form.getRawValue() as IAide | NewAide;
  }

  resetForm(form: AideFormGroup, aide: AideFormGroupInput): void {
    const aideRawValue = this.convertAideToAideRawValue({ ...this.getFormDefaults(), ...aide });
    form.reset(
      {
        ...aideRawValue,
        id: { value: aideRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AideFormDefaults {
    return {
      id: null,
    };
  }

  private convertAideRawValueToAnnonce(rawAnnonce: AideFormRawValue | NewAideFormRawValue): IAide | NewAide {
    return {
      ...rawAnnonce,
    };
  }

  private convertAideToAideRawValue(
    aide: IAide | (Partial<NewAide> & AideFormDefaults)
  ): AideFormRawValue | PartialWithRequiredKeyOf<NewAideFormRawValue> {
    return {
      ...aide,
    };
  }
}
