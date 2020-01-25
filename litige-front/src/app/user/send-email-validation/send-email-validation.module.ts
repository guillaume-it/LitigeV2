import { SendEmailValidationComponent } from './send-email-validation.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { Role } from 'src/app/models';
import { AuthGuard } from 'src/app/auth.guard';
import { SharedModule } from 'src/app/shared';

const route: Routes = [
  {
    path: '',
    data: { roles: [Role.CLAIMANT, Role.ADMIN, Role.AGENT] },
    canActivateChild: [AuthGuard],
    component: SendEmailValidationComponent
  }
];
@NgModule({
  imports: [CommonModule, RouterModule.forChild(route), SharedModule],
  declarations: [SendEmailValidationComponent]
})
export class SendEmailValidationModule {}
