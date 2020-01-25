import { UserService } from './../../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from 'src/app/models';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-identification',
  templateUrl: './identification.component.html',
  styleUrls: ['./identification.component.scss']
})
export class IdentificationComponent implements OnInit {
  formGroup = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    phone: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.formGroup.addControl(
      'email',
      new FormControl('', {
        validators: [Validators.required, Validators.email]
        // asyncValidators: createUniqueEmailValidator(this.userService, this.snackBar, this.formGroup.get('email').value),
        // updateOn: 'blur'
      })
    );
  }
  create() {
    const user = new User();
    user.firstName = this.formGroup.get('firstName').value;
    user.lastName = this.formGroup.get('lastName').value;
    user.phone = this.formGroup.get('phone').value;
    user.email = this.formGroup.get('email').value;
    user.password = this.formGroup.get('password').value;

    this.userService.signinClaimant(user).subscribe(
      res => {
        this.router.navigate(['send-validation-email']);
      },
      err => {
        console.log(err);
        // TODO manage error
      }
    );
  }
}
