import { ClaimCreateComponent } from './signin-claimant/claim-create/claim-create.component';
import { AddressComponent } from './signin-claimant/address/address.component';

import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { SigninClaimantComponent } from './signin-claimant/signin-claimant.component';

@NgModule({
  declarations: [AddressComponent, ClaimCreateComponent, SigninClaimantComponent],
  imports: [SharedModule]
})
export class PublicModule {}
