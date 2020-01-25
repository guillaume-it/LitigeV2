import { LinkEmailValidationComponent } from './link-email-validation.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule, MatFormFieldModule, MatInputModule } from '@angular/material';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { Role } from 'src/app/models';
import { AuthGuard } from 'src/app/auth.guard';

const route: Routes = [
  {
    path: '',
    data: { roles: [Role.CLAIMANT, Role.ADMIN, Role.AGENT] },
    canActivateChild: [AuthGuard],
    component: LinkEmailValidationComponent
  }
];
@NgModule({
  imports: [CommonModule, RouterModule.forChild(route), MatCardModule, MatFormFieldModule, FormsModule, MatInputModule],
  declarations: [LinkEmailValidationComponent],
  exports: [LinkEmailValidationComponent, MatCardModule, MatFormFieldModule, FormsModule]
})
export class LinkEmailValidationModule {}
