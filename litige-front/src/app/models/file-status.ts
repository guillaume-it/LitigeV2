import { Subject } from 'rxjs';
import * as moment from 'moment';

export class FileStatus {
  id: number;
  progress = 0;
  loaded: boolean;
  progressEvent = new Subject<number>();

  constructor(public file: File) {
    this.id = moment().millisecond();
  }
}
