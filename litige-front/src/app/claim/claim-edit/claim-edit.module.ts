import { FileExplorerModule } from './../../shared/file-explorer/file-explorer.module';
import { UploadModule } from './../../shared/upload/upload.module';
import { SharedModule } from '../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClaimEditComponent } from './claim-edit.component';

@NgModule({
  imports: [CommonModule, SharedModule, UploadModule, FileExplorerModule],
  declarations: [ClaimEditComponent],
  exports: [ClaimEditComponent]
})
export class ClaimEditModule {}
