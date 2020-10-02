import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';

import { Observable } from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class TransactionsUploadService {

  constructor(private httpClient: HttpClient) { }

  upload(fileToUpload: File): Observable<HttpEvent<null>> {
    const formData = new FormData();
    formData.append('file', fileToUpload, fileToUpload.name);

    return this.httpClient.post<null>('/api/transactions/upload', formData, {
      reportProgress: true,
      responseType: 'json',
      observe: 'events'
    });
  }
}
