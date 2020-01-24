import { IdentificationComponent } from './public/signin-claimant/identification/identification.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { LoginComponent } from './public/login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { SigninComponent } from './public/signin/signin.component';
import { Role } from './models/user';
import { SigninClaimantComponent } from './public/signin-claimant/signin-claimant.component';
import { SendEmailValidationComponent } from './public/signin-claimant/send-email-validation/send-email-validation.component';

const routes: Routes = [
  {
    path: '',
    data: { roles: [Role.ADMIN, Role.AGENT, Role.CLAIMANT] },
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      {
        path: 'profile',
        component: ProfileComponent,
        data: { roles: [Role.ADMIN, Role.AGENT, Role.CLAIMANT] }
      },
      { path: 'admin', loadChildren: './admin/admin.module#AdminModule' },
      { path: 'claim', loadChildren: './claim/claim.module#ClaimModule' },
      { path: '', loadChildren: './litige/litige.module#LitigeModule' }
    ]
  },
  {
    path: '',
    canActivateChild: [AuthGuard],
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'signin', component: SigninComponent },
      { path: 'signin-claimant', component: IdentificationComponent },
      { path: 'validation-email', component: SendEmailValidationComponent }
    ]
  },
  { path: '', redirectTo: '/litige', pathMatch: 'full' },
  { path: '**', redirectTo: '/litige', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
