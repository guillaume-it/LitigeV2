import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormGroup, Validators, FormControl, AbstractControl } from '@angular/forms';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from 'src/app/models';
import { UserService, formatError } from 'src/app/services';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
  user: User;
  adminMode: boolean;
  pwdForm: FormGroup;
  private loadingSubject: BehaviorSubject<boolean>;
  loading$: Observable<boolean>;

  constructor(
    private userService: UserService,
    private dialogRef: MatDialogRef<ChangePasswordComponent>,
    @Inject(MAT_DIALOG_DATA) data,
    private snackBar: MatSnackBar
  ) {
    this.user = data.user;
    this.adminMode = data.adminMode;
    this.loadingSubject = new BehaviorSubject<boolean>(false);
    this.loading$ = this.loadingSubject.asObservable();
  }

  ngOnInit() {
    if (this.adminMode) {
      this.pwdForm = new FormGroup({
        newPassword: new FormControl('', Validators.required)
      });
    } else {
      this.pwdForm = new FormGroup(
        {
          oldPassword: new FormControl('', Validators.required),
          newPassword: new FormControl('', Validators.required),
          newPassword1: new FormControl('', Validators.required)
        },
        (c: AbstractControl) => {
          if (c.get('newPassword').value !== c.get('newPassword1').value) {
            c.get('newPassword1').setErrors({ passwordMismatch: true });
            return { passwordMismatch: true };
          }
          return null;
        }
      );
    }
  }

  close() {
    this.dialogRef.close();
  }

  submit() {
    console.log('change password');
    this.loadingSubject.next(true);
    const oldPassword = this.pwdForm.get('oldPassword') ? this.pwdForm.get('oldPassword').value : '';
    this.userService.changePassword(this.user.id, oldPassword, this.pwdForm.get('newPassword').value).subscribe(
      res => {
        this.snackBar.open(`Password for user ${this.user.email} changed.`);
        this.dialogRef.close(this.user);
        this.loadingSubject.next(false);
      },
      err => {
        this.snackBar.open(`Change password failed due to ${formatError(err)}.`);
        this.loadingSubject.next(false);
      }
    );
  }
}
