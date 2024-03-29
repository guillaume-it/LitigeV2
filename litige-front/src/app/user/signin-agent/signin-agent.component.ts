import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { createUniqueEmailValidator } from 'src/app/helpers/unique-email.validator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { formatError } from 'src/app/services/store.service';
import { BehaviorSubject, Observable } from 'rxjs';

@Component({
  selector: 'app-signin-agent',
  templateUrl: './signin-agent.component.html',
  styleUrls: ['./signin-agent.component.scss']
})
export class SigninAgentComponent implements OnInit {
  signinForm: FormGroup;
  private loadingSubject: BehaviorSubject<boolean>;
  loading$: Observable<boolean>;

  constructor(private router: Router, private userService: UserService, private snackBar: MatSnackBar) {
    this.loadingSubject = new BehaviorSubject<boolean>(false);
    this.loading$ = this.loadingSubject.asObservable();
  }

  ngOnInit() {
    this.signinForm = new FormGroup({
      email: new FormControl('', {
        validators: [Validators.required],
        asyncValidators: createUniqueEmailValidator(this.userService, this.snackBar),
        updateOn: 'blur'
      }),
      password: new FormControl('', Validators.required)
    });
  }

  signin() {
    this.userService.signin(this.signinForm.get('email').value, this.signinForm.get('password').value).subscribe(
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
