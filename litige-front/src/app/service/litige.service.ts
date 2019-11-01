import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LitigeService {

  constructor(private http: HttpClient) { }
  baseUrl: string = 'http://localhost:8080/litige/';


  getLitigeById(id: number) {
    return this.http.get(this.baseUrl + 'litige/' + id + '?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token);
  }
}
