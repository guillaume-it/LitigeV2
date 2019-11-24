import { LitigeListComponent } from './litige-list/litige-list.component';
import { LitigeDetailComponent } from './litige-detail/litige-detail.component';

import { NgModule } from '@angular/core';

import { SharedModule } from '../shared/shared.module';

import { LitigeRoutingModule } from './litige-routing.module';

@NgModule({
  declarations: [LitigeDetailComponent, LitigeListComponent],

  imports: [SharedModule, LitigeRoutingModule]
})
export class LitigeModule {}
