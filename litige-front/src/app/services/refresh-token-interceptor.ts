// import { Injectable } from '@angular/core';
// import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { catchError, retry } from 'rxjs/operators';
// import { AuthenticationService } from './authentication.service';


// @Injectable()
// export class RefreshTokenInterceptor implements HttpInterceptor {
//     constructor(private authenticationService: AuthenticationService) { }

//     intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

//         return next.handle(request).pipe(
//             retry(3),
//             catchError((error: HttpErrorResponse) => {
//               if (error.status !== 401) {
//                 // 401 handled in auth.interceptor
//                 if(error.error === 'invalid_token'){
//                     this.authenticationService.refreshToken().subscribe(accessToken =>{
//                         console.log("Refresh token interceptor: ",request);
//                     });    
//               }else{
//                 // auto logout if 401 response returned from api
//                 this.authenticationService.logout();
//                 location.reload(true);
//                 }
//               return throwError(error);
//             }
//         }
//           ));
//         }
    
// }