import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';

import { TransactionsUploadService } from './transactions-upload.service';
import { TransactionsUploadError } from "./transactions-upload-error.model";

@Component({
  selector: 'app-transactions-upload',
  templateUrl: './transactions-upload.component.html',
  styleUrls: ['./transactions-upload.component.scss']
})
export class TransactionsUploadComponent implements OnInit {

  constructor(private transactionsUploadService: TransactionsUploadService) { }

  progress: number = 0;
  uploadMessages: TransactionsUploadError[] = [];
  fileToUpload: File | null = null;

  ngOnInit(): void {
  }

  selectFile(files: FileList | null) {
    if (files && files.length > 0) {
      this.resetUpload();
      this.fileToUpload = files[0];
    }
  }

  upload() {
    if (this.fileToUpload != null) {
      this.transactionsUploadService.upload(this.fileToUpload)
        .subscribe(
          event => this.handleEvent(event),
          errorResponse => this.handleError(errorResponse)
        )
    }
  }

  private resetUpload() {
    this.progress = 0;
    this.uploadMessages = [];
  }

  private handleError(errorResponse: HttpErrorResponse) {
    this.uploadMessages = errorResponse.error;
  }

  private handleEvent(event: HttpEvent<TransactionsUploadError[]>) {
    if (event.type === HttpEventType.UploadProgress && event.total) {
      this.progress = Math.round(100 * event.loaded / event.total);

    } else if (event.type === HttpEventType.Response && event.body) {
      this.uploadMessages = event.body;
    }
  }
}
