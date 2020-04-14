import { NgModule } from '@angular/core';
import { LoginComponent } from './login.component';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'src/app/auth.guard';
import { SharedModule } from 'src/app/shared';

const route: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    component: LoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(route), SharedModule],
  declarations: [LoginComponent],
  exports: [LoginComponent]
})
export class LoginModule {}
