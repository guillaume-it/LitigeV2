import { UploadComponent } from './upload.component';
import { SharedModule } from '../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [UploadComponent],
  exports: [UploadComponent]
})
export class UploadModule {}
