import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { Pageable } from '../models/pageable';
import { Observable } from 'rxjs';
import { Page } from '../models/page';

@Injectable({
  providedIn: 'root'
})
export class CrudService<T> {
  constructor(private http: HttpClient) {}

  get(url: string): Observable<T> {
    return this.http.get<T>(url);
  }

  getPage(url: string, config?: Pageable<T>): Observable<Page<T>> {
    let params = new HttpParams();
    if (config) {
      params = params.set('page', config.page.toString());
      params = params.set('size', config.limit.toString());
      if (config.sort.direction) {
        params = params.set(
          'sort',
          config.sort.active + ',' + config.sort.direction
        );
      }
    }

    console.log(`load ${url}, ${params.toString()}`);
    return this.http.get<Page<T>>(url, { params });
  }
}
