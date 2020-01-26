import { AbstractControl, ValidationErrors } from '@angular/forms';
import { UserService } from '../services/user.service';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material';
import { formatError } from '../services/store.service';

export function createUniqueEmailValidator(
  userService: UserService,
  snackBar?: MatSnackBar,
  defaultEmail?: AbstractControl
) {
  return (ctrl: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
    if (defaultEmail && defaultEmail.value && ctrl.value === defaultEmail.value) {
      return of(null);
    }
    return userService.validateEmail(ctrl.value).pipe(
      map(
        res => (res ? { emailTaken: true } : null),
        catchError(err => {
          snackBar.open(`validation failed due to ${formatError(err)}`);
          return null;
        })
      )
    );
  };
}
