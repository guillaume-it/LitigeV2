import { FileInformation } from 'src/app/models/file-information';
import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { State, ScreenState } from 'src/app/models/state';

@Component({
  selector: 'app-file-icon',
  templateUrl: './file-icon.component.html',
  styleUrls: ['./file-icon.component.scss']
})
export class FileIconComponent implements OnInit {
  @Input()
  fileInformation: FileInformation;
  @Input()
  stateEvent: EventEmitter<State>;
  @Input()
  state: State;
  @Output()
  download = new EventEmitter<FileInformation>();

  screenState = ScreenState;
  constructor() {}

  ngOnInit() {
    if (this.stateEvent) {
      this.stateEvent.subscribe(state => {
        this.state = state;
      });
    }
  }

  deleteFile(name: string) {}
  downloadFile() {
    this.download.emit(this.fileInformation);
  }
}
