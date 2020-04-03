import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { ConfigService } from './config.service';
import { retry, catchError } from 'rxjs/operators';

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

    const accessToken = this.authenticationService.accessTokenValue;
    if (accessToken) {
      request = this.addToken(request, accessToken);
    }

    return next.handle(request).pipe(
      retry(3),
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401 && error.error.error === 'invalid_token') {
          // 401 handled in auth.interceptor
          this.authenticationService.refreshToken().subscribe(accessToken => {
            console.log("Refresh token interceptor: ", request);
          });
        } else {
          // auto logout if 401 response returned from api
          this.authenticationService.logout();
          location.reload(true);
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
  /**
   * Add the token in the header of the request.
   * 
   * @param request 
   * @param token 
   */
  private addToken(request: HttpRequest<any>, token: string): HttpRequest<any> {
    return request.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
  }
}
