import { SharedModule } from 'src/app/shared';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SigninAgentComponent } from './signin-agent.component';
import { Routes, RouterModule } from '@angular/router';

const route: Routes = [
  {
    path: '',
    component: SigninAgentComponent
  }
];
@NgModule({
  imports: [CommonModule, RouterModule.forChild(route), SharedModule],
  declarations: [SigninAgentComponent],
  exports: [SigninAgentComponent]
})
export class SigninAgentModule {}
