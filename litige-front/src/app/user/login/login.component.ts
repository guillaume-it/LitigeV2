import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { BehaviorSubject, Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;

  constructor(private authenticationService: AuthenticationService, private router: Router, private snackBar: MatSnackBar) {
   
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      login: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  login() {
    this.loading = true;
    this.authenticationService.login(this.loginForm.value.login, this.loginForm.value.password).subscribe(
      data => {
        this.loading = false;
        this.router.navigate(['/']);
      },
      error => {
        this.snackBar.open('Authentication failed.');
        this.loading = false;
      }
    );
  }
}
