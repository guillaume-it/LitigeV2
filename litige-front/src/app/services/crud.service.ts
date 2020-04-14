import { Injectable } from '@angular/core';
import { HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';
import { Pageable } from '../models/pageable';
import { Observable } from 'rxjs';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root'
})
export class CrudService {
  constructor(private http: HttpClient) {}

  get<T>(url: string, params?: HttpParams): Observable<T> {
    return this.http.get<T>(url, { params });
  }

  getPage<T>(url: string, config?: Pageable<T>): Observable<Page<T>> {
    let params = new HttpParams();
    if (config) {
      params = params.set('page', config.page.toString());
      params = params.set('size', config.limit.toString());
      if (config.sort.direction) {
        params = params.set('sort', config.sort.active + ',' + config.sort.direction);
      }
    }

    console.log(`load ${url}, ${params.toString()}`);
    return this.http.get<Page<T>>(url, { params });
  }

  put<T>(url: string, objet: any): Observable<T> {
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    console.log(url);
    return this.http.put<T>(url, objet, { headers });
  }

  post<T>(url: string, objet: any): Observable<T> {
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    console.log(url);
    return this.http.post<T>(url, objet, { headers });
  }
}
