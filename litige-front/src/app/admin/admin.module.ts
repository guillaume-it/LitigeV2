import { UserModule } from './../user/user.module';
import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { AdminRoutingModule } from './admin-routing.module';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UsersComponent } from './users/users.component';

@NgModule({
  declarations: [UsersComponent, UserDetailComponent],
  imports: [SharedModule, AdminRoutingModule, UserModule],
  entryComponents: [UserDetailComponent]
})
export class AdminModule {}
