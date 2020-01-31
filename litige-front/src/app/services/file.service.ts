import { FileStatus } from './../models/file-status';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpResponse, HttpRequest, HttpEventType, HttpClient } from '@angular/common/http';
import { url } from './url';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  constructor(private http: HttpClient) {}

  public upload(files: Map<string, FileStatus>, idClaim: number) {
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
