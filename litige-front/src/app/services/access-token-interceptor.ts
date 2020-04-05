import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { ConfigService } from './config.service';
import { retry, catchError, delay, map, flatMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AccessTokenInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService, private config: ConfigService) {
  }

  /**
   * 
   * @param request 
   * @param next 
   */
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Url without need a token
    if (!this.interceptUrl(request)) {
      console.log(`skip token interceptor for ${request.urlWithParams}`);
      return next.handle(request);
    }


    request = this.addAuthenticationToken(request);

    //   return next.handle(request);
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        //   request.url.includes("refreshtoken") ||
        console.log('catch 1');
        //   request.url.includes("login")
        //   if (request.url.includes("refreshtoken")) {
        // invalid_token

        if (error.status === 401) {
          // 401 handled in auth.interceptor
          return this.authenticationService.refreshToken()
            .pipe(map(token => {
              return this.addAuthenticationToken(request);
            }));
        }

        return throwError(error);

      }
      ));
  }

  /**
 * Test if the interceptor must intercep the request
 * @param req 
 */
  interceptUrl(req: HttpRequest<any>): boolean {
    if (req.url.startsWith(this.config.config.authUrl + '/oauth/logout')) {
      return false;
    }

    if (req.url.startsWith(this.config.config.serverUrl) &&
      !req.url.startsWith(this.config.config.authUrl) &&
      !req.headers.get('Authorization')) {
      return true;
    }

    return false;

  }

  addAuthenticationToken(request) {
    // Get access token from Local Storage
    const accessToken = this.authenticationService.accessTokenValue;

    // If access token is null this means that user is not logged in
    // And we return the original request
    if (!accessToken) {
      return request;
    }

    // We clone the request, because the original request is immutable
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.authenticationService.accessTokenValue}`
      }
    });
  }

}
