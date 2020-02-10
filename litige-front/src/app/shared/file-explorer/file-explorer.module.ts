import { FileExplorerComponent } from './file-explorer.component';
import { NgBytesPipeModule } from 'angular-pipes';

import { SharedModule } from '../shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileIconComponent } from './file-icon';

@NgModule({
  imports: [CommonModule, SharedModule, NgBytesPipeModule],
  declarations: [FileExplorerComponent, FileIconComponent],
  exports: [FileExplorerComponent]
})
export class FileExplorerModule {}
