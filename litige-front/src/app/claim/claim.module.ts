import { NgModule } from '@angular/core';
import { ClaimDetailModule } from './claim-detail';
import { ClaimantListModule } from './claim-list';
import { ClaimRoutingModule } from './claim-routing.module';

@NgModule({
  imports: [ClaimRoutingModule, ClaimDetailModule, ClaimantListModule]
})
export class ClaimModule {}
