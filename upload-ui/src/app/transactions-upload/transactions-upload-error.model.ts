export class TransactionsUploadError {

  constructor(public lineNumber: number,
              public errors: string[]) { }
}
