import { SharedModule } from 'src/app/shared';
import { IdentificationComponent } from './identification.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

const route: Routes = [
  {
    path: '',
    component: IdentificationComponent
  }
];
@NgModule({
  imports: [CommonModule, RouterModule.forChild(route), SharedModule, MatProgressSpinnerModule],
  declarations: [IdentificationComponent],
  exports: [IdentificationComponent]
})
export class IdentificationModule {}
