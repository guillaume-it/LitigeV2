import { CrudService } from './crud.service';
import { Injectable } from '@angular/core';
import { Litige } from '../models/litige';
import { Observable } from 'rxjs';
import { Page } from '../models/page';
import { url } from './url';
import { environment } from 'src/environments/environment';
import { FileInformation } from '../models/file-information';
import { HttpRequest, HttpEventType, HttpResponse, HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LitigeService {
  constructor(private crudService: CrudService, private http: HttpClient) {}

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

  public upload(files: Map<string, FileInformation>, idClaim: number) {
    for (const [key, value] of files) {
      if (!value.loaded) {
        // create a new multipart-form for every file
        const formData: FormData = new FormData();
        formData.append('file', value.file, value.file.name);
        formData.append('idClaim', idClaim.toString());
        // create a http-post request and pass the form
        // tell it to report the upload progress
        const req = new HttpRequest('POST', environment.serverUrl + url.litiges + '/upload-file', formData, {
          reportProgress: true,
          responseType: 'text'
        });

        // create a new progress-subject for every file

        // send the http-request and subscribe for progress-updates
        this.http.request(req).subscribe(event => {
          if (event.type === HttpEventType.UploadProgress) {
            // calculate the progress percentage
            const percentDone = Math.round((100 * event.loaded) / event.total);
            // pass the percentage into the progress-stream
            value.progressEvent.next(percentDone);
          } else if (event instanceof HttpResponse) {
            // Close the progress-stream if we get an answer form the API
            // The upload is complete
            value.progressEvent.complete();
          }
        });
      }
    }
  }
}
