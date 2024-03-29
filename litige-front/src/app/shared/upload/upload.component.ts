import { LoadingState } from './../../models/state';
import { FileInformation } from '../../models/file-information';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { ScreenState, State } from 'src/app/models/state';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss']
})
export class UploadComponent implements OnInit {
  @Output()
  selectFilesEvent = new EventEmitter<Map<string, FileInformation>>();
  @Output()
  deleteFileEvent = new EventEmitter<FileInformation>();
  @Input()
  stateEvent: EventEmitter<State>;
  @Input()
  state = new State();
  screenState = ScreenState;
  loadingState = LoadingState;

  filesStatus = new Map<string, FileInformation>();

  constructor() {}

  ngOnInit() {
    if (this.stateEvent) {
      this.stateEvent.subscribe(state => {
        this.state = state;
      });
    }
  }

  /**
   * on file drop handler
   */
  onFileDropped($event) {
    this.prepareFilesList($event);
  }

  /**
   * handle file from browsing
   */
  fileBrowseHandler(files) {
    this.prepareFilesList(files);
  }

  /**
   * Delete file from files list
   * @param index (File index)
   */
  deleteFile(name: string) {
    const file = this.filesStatus.get(name);
    if (this.filesStatus.delete(name)) {
      this.deleteFileEvent.next(file);
    }
  }

  /**
   * Convert Files list to a map
   * @param files (Files List)
   */
  prepareFilesList(files: Set<File>) {
    for (const item of files) {
      this.filesStatus.set(item.name, new FileInformation(item));
    }
    this.selectFilesEvent.next(this.filesStatus);
  }

  /**
   * format bytes
   * @param bytes (File size in bytes)
   * @param decimals (Decimals point)
   */
  formatBytes(bytes, decimals) {
    if (bytes === 0) {
      return '0 Bytes';
    }
    const k = 1024;
    const dm = decimals <= 0 ? 0 : decimals || 2;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }
}
