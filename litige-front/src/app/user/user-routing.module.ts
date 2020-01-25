import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Role } from 'src/app/models';
import { AuthGuard } from 'src/app/auth.guard';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: 'login', loadChildren: './login/login.module#LoginModule' },
      { path: 'signin-agent', loadChildren: './signin-agent/signin-agent.module#SigninAgentModule' },
      { path: 'signin-claimant', loadChildren: './identification/identification.module#IdentificationModule' },
      {
        path: 'send-email-validation',
        loadChildren: './send-email-alidation/send-email-validation.module#SendEmailValidationModule'
      },
      {
        path: 'link-validation-email/:email/:token',
        loadChildren: './link-validation-email/link-validation-email.module#LoginModule'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SigninRoutingModule {}
