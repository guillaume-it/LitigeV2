import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { RoleEnum } from './models/role-enum';

// https://angular.io/guide/router#preloading-background-loading-of-feature-areas
const routes: Routes = [
  {
    path: '',
    data: { roles: [RoleEnum.ADMIN, RoleEnum.AGENT, RoleEnum.CLAIMANT] },
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) },
      { path: 'claim', loadChildren: () => import('./claim/claim.module').then(m => m.ClaimModule) }
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
