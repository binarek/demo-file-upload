import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { TransactionsUploadComponent } from './transactions-upload/transactions-upload.component';
import { DragAndDropDirective } from './drag-and-drop/drag-and-drop.directive';
import { AlertComponent } from './alert/alert.component';
import {NgBytesPipeModule} from "angular-pipes";

@NgModule({
  declarations: [
    AppComponent,
    TransactionsUploadComponent,
    DragAndDropDirective,
    AlertComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    NgBytesPipeModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
