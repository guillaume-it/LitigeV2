import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: 'login', loadChildren: () => import('./login/login.module').then(m => m.LoginModule) },
      {
        path: 'signin-agent',
        loadChildren: () => import('./signin-agent/signin-agent.module').then(m => m.SigninAgentModule)
      },
      {
        path: 'signin-claimant',
        loadChildren: () => import('./identification/identification.module').then(m => m.IdentificationModule)
      },
      {
        path: 'send-email-validation',
        loadChildren: () =>
          import('./send-email-validation/send-email-validation.module').then(m => m.SendEmailValidationModule)
      },
      {
        path: 'link-validation-email/:email/:token',
        loadChildren: () =>
          import('./link-email-validation/link-email-validation.module').then(m => m.LinkEmailValidationModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SigninRoutingModule {}
