import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { FileInformation } from 'src/app/models/file-information';
import { State, ScreenState } from 'src/app/models/state';

@Component({
  selector: 'app-file-explorer',
  templateUrl: './file-explorer.component.html',
  styleUrls: ['./file-explorer.component.scss']
})
export class FileExplorerComponent implements OnInit {
  @Input()
  filesStatus: Map<string, FileInformation>;
  @Input()
  stateEvent: EventEmitter<State>;
  @Input()
  state: State;

  screenState = ScreenState;
  constructor() {}

  ngOnInit() {
    if (this.stateEvent) {
      this.stateEvent.subscribe(state => {
        this.state = state;
      });
    }
  }
}
