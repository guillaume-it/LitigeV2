import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Subject, Observable } from 'rxjs';
import { User } from 'src/app/models/user';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  userSubject: Observable<User>;

  constructor(public authentication: AuthenticationService, private snackBar: MatSnackBar,  private router: Router) {}

  ngOnInit() {
    this.userSubject = this.authentication.currentUserObservable;
  }

  logout() {
    this.authentication.logout().subscribe(data=>{
      this.router.navigate(['/login']);
      this.snackBar.open('Session completed');
    },error =>{
      this.snackBar.open('Error: session not completed');
    });
  }
}
