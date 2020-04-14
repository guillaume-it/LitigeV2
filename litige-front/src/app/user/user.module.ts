import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SigninRoutingModule } from './user-routing.module';
import { LoginModule } from './login';
import { SigninAgentModule } from './signin-agent';
import { SendEmailValidationModule } from './send-email-validation';
import { LinkEmailValidationModule } from './link-email-validation';
import { ChangePasswordModule } from './change-password';

@NgModule({
  imports: [
    CommonModule,
    SigninRoutingModule,
    SigninAgentModule,
    SendEmailValidationModule,
    LinkEmailValidationModule,
    LoginModule,
    ChangePasswordModule
  ],
  exports: [
    SigninRoutingModule,
    SigninAgentModule,
    SendEmailValidationModule,
    LinkEmailValidationModule,
    LoginModule,
    ChangePasswordModule
  ]
})
export class UserModule {}
