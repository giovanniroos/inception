/*
 * Copyright 2018 Marcus Portmann
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

import {Inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs/Rx';
import {catchError} from 'rxjs/operators';
import {map} from 'rxjs/operators';


import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Organization} from "./organization";
import {OrganizationStatus} from "./organization-status";

/**
 * The SecurityService class provides the Security Service implementation.
 *
 * @author Marcus Portmann
 */
@Injectable()
export class SecurityService {

  /**
   * Constructs a new SecurityService.
   *
   * @param {HttpClient} httpClient The HTTP client.
   */
  constructor(private _httpClient: HttpClient) {
  }

  /**
   * Retrieve the organizations.
   *
   * @returns {Observable<Organization[]>}
   */
  public getOrganizations(): Observable<Organization[]> {

    return this._httpClient.get<Organization[]>('http://localhost:20000/api/organizations').pipe(
      map(organizations => {


        for (var i: number = 0; i < organizations.length; i++) {


          console.log('organizations[' + i + '] = ', organizations[i]);

          if (organizations[i].status == OrganizationStatus.Active) {
            console.log('Found active organization ', organizations[i].name);
          }
          else if (organizations[i].status == OrganizationStatus.Inactive) {
            console.log('Found inactive organization ', organizations[i].name);
          }
        }


        return organizations;

      }), catchError((error: HttpErrorResponse) => {

        console.log('catchError = ', error);

        // TODO: Map different HTTP error codes to specific error types -- MARCUS


        return Observable.throw(error);

        //return Observable.throw(new LoginError(error.status));

      }));
  }
}
