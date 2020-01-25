import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SigninRoutingModule } from './user-routing.module';
import { LoginModule } from './login';
import { SigninAgentModule } from './signin-agent';
import { SendEmailValidationModule } from './send-email-validation';
import { LinkEmailValidationModule } from './link-email-validation';

@NgModule({
  imports: [
    CommonModule,
    SigninRoutingModule,
    SigninAgentModule,
    SendEmailValidationModule,
    LinkEmailValidationModule,
    LoginModule
  ],
  exports: [SigninRoutingModule, SigninAgentModule, SendEmailValidationModule, LinkEmailValidationModule, LoginModule]
})
export class UserModule {}
