import { Component, OnInit, Input } from '@angular/core';
import { FileStatus } from 'src/app/models/file-status';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.scss']
})
export class ProgressBarComponent implements OnInit {
  @Input()
  fileStatus: FileStatus;

  progress = 0;

  constructor() {}

  ngOnInit() {
    if (this.fileStatus && this.fileStatus.progressEvent) {
      this.fileStatus.progressEvent.subscribe(progress => {
        this.progress = progress;
      });
    }
  }
}
