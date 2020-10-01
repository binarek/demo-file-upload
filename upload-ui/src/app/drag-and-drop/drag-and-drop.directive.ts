import {Directive, EventEmitter, HostBinding, HostListener, Output} from '@angular/core';

@Directive({
  selector: '[appDragAndDrop]'
})
export class DragAndDropDirective {

  constructor() {
  }

  @HostBinding('class.drag-over')
  fileOver = false;

  @Output()
  fileDropped = new EventEmitter<FileList>();

  @HostListener('dragover', ['$event'])
  onDragOver(event: Event) {
    DragAndDropDirective.disableEvent(event);
    this.fileOver = true;
  }

  @HostListener('dragleave', ['$event'])
  onDragLeave(event: Event) {
    DragAndDropDirective.disableEvent(event);
    this.fileOver = false;
  }

  @HostListener('drop', ['$event'])
  onDrop(event: DragEvent) {
    DragAndDropDirective.disableEvent(event);
    this.fileOver = false;

    const files = DragAndDropDirective.getFiles(event);
    if (files && files.length > 0) {
      this.fileDropped.emit(files);
    }
  }

  private static disableEvent(event: Event) {
    event.preventDefault();
    event.stopPropagation();
  }

  private static getFiles(event: DragEvent): FileList | null {
    if (event.dataTransfer) {
      return event.dataTransfer.files;
    }
    return null;
  }
}
