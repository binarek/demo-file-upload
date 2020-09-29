import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { TransactionsUploadComponent } from './transactions-upload/transactions-upload.component';

@NgModule({
  declarations: [
    AppComponent,
    TransactionsComponent,
    TransactionsUploadComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
