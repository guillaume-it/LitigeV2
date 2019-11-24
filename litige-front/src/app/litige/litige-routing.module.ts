import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../auth.guard';
import { Role } from '../models/user';
import { LitigeComponent } from './litige/litige.component';

const routes: Routes = [
  {
    path: '',
    data: { roles: [Role.USER, Role.ADMIN] },
    canActivateChild: [AuthGuard],
    children: [{ path: 'litige', component: LitigeComponent }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LitigeRoutingModule {}
