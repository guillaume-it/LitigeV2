import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClaimCreateComponent } from './claim-create.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [ClaimCreateComponent],
  exports: [ClaimCreateComponent]
})
export class ClaimCreateModule {}
