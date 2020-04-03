import { AccessTokenInterceptor } from './access-token-interceptor';
import { CrudService } from './crud.service';
import { ConfigService } from './config.service';
import { NgModule } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { LitigeService } from './litige.service';
import { UserService } from './user.service';
import { JwtHelperService, JwtModule, JwtModuleOptions } from '@auth0/angular-jwt';

const JWT_Module_Options: JwtModuleOptions = {
  config: {
  }
};

@NgModule({
  providers: [AuthenticationService, ConfigService, CrudService, LitigeService, 
    AccessTokenInterceptor, UserService, JwtHelperService],
    imports: [JwtModule.forRoot(JWT_Module_Options)]
})
export class ServiceModule { }
