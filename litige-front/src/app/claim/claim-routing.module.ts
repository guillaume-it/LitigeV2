import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Role } from '../models';
import { AuthGuard } from '../auth.guard';
import { ClaimListComponent } from './claim-list';
import { ClaimEditComponent } from './claim-edit';
import { ScreenState } from '../models/screen-state';

const routes: Routes = [
  {
    path: '',
    data: { roles: [Role.CLAIMANT, Role.ADMIN, Role.AGENT] },
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
