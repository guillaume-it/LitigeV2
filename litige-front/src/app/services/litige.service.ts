import { CrudService } from './crud.service';
import { Injectable } from '@angular/core';
import { Litige } from '../models/litige';
import { Observable } from 'rxjs';
import { Page } from '../models/page';
import { url } from './url';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LitigeService {
  constructor(private crudService: CrudService) {}

  loadPages(): Observable<Page<Litige>> {
    return this.crudService.getPage(environment.serverUrl + url.litiges, {
      page: 0,
      limit: 20,
      size: 20,
      sort: {
        active: 'id',
        direction: 'asc'
      }
    });
  }
  getLitige(id): Observable<Litige> {
    return this.crudService.get(environment.serverUrl + url.litiges + '/' + id);
  }
  create(value: Litige): Observable<Litige> {
    return this.crudService.post(environment.serverUrl + url.litiges, value);
  }
  update(value: Litige): Observable<Litige> {
    return this.crudService.put(environment.serverUrl + url.litiges, value);
  }
}
