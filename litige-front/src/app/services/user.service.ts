import { environment } from 'src/environments/environment';
import { CrudService } from './crud.service';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Page } from '../models/page';

export class UserServiceFilter {
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private crudService: CrudService) {}

  delete(i: number): Observable<boolean> {
    throw new Error('Method not implemented.');
  }
  all(): Observable<Page<User>> {
    return this.crudService.get(environment.authUrl + '/users');
  }

  findByEmail(email: string): Observable<User> {
    const params = new HttpParams().set('email', email);

    return this.crudService.get(environment.authUrl + '/users/findByEmail', params);
  }

  changePassword(id: number, oldPassword: string, newPassword: string): Observable<boolean> {
    console.log(`change password ${id} ${oldPassword} ${newPassword}`);
    return this.crudService
      .put<boolean>(
        environment.authUrl + `/users/${id}/changePassword`,
        new HttpParams()
          .set('id', String(id))
          .set('oldPassword', oldPassword)
          .set('newPassword', newPassword)
      )
      .pipe(map(() => true));
  }

  signinClaimant(user: User): Observable<User> {
    return this.crudService.post<User>(environment.authUrl + '/users/signin-claimant', user);
  }

  signin(email: string, password: string): Observable<User> {
    console.log(`signin ${email} ${password}`);
    return this.crudService.post<User>(
      environment.authUrl + '/users/signin',
      new HttpParams().set('email', email).set('password', password)
    );
  }

  validateEmail(email: string): Observable<boolean> {
    return this.crudService.post<boolean>(
      environment.authUrl + '/users/validateEmail',
      new HttpParams().set('email', email)
    );
  }

  validateAccount(email: string, token: string): Observable<User> {
    return this.crudService.get<User>(
      environment.authUrl + '/users/validate-account',
      new HttpParams().set('email', email).set('token', token)
    );
  }

  update(user: User): Observable<boolean> {
    return this.crudService.put<User>(environment.authUrl + '/users/', user).pipe(
      map(res => {
        console.log(`updated ${user.constructor.name} ${user.id}`);
        return true;
      })
    );
  }
}
