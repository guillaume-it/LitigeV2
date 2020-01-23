import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { createUniqueEmailValidator } from 'src/app/helpers/unique-email.validator';
import { MatSnackBar } from '@angular/material';
import { formatError } from 'src/app/services/store.service';
import { BehaviorSubject, Observable } from 'rxjs';

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

    this.claimantForm = new FormGroup({
      address: new FormControl('', Validators.required),
      objet: new FormControl('', Validators.required),
      message: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  signin() {
    this.userService
      .signinClaimant(
        this.claimantForm.get('email').value,
        this.claimantForm.get('password').value,
        this.claimantForm.get('firstName').value,
        this.claimantForm.get('name').value,
        this.claimantForm.get('phone').value
      )
      .subscribe(
        newUser => {
          console.log(`after signin ${newUser}`);
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
