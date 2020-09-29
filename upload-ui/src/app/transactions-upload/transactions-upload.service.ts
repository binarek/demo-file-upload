import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from  '@angular/common/http';

import { Observable } from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class TransactionsUploadService {

  constructor(private httpClient: HttpClient) { }

  public upload(fileToUpload: File): Observable<HttpEvent<string[]>> {  // TODO typed errors
    console.log("SEND");
    console.log(fileToUpload);

    const formData = new FormData();
    formData.append('file', fileToUpload, fileToUpload.name);

    return this.httpClient.post<string[]>('/api/transactions/upload', formData, { reportProgress: true, responseType: 'json', observe: 'events' });
        // .map(() => { return true; });
  }
}
