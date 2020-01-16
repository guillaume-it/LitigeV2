import { TokenInterceptor } from './token-interceptor';
import { CrudService } from './crud.service';
import { ConfigService } from './config.service';
import { NgModule } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { LitigeService } from './litige.service';
import { UserService } from './user.service';

@NgModule({
  providers: [AuthenticationService, ConfigService, CrudService, LitigeService, TokenInterceptor, UserService]
})
export class ServiceModule {}
