import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../auth.guard';
import { ClaimListComponent } from './claim-list';
import { ClaimEditComponent } from './claim-edit';
import { ScreenState } from '../models/state';
import { RoleEnum } from '../models/role-enum';

const routes: Routes = [
  {
    path: '',
    data: { roles: [RoleEnum.CLAIMANT, RoleEnum.ADMIN, RoleEnum.AGENT] },
    canActivateChild: [AuthGuard],
    children: [
      {
        path: 'list',
        component: ClaimListComponent
      },
      {
        path: ScreenState.DETAIL + '/:id',
        component: ClaimEditComponent
      },
      {
        path: ScreenState.CREATE,
        component: ClaimEditComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClaimRoutingModule {}
