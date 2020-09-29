import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionsUploadComponent } from './transactions-upload.component';

describe('TransactionsUploadComponent', () => {
  let component: TransactionsUploadComponent;
  let fixture: ComponentFixture<TransactionsUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionsUploadComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionsUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
