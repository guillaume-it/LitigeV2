import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LitigeListComponent } from './litige-list.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [LitigeListComponent],
  imports: [CommonModule, SharedModule, RouterModule],
  exports: [LitigeListComponent]
})
export class LitigeListModule {}
