import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Role } from '../models';
import { AuthGuard } from '../auth.guard';
import { ClaimDetailComponent } from './claim-detail';
import { ClaimListComponent } from './claim-list';
import { ClaimCreateComponent } from './claim-create';

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
      },
      {
        path: 'create',
        component: ClaimCreateComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClaimRoutingModule {}
