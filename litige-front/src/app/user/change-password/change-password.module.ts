import { SharedModule } from '../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangePasswordComponent } from './change-password.component';

@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [ChangePasswordComponent],
  exports: [ChangePasswordComponent],
  entryComponents: [ChangePasswordComponent]
})
export class ChangePasswordModule {}
