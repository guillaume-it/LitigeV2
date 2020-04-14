import { ClaimEditModule } from './claim-edit';
import { NgModule } from '@angular/core';
import { ClaimantListModule } from './claim-list';
import { ClaimRoutingModule } from './claim-routing.module';

@NgModule({
  imports: [ClaimRoutingModule, ClaimEditModule, ClaimantListModule]
})
export class ClaimModule {}
