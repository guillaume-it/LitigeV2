import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './login.component';
import { Routes, RouterModule } from '@angular/router';
import { Role } from 'src/app/models';
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
