import { Subject } from 'rxjs';
import * as moment from 'moment';

export class FileInformation {
  id: number;
  progress = 0;
  loaded = false;
  progressEvent = new Subject<number>();
  name: string;
  uri: string;
  contentType: string;
  size: number;

  constructor(public file?: File) {
    if (file) {
      this.name = this.file.name;
      this.size = this.file.size;
      this.contentType = this.file.type;

      this.progressEvent.subscribe(progress => {
        if (progress === 100) {
          this.loaded = true;
        }
      });
    }
  }
}
