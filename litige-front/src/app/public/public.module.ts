import { ClaimCreateComponent } from './signin-claimant/claim-create/claim-create.component';
import { AddressComponent } from './signin-claimant/address/address.component';

import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [AddressComponent, ClaimCreateComponent],
  imports: [SharedModule]
})
export class PublicModule {}
