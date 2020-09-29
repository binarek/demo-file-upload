import { Component, OnInit } from '@angular/core';
import { HttpEvent, HttpEventType } from '@angular/common/http';

import { TransactionsUploadService } from './transactions-upload.service';

@Component({
  selector: 'app-transactions-upload',
  templateUrl: './transactions-upload.component.html',
  styleUrls: ['./transactions-upload.component.scss']
})
export class TransactionsUploadComponent implements OnInit {

  constructor(private transactionsUploadService: TransactionsUploadService) { }
  
  progress: number = 0;
  uploadMessages: string[] = [];

  private fileToUpload: File | null = null;

  ngOnInit(): void {
  }

  selectFile(event: Event) {
    this.resetUpload();

    const target = event.target as HTMLInputElement;
    if (target.files) {
      this.fileToUpload = target.files[0];
    }
  }

  upload() {
    if (this.fileToUpload != null) {
      this.transactionsUploadService.upload(this.fileToUpload)
          .subscribe(event => this.handleEvent(event))
    }
  }
  
  private resetUpload() {
    this.progress = 0;
    this.uploadMessages = [];
  }

  private handleEvent(event: HttpEvent<string[]>) {
    if (event.type === HttpEventType.UploadProgress && event.total) {
      this.progress = Math.round(100 * event.loaded / event.total);

    } else if (event.type === HttpEventType.Response && event.body) {
      this.uploadMessages = event.body;
    }
  }
}
