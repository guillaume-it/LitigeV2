import { LitigeListModule } from './litige-list/litige-list.module';
import { LitigeDetailModule } from './litige-detail/litige-detail.module';
import { NgModule } from '@angular/core';
import { LitigeRoutingModule } from './litige-routing.module';

@NgModule({
  imports: [LitigeRoutingModule, LitigeDetailModule, LitigeListModule]
})
export class LitigeModule {}
