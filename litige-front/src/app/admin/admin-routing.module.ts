import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Role } from '../models';
import { AuthGuard } from '../auth.guard';
import { UsersComponent } from './users/users.component';

const routes: Routes = [
  {
    path: '',
    data: { roles: [Role.ADMIN, Role.ADMIN] },
    canActivateChild: [AuthGuard],
    children: [{ path: 'users', component: UsersComponent }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {}
