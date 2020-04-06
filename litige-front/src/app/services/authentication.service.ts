import { environment } from '../../environments/environment';
import { CrudService } from './crud.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, Observable, of, EMPTY } from 'rxjs';
import { catchError, map, switchMap, tap, flatMap } from 'rxjs/operators';
import { User } from '../models/user';
import { ConfigService } from './config.service';
import { UserService } from './user.service';
import { RoleEnum } from '../models/role-enum';
import { TokenJwt } from '../models/token-jwt';

const accessTokenKey = 'access_token';
const refreshTokenKey = 'refresh_token';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {

  public currentUserSubject = new BehaviorSubject<User>(null);
  public accessTokenSubject = new BehaviorSubject<string>(null);
  private refreshTokenLoading = false;
  constructor(private http: HttpClient, private config: ConfigService, private userService: UserService,
    private crudService: CrudService, private jwtHelperService: JwtHelperService
  ) {
    this.currentUserSubject = new BehaviorSubject<User>(null);
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  public get accessTokenValue(): string {
    return this.accessTokenSubject.value;
  }
  logout(): Observable<boolean> {

    this.currentUserSubject.next(null);
    return new Observable(subscriber => {
      this.crudService.post(environment.logoutUrl, new HttpParams())
        .subscribe(data => {
          this.clearToken();
          subscriber.next(true);
          subscriber.complete();
        }, error => {
          subscriber.next(false);
          subscriber.complete();
        });
    });
  }

  currentUserUpdateForceLogout(user: User): boolean {
    console.log(`force update of logged user ${user.email}`);
    // if (user.email !== this.loggedUserSubject.value.email || user.role !== this.loggedUserSubject.value.role) {
    //   this.logout('Changed email or role of the current user: forced logout');
    //   return true;
    // }
    // this.accessTokenSubject.next(this.accessTokenSubject.value);
    return false;
  }

  hasRole(role: string): Observable<boolean> {
    return new Observable(subscriber => {
      this.currentUserSubject.subscribe(user => {
        if (user && user.roles === RoleEnum[role]) {
          subscriber.next(true);
          subscriber.complete();
        }
      }, error => {
        subscriber.next(false);
        subscriber.complete();
      }
      );
    });
  }

  private extractLoggedUser(accessToken): Observable<User> {
    if (accessToken) {
      const data = this.jwtHelperService.decodeToken(accessToken);
      console.log(data);
      if (data) {
        return this.userService.findByEmail(data.email);
      }
    }
    return of(null);
  }

  public login(username: string, password: string): Observable<User> {

    const params = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('grant_type', 'password');

    const headers = new HttpHeaders().append(
      'Authorization',
      'Basic ' + btoa(`${this.config.config.clientId}:${this.config.config.clientSecret}`)
    );

    return this.http
      .post<any>(this.config.config.loginUrl, params, {
        headers: headers
      })
      .pipe(
        // delay(2000),
        map(jwt => {
          console.log('store the token');
          this.accessTokenSubject.next(this.storeToken(jwt));
          return this.accessTokenSubject.getValue();
        }),
        switchMap(jwt => this.extractLoggedUser(jwt)),
        tap(user => {
          this.currentUserSubject.next(user);
          console.log(user);
        }),
        catchError(error => {
          console.error(error);
          throw error;
        }));
  }

  private clearToken() {
    localStorage.removeItem(accessTokenKey);
    localStorage.removeItem(refreshTokenKey);
  }

  private storeToken(jwt: TokenJwt): string {
    console.log(`store token`);
    if (jwt && jwt[accessTokenKey]) {
      const accessToken = jwt[accessTokenKey];
      if (jwt[refreshTokenKey]) {
        localStorage.setItem(refreshTokenKey, jwt[refreshTokenKey]);
      }
      localStorage.setItem(accessTokenKey, accessToken);

      return accessToken;
    }
    console.log('token invalid');
    return null;
  }

  public isNewAccesTokenLoading(): boolean {
    return this.refreshTokenLoading;
  }
  /**
   * Return a new acces token.
   */
  public refreshToken(): Observable<any> {
    this.refreshTokenLoading = true;
    const refreshToken = localStorage.getItem(refreshTokenKey);
    if (refreshToken && !this.jwtHelperService.isTokenExpired(refreshToken)) {
      const params = new HttpParams()
        .set(refreshTokenKey, refreshToken)
        .set('grant_type', refreshTokenKey);
      return this.http
        .post<TokenJwt>(this.config.config.loginUrl, params, {
          headers: new HttpHeaders().append(
            'Authorization',
            'Basic ' + btoa(`${this.config.config.clientId}:${this.config.config.clientSecret}`)
          )
        })
        .pipe(map(
          jwt => {
            console.log('load token response');
            const token = this.storeToken(jwt);
            this.accessTokenSubject.next(token);
            return token;
          }, error => {
            console.log("error fatal shit");
          }));
    } else {
      // the refresh token isn't valid
      this.accessTokenSubject.next(null);
      return EMPTY;
    }
  }

}
