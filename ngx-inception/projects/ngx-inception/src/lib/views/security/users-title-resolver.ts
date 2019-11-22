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

import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable, of} from 'rxjs';
import {I18n} from '@ngx-translate/i18n-polyfill';

/**
 * The UsersTitleResolver class provides the route data resolver that resolves the
 * title for the "Users" route in the navigation hierarchy.
 *
 * @author Marcus Portmann
 */
export class UsersTitleResolver implements Resolve<string> {

  /**
   * Constructs a new UsersTitleResolver.
   *
   * @param i18n The internationalization service.
   */
  constructor(private i18n: I18n) {
  }

  /**
   * Resolve the title.
   *
   * @param activatedRouteSnapshot The activate route snapshot.
   * @param routerStateSnapshot    The router state snapshot.
   */
  resolve(activatedRouteSnapshot: ActivatedRouteSnapshot, routerStateSnapshot: RouterStateSnapshot):
    Observable<string> {
    let userDirectoryId = activatedRouteSnapshot.paramMap.get('userDirectoryId');

    if (!userDirectoryId) {
      throw(new Error('No userDirectoryId route parameter found'));
    }

    userDirectoryId = decodeURIComponent(userDirectoryId);

    return of(this.i18n({
      id: '@@security_users_title_resolver_title',
      value: 'Users'
    }));
  }
}
