import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpEventType} from '@angular/common/http';

import {TransactionsUploadService} from './transactions-upload.service';
import {TransactionsUploadError} from "./transactions-upload-error.model";

@Component({
  selector: 'app-transactions-upload',
  templateUrl: './transactions-upload.component.html',
  styleUrls: ['./transactions-upload.component.scss']
})
export class TransactionsUploadComponent implements OnInit {

  constructor(private transactionsUploadService: TransactionsUploadService) {
  }

  fileToUpload: File | null = null;

  uploadProgress: number = 0;
  uploadStatus: 'success' | 'error' | null = null;
  uploadStatusMessage = '';
  uploadFileLineErrors: TransactionsUploadError[] = [];

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
      this.resetUpload();
      this.transactionsUploadService.upload(this.fileToUpload)
        .subscribe(
          event => this.handleUploadEvent(event),
          errorResponse => this.handleUploadError(errorResponse)
        )
    }
  }

  private resetUpload() {
    this.uploadStatus = null;
    this.uploadProgress = 0;
    this.uploadStatusMessage = '';
    this.uploadFileLineErrors = [];
  }

  private handleUploadEvent(event: HttpEvent<null>) {
    if (event.type === HttpEventType.UploadProgress && event.total) {
      this.uploadProgress = Math.round(100 * event.loaded / event.total);

    } else if (event.type === HttpEventType.Response) {
      this.uploadStatusMessage = 'File successfully uploaded';
      this.uploadStatus = 'success';
    }
  }

  private handleUploadError(res: HttpErrorResponse) {
    this.uploadStatusMessage = TransactionsUploadComponent.buildUploadStatusMessage(res);
    this.uploadFileLineErrors = TransactionsUploadComponent.buildUploadFileLineErrors(res);
    this.uploadStatus = 'error';
  }

  private static buildUploadStatusMessage(res: HttpErrorResponse): string {
    const standardMessage = 'Cannot upload file';
    if (res.error && res.error.detail) {
      return standardMessage + `: ${res.error.detail}`;
    } else {
      return standardMessage + ': Something went wrong, please contact the Admin';
    }
  }

  private static buildUploadFileLineErrors(res: HttpErrorResponse): TransactionsUploadError[] {
    if (res.error && res.error.fileLineErrors) {
      return res.error.fileLineErrors;
    }
    return [];
  }
}
