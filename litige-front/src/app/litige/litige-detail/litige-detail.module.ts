import { LitigeDetailComponent } from './litige-detail.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared';

@NgModule({
  declarations: [LitigeDetailComponent],
  imports: [CommonModule, SharedModule],
  exports: [LitigeDetailComponent]
})
export class LitigeDetailModule {}
