import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { Role } from './models/user';
// https://angular.io/guide/router#preloading-background-loading-of-feature-areas
const routes: Routes = [
  {
    path: '',
    data: { roles: [Role.ADMIN, Role.AGENT, Role.CLAIMANT] },
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      { path: 'admin', loadChildren: './admin/admin.module#AdminModule' },
      { path: 'claim', loadChildren: './claim/claim.module#ClaimModule' }
    ]
  },
  {
    path: '',
    children: [{ path: 'user', loadChildren: './user/user.module#UserModule' }]
  },
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
