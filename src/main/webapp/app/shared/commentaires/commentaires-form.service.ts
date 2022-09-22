import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICommentaire, NewCommentaire } from '../models/commentaire.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICommentaire for edit and NewCommentaireFormGroupInput for create.
 */
type CommentaireFormGroupInput = ICommentaire | PartialWithRequiredKeyOf<NewCommentaire>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICommentaire | NewCommentaire> = Omit<T, ''> & {
  dateDeDelegation?: string | null;
};

type CommentaireFormRawValue = FormValueOf<ICommentaire>;

type NewCommentaireFormRawValue = FormValueOf<NewCommentaire>;

type CommentaireFormDefaults = Pick<NewCommentaire, 'id'>;

type CommentaireFormGroupContent = {
  id: FormControl<CommentaireFormRawValue['id'] | NewCommentaire['id']>;
  message: FormControl<CommentaireFormRawValue['message']>;
  dateDeMessage: FormControl<CommentaireFormRawValue['dateDeMessage']>;
  email: FormControl<CommentaireFormRawValue['email']>;
  nomCommentateur: FormControl<CommentaireFormRawValue['nomCommentateur']>;
  mandataireDelegateur: FormControl<CommentaireFormRawValue['mandataireDelegateur']>;
};

export type CommentaireFormGroup = FormGroup<CommentaireFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommentaireFormService {
  createCommentaireFormGroup(commentaire: CommentaireFormGroupInput = { id: null }): CommentaireFormGroup {
    const commentaireRawValue = this.convertCommentaireToCommentaireRawValue({
      ...this.getFormDefaults(),
      ...commentaire,
    });
    return new FormGroup<CommentaireFormGroupContent>({
      id: new FormControl(
        { value: commentaireRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      message: new FormControl(commentaireRawValue.message, {
        validators: [Validators.required],
      }),
      email: new FormControl(commentaireRawValue.email),
      dateDeMessage: new FormControl(commentaireRawValue.dateDeMessage),
      nomCommentateur: new FormControl(commentaireRawValue.nomCommentateur),
      mandataireDelegateur: new FormControl(commentaireRawValue.mandataireDelegateur),
    });
  }

  getCommentaire(form: CommentaireFormGroup): ICommentaire | NewCommentaire {
    return this.convertCommentaireRawValueToCommentaire(form.getRawValue() as CommentaireFormRawValue | NewCommentaireFormRawValue);
  }

  resetForm(form: CommentaireFormGroup, commentaire: CommentaireFormGroupInput): void {
    const commentaireRawValue = this.convertCommentaireToCommentaireRawValue({ ...this.getFormDefaults(), ...commentaire });
    form.reset(
      {
        ...commentaireRawValue,
        id: { value: commentaireRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CommentaireFormDefaults {
    return {
      id: null,
    };
  }

  private convertCommentaireRawValueToCommentaire(
    rawCommentaire: CommentaireFormRawValue | NewCommentaireFormRawValue
  ): ICommentaire | NewCommentaire {
    return {
      ...rawCommentaire,
    };
  }

  private convertCommentaireToCommentaireRawValue(
    commentaire: ICommentaire | (Partial<NewCommentaire> & CommentaireFormDefaults)
  ): CommentaireFormRawValue | PartialWithRequiredKeyOf<NewCommentaireFormRawValue> {
    return {
      ...commentaire,
    };
  }
}
