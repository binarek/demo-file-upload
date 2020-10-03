import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent implements OnInit {

  constructor() {
  }

  @Input()
  type: 'success' | 'error' = 'success';

  private _message: string = '';

  @Input()
  set message(val: string) {
    this._message = val;
    this.closed = false;
  }

  get message() {
    return this._message;
  }

  closed = false;

  ngOnInit(): void {
  }

  typeName(): string {
    switch (this.type) {
      case 'success':
        return 'Success';
      case 'error':
        return 'Error';
      default:
        return '';
    }
  }
}
