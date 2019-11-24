import { CrudService } from './crud.service';
import { Injectable } from '@angular/core';
import { Litige } from '../models/litige';
import { Observable } from 'rxjs';
import { Page } from '../models/page';
import { url } from './url';

@Injectable({
  providedIn: 'root'
})
export class LitigeServive {
  constructor(private crudService: CrudService<Litige>) {}

  loadPages(): Observable<Page<Litige>> {
    return this.crudService.getPage(url.litiges, {
      page: 0,
      limit: 20,
      size: 20,
      sort: {
        active: 'id',
        direction: 'asc'
      }
    });
  }
  getLitige(): Observable<Litige> {
    return this.crudService.get(url.litiges + '1');
  }
}
