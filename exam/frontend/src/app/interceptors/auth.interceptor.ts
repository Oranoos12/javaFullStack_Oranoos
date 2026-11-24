import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { environment } from '../../environments/environment';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const user = inject(AuthService).user();
  if (user === null) {
    return next(request);
  }

  const withHeaders = request.clone({
    setHeaders: {
      [environment.userHeader]: user.id,
      [environment.roleHeader]: user.role
    }
  });
  return next(withHeaders);
};
