/*
 * Copyright 2019 Marcus Portmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

import {flatMap} from 'rxjs/operators';

import {SessionService} from './session.service';

/**
 * The SessionInterceptor class implements an Angular HTTP interceptor, which injects the OAuth2
 * access token associated with the current active user session into HTTP requests as an
 * Authorization header.
 *
 * @author Marcus Portmann
 */
@Injectable()
export class SessionInterceptor implements HttpInterceptor {

  /**
   * Constructs a new SessionInterceptor.
   *
   * @param sessionService The session service.
   */
  constructor(private sessionService: SessionService) {
  }

  intercept(httpRequest: HttpRequest<any>,
            nextHttpHandler: HttpHandler): Observable<HttpEvent<any>> {
    if (!httpRequest.url.endsWith('/oauth/token')) {
      return this.sessionService.session.pipe(flatMap(session => {
        if (session) {
          httpRequest = httpRequest.clone({
            setHeaders: {
              Authorization: `Bearer ${session.accessToken}`
            }
          });
        }

        return nextHttpHandler.handle(httpRequest);
      }));
    } else {
      return nextHttpHandler.handle(httpRequest);
    }
  }
}
