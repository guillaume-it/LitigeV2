import { FileInformation } from '../models/file-information';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';
import { HttpResponse, HttpRequest, HttpEventType, HttpClient } from '@angular/common/http';
import { url } from './url';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  constructor(private http: HttpClient) {}
}
