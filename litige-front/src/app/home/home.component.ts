import { Component, OnInit } from '@angular/core';
import { AppService } from '../service/app.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CrudService } from '../service/crud.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  
  title = 'Demo';
  greeting = {};

  constructor(private app: AppService, private http: HttpClient) {
  }
  ngOnInit(): void {
    this.http.get('http://localhost:8080/token').subscribe(data => {
      const token = data['token'];
      this.http.get('http://localhost:8080', { headers: new HttpHeaders().set('X-Auth-Token', token) })
        .subscribe(response => this.greeting = response);
    }, () => { });  }
   

  authenticated() { return this.app.authenticated; }
}
