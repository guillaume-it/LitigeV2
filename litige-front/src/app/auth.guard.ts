import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { Observable } from 'rxjs';
import { User } from './models/user';
import { AuthenticationService } from './services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {
  constructor(private authentication: AuthenticationService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    return this.checkRoute(route, state, this.authentication.currentUserValue);
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.checkRoute(childRoute, state, this.authentication.currentUserValue);
  }

  private checkRoute(route: ActivatedRouteSnapshot, state: RouterStateSnapshot, user: User): boolean {

    if (
      !route.data.roles
      ||
      (user
        &&
        (user.roles && user.roles.length > 0
          && route.data.roles.some(rr => user.roles.map(role => role.name).includes(rr))))
    ) {
      return true;
    }
    return false;
  }
}
