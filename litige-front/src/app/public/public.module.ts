import { SigninClaimantComponent } from './signin-claimant/signin-claimant.component';
import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { LoginComponent } from './login/login.component';
import { SigninComponent } from './signin/signin.component';

@NgModule({
  declarations: [LoginComponent, SigninComponent, SigninClaimantComponent],
  imports: [SharedModule]
})
export class PublicModule {}
