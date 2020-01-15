import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LitigeListComponent } from './litige-list.component';

@NgModule({
  declarations: [LitigeListComponent],
  imports: [CommonModule, SharedModule],
  exports: [LitigeListComponent]
})
export class LitigeListModule {}
