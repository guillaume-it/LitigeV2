import { ClaimCreateComponent } from './signin-claimant/claim-create/claim-create.component';
import { IdentificationComponent } from './signin-claimant/identification/identification.component';
import { SigninClaimantComponent } from './signin-claimant/signin-claimant.component';
import { AddressComponent } from './signin-claimant/address/address.component';

import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { LoginComponent } from './login/login.component';
import { SigninComponent } from './signin/signin.component';
import { SendEmailValidationComponent } from './signin-claimant/send-email-validation/send-email-validation.component';

@NgModule({
  declarations: [
    LoginComponent,
    SigninComponent,
    SigninClaimantComponent,
    IdentificationComponent,
    AddressComponent,
    ClaimCreateComponent,
    SendEmailValidationComponent
  ],
  imports: [SharedModule]
})
export class PublicModule {}
