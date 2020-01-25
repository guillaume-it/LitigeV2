import { SendEmailValidationComponent } from './send-email-validation.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared';

const route: Routes = [
  {
    path: '',
    component: SendEmailValidationComponent
  }
];
@NgModule({
  imports: [CommonModule, RouterModule.forChild(route), SharedModule],
  declarations: [SendEmailValidationComponent]
})
export class SendEmailValidationModule {}
