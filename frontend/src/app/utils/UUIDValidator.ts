import { AbstractControl, ValidatorFn } from '@angular/forms';

export class UUIDValidator {
  static validUUID(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const regex = /^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/;
      return regex.test(control.value) ? null : { 'invalidUUID': true };
    };
  }
}
