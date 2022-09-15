import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPostulant, NewPostulant } from '../postulant.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPostulant for edit and NewPostulantFormGroupInput for create.
 */
type PostulantFormGroupInput = IPostulant | PartialWithRequiredKeyOf<NewPostulant>;

type PostulantFormDefaults = Pick<NewPostulant, 'id' | 'annonces'>;

type PostulantFormGroupContent = {
  id: FormControl<IPostulant['id'] | NewPostulant['id']>;
  numeroMomo: FormControl<IPostulant['numeroMomo']>;
  observation: FormControl<IPostulant['observation']>;
  annonces: FormControl<IPostulant['annonces']>;
  mandataireDelegateur: FormControl<IPostulant['mandataireDelegateur']>;
};

export type PostulantFormGroup = FormGroup<PostulantFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PostulantFormService {
  createPostulantFormGroup(postulant: PostulantFormGroupInput = { id: null }): PostulantFormGroup {
    const postulantRawValue = {
      ...this.getFormDefaults(),
      ...postulant,
    };
    return new FormGroup<PostulantFormGroupContent>({
      id: new FormControl(
        { value: postulantRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      numeroMomo: new FormControl(postulantRawValue.numeroMomo, {
        validators: [Validators.required],
      }),
      observation: new FormControl(postulantRawValue.observation),
      annonces: new FormControl(postulantRawValue.annonces ?? []),
      mandataireDelegateur: new FormControl(postulantRawValue.mandataireDelegateur),
    });
  }

  getPostulant(form: PostulantFormGroup): IPostulant | NewPostulant {
    return form.getRawValue() as IPostulant | NewPostulant;
  }

  resetForm(form: PostulantFormGroup, postulant: PostulantFormGroupInput): void {
    const postulantRawValue = { ...this.getFormDefaults(), ...postulant };
    form.reset(
      {
        ...postulantRawValue,
        id: { value: postulantRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PostulantFormDefaults {
    return {
      id: null,
      annonces: [],
    };
  }
}
