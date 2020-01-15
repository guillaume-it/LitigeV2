import { NgModule } from '@angular/core';
import { ClaimListComponent } from './claim-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [ClaimListComponent],
  imports: [CommonModule, SharedModule],
  exports: [ClaimListComponent]
})
export class ClaimantListModule {}
