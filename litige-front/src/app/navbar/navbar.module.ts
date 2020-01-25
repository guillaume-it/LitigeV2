import { SharedModule } from 'src/app/shared';
import { NavbarComponent } from './navbar.component';
import { NgModule } from '@angular/core';
import { MatToolbarModule, MatButtonModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [NavbarComponent],
  imports: [CommonModule, MatToolbarModule, MatButtonModule, RouterModule, SharedModule],
  exports: [NavbarComponent]
})
export class NavBarModule {}
