import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CrudService {

  urlServeur = "http://localhost:8080/";

  constructor(private http: HttpClient) { }

  get(uri: string, headers?: HttpHeaders) {
    if (headers){
      console.log("add hearders", headers);
      return this.http.get(this.urlServeur + uri, { headers: headers });
    }else{
    return this.http.get(this.urlServeur + uri);
    }
  }
  
  
}
