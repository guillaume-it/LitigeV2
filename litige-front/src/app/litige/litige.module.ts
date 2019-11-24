import { LitigeComponent } from './litige/litige.component';
import { NgModule } from '@angular/core';

import { SharedModule } from '../shared/shared.module';

import { LitigeRoutingModule } from './litige-routing.module';

@NgModule({
  declarations: [LitigeComponent],

  imports: [SharedModule, LitigeRoutingModule]
})
export class LitigeModule {}
