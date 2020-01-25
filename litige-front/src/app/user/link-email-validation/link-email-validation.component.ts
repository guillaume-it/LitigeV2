import { UserService } from 'src/app/services/user.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models';

@Component({
  selector: 'app-link-email-validation',
  templateUrl: './link-email-validation.component.html',
  styleUrls: ['./link-email-validation.component.scss']
})
export class LinkEmailValidationComponent implements OnInit {
  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) {}
  user: User;
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.userService.validateAccount(params.get('email'), params.get('token')).subscribe(
        user => {
          this.user = user;
        },
        err => {
          // TODO manage error
          console.log(err);
        }
      );
    });
  }
}
