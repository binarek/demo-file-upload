import { TestBed } from '@angular/core/testing';

import { TransactionsUploadService } from './transactions-upload.service';

describe('TransactionsUploadService', () => {
  let service: TransactionsUploadService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransactionsUploadService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
