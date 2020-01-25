import { SharedModule } from 'src/app/shared';
import { IdentificationComponent } from './identification.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { Role } from 'src/app/models';
import { AuthGuard } from 'src/app/auth.guard';

const route: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    component: IdentificationComponent
  }
];
@NgModule({
  imports: [CommonModule, RouterModule.forChild(route), SharedModule],
  declarations: [IdentificationComponent],
  exports: [IdentificationComponent]
})
export class IdentificationModule {}
