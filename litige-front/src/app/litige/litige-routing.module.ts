import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../auth.guard';
import { Role } from '../models/user';
import { LitigeListComponent } from './litige-list/litige-list.component';

const routes: Routes = [
  {
    path: '',
    // data: { roles: [Role.USER, Role.ADMIN] },
    //canActivateChild: [AuthGuard],
    children: [{ path: 'litige', component: LitigeListComponent }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LitigeRoutingModule {}
