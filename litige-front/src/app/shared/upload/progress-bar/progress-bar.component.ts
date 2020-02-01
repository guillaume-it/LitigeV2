import { Component, OnInit, Input } from '@angular/core';
import { FileInformation } from 'src/app/models/file-information';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.scss']
})
export class ProgressBarComponent implements OnInit {
  @Input()
  fileStatus: FileInformation;

  progress = 0;

  constructor() {}

  ngOnInit() {
    if (this.fileStatus) {
      if (this.fileStatus.loaded) {
        this.progress = 100;
      }
      if (this.fileStatus.progressEvent) {
        this.fileStatus.progressEvent.subscribe(progress => {
          this.progress = progress;
        });
      }
    }
  }
}
