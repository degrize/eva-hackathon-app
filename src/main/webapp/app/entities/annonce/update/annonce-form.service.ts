import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAnnonce, NewAnnonce } from '../annonce.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnnonce for edit and NewAnnonceFormGroupInput for create.
 */
type AnnonceFormGroupInput = IAnnonce | PartialWithRequiredKeyOf<NewAnnonce>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAnnonce | NewAnnonce> = Omit<T, 'dateDeDelegation'> & {
  dateDeDelegation?: string | null;
};

type AnnonceFormRawValue = FormValueOf<IAnnonce>;

type NewAnnonceFormRawValue = FormValueOf<NewAnnonce>;

type AnnonceFormDefaults = Pick<NewAnnonce, 'id' | 'dateDeDelegation' | 'categories' | 'postulants'>;

type AnnonceFormGroupContent = {
  id: FormControl<AnnonceFormRawValue['id'] | NewAnnonce['id']>;
  titre: FormControl<AnnonceFormRawValue['titre']>;
  dateDeDelegation: FormControl<AnnonceFormRawValue['dateDeDelegation']>;
  dateDeDelais: FormControl<AnnonceFormRawValue['dateDeDelais']>;
  tarif: FormControl<AnnonceFormRawValue['tarif']>;
  postulantRetenu: FormControl<AnnonceFormRawValue['postulantRetenu']>;
  imageVideo: FormControl<AnnonceFormRawValue['imageVideo']>;
  imageVideoContentType: FormControl<AnnonceFormRawValue['imageVideoContentType']>;
  description: FormControl<AnnonceFormRawValue['description']>;
  categories: FormControl<AnnonceFormRawValue['categories']>;
  mandataireDelegateur: FormControl<AnnonceFormRawValue['mandataireDelegateur']>;
  postulants: FormControl<AnnonceFormRawValue['postulants']>;
};

export type AnnonceFormGroup = FormGroup<AnnonceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnnonceFormService {
  createAnnonceFormGroup(annonce: AnnonceFormGroupInput = { id: null }): AnnonceFormGroup {
    const annonceRawValue = this.convertAnnonceToAnnonceRawValue({
      ...this.getFormDefaults(),
      ...annonce,
    });
    return new FormGroup<AnnonceFormGroupContent>({
      id: new FormControl(
        { value: annonceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titre: new FormControl(annonceRawValue.titre, {
        validators: [Validators.required],
      }),
      dateDeDelegation: new FormControl(annonceRawValue.dateDeDelegation),
      dateDeDelais: new FormControl(annonceRawValue.dateDeDelais, {
        validators: [Validators.required],
      }),
      tarif: new FormControl(annonceRawValue.tarif, {
        validators: [Validators.required],
      }),
      postulantRetenu: new FormControl(annonceRawValue.postulantRetenu),
      imageVideo: new FormControl(annonceRawValue.imageVideo),
      imageVideoContentType: new FormControl(annonceRawValue.imageVideoContentType),
      description: new FormControl(annonceRawValue.description),
      categories: new FormControl(annonceRawValue.categories ?? []),
      mandataireDelegateur: new FormControl(annonceRawValue.mandataireDelegateur),
      postulants: new FormControl(annonceRawValue.postulants ?? []),
    });
  }

  getAnnonce(form: AnnonceFormGroup): IAnnonce | NewAnnonce {
    return this.convertAnnonceRawValueToAnnonce(form.getRawValue() as AnnonceFormRawValue | NewAnnonceFormRawValue);
  }

  resetForm(form: AnnonceFormGroup, annonce: AnnonceFormGroupInput): void {
    const annonceRawValue = this.convertAnnonceToAnnonceRawValue({ ...this.getFormDefaults(), ...annonce });
    form.reset(
      {
        ...annonceRawValue,
        id: { value: annonceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AnnonceFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateDeDelegation: currentTime,
      categories: [],
      postulants: [],
    };
  }

  private convertAnnonceRawValueToAnnonce(rawAnnonce: AnnonceFormRawValue | NewAnnonceFormRawValue): IAnnonce | NewAnnonce {
    return {
      ...rawAnnonce,
      dateDeDelegation: dayjs(rawAnnonce.dateDeDelegation, DATE_TIME_FORMAT),
    };
  }

  private convertAnnonceToAnnonceRawValue(
    annonce: IAnnonce | (Partial<NewAnnonce> & AnnonceFormDefaults)
  ): AnnonceFormRawValue | PartialWithRequiredKeyOf<NewAnnonceFormRawValue> {
    return {
      ...annonce,
      dateDeDelegation: annonce.dateDeDelegation ? annonce.dateDeDelegation.format(DATE_TIME_FORMAT) : undefined,
      categories: annonce.categories ?? [],
      postulants: annonce.postulants ?? [],
    };
  }
}
