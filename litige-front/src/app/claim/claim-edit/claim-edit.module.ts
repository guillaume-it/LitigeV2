import { SharedModule } from '../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClaimEditComponent } from './claim-edit.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [ClaimEditComponent],
  exports: [ClaimEditComponent]
})
export class ClaimEditModule {}
