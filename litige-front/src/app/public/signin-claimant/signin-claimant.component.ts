import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { createUniqueEmailValidator } from 'src/app/helpers/unique-email.validator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { formatError } from 'src/app/services/store.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from 'src/app/models';

@Component({
  selector: 'app-signin-claimant',
  templateUrl: './signin-claimant.component.html',
  styleUrls: ['./signin-claimant.component.scss']
})
export class SigninClaimantComponent implements OnInit {
  formGroup: FormGroup;
  private loadingSubject: BehaviorSubject<boolean>;
  loading$: Observable<boolean>;
  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }
  constructor(private router: Router, private userService: UserService, private snackBar: MatSnackBar) {
    this.loadingSubject = new BehaviorSubject<boolean>(false);
    this.loading$ = this.loadingSubject.asObservable();
  }

  ngOnInit() {
    this.formGroup = new FormGroup({});
  }

  signin() {
    const user = new User();
    user.email = this.formGroup.get('email').value;
    user.password = this.formGroup.get('password').value;
    user.firstName = this.formGroup.get('firstName').value;
    user.lastName = this.formGroup.get('name').value;
    user.phone = this.formGroup.get('phone').value;
    this.userService.signinClaimant(user).subscribe(
      newUser => {
        this.loadingSubject.next(false);
        if (newUser) {
          this.snackBar.open(`User ${newUser.email} successfully created.`);
          this.router.navigate(['/login']);
        }
      },
      err => {
        this.snackBar.open(`User creation failed due to ${formatError(err)}.`);
        this.loadingSubject.next(false);
      }
    );
  }
}
