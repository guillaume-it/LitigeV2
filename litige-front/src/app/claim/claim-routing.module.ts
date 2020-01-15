import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Role } from '../models';
import { AuthGuard } from '../auth.guard';
import { ClaimDetailComponent } from './claim-detail';
import { ClaimListComponent } from './claim-list';

const routes: Routes = [
  {
    path: '',
    data: { roles: [Role.CLAIMANT, Role.ADMIN, Role.USER, Role.USER_MANAGER] },
    canActivateChild: [AuthGuard],
    children: [
      {
        path: '',
        component: ClaimListComponent
      },
      {
        path: '{id}',
        component: ClaimDetailComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClaimRoutingModule {}