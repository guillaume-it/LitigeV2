import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class CrudService implements OnInit{

  baseUrl: string = 'http://localhost:8080/';

  constructor(private router: Router, private apiService: UserService) { }

  ngOnInit() {
    if (!window.sessionStorage.getItem('token')) {
      this.router.navigate(['login']);
      return;
    }

  }

}
