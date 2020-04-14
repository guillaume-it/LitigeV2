import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../auth.guard';
import { UsersComponent } from './users/users.component';
import { RoleEnum } from '../models/role-enum';

const routes: Routes = [
  {
    path: '',
    data: { roles: [RoleEnum.ADMIN, RoleEnum.ADMIN] },
    canActivateChild: [AuthGuard],
    children: [{ path: 'users', component: UsersComponent }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
