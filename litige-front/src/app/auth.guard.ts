import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from './models/user';
import { AuthenticationService } from './services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {
  constructor(private authentication: AuthenticationService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    return this.canActivateRoute(route, state);
  }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.canActivateRoute(childRoute, state);
  }

  private canActivateRoute(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.authentication.loggedUser$.pipe(
      map(loggedUser => {
        const res = this.checkRoute(route, state, loggedUser);
        return res;
      })
    );
  }

  private checkRoute(route: ActivatedRouteSnapshot, state: RouterStateSnapshot, user: User): boolean {
    if (
      (!route.data.roles && !user) ||
      (user && (!user.role || (user.role && user.role.length > 0 && route.data.roles.includes(user.role))))
    ) {
      return true;
    }
    return false;
  }
}
