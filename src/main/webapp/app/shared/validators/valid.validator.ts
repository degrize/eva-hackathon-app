import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function numeroMoMoValidator(): ValidatorFn {
  return (ctrl: AbstractControl): null | ValidationErrors => {
    if (ctrl.value) {
      if (ctrl.value.toString().startsWith('05')) {
        return null;
      }
      return {
        validValidator: ctrl.value,
      };
    } else {
      return {
        validValidator: ctrl.value,
      };
    }
  };
}
