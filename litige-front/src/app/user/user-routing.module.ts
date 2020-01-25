import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: 'login', loadChildren: './login/login.module#LoginModule' },
      { path: 'signin-agent', loadChildren: './signin-agent/signin-agent.module#SigninAgentModule' },
      { path: 'signin-claimant', loadChildren: './identification/identification.module#IdentificationModule' },
      {
        path: 'send-email-validation',
        loadChildren: './send-email-validation/send-email-validation.module#SendEmailValidationModule'
      },
      {
        path: 'link-validation-email/:email/:token',
        loadChildren: './link-email-validation/link-email-validation.module#LinkEmailValidationModule'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SigninRoutingModule {}
