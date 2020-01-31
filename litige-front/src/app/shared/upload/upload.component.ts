import { FileStatus } from './../../models/file-status';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss']
})
export class UploadComponent implements OnInit {
  @Output()
  selectFilesEvent = new EventEmitter<Map<string, FileStatus>>();
  @Output()
  deleteFileEvent = new EventEmitter<FileStatus>();

  filesStatus = new Map<string, FileStatus>();

  constructor() {}

  ngOnInit() {}

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
      this.filesStatus.set(item.name, new FileStatus(item));
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
