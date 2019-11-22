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
 * The ConfigurationTitleResolver class provides the route data resolver that resolves the
 * title for the "Configuration" route in the navigation hierarchy.
 *
 * @author Marcus Portmann
 */
export class ConfigurationTitleResolver implements Resolve<string> {

  /**
   * Constructs a new ConfigurationTitleResolver.
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
  resolve(activatedRouteSnapshot: ActivatedRouteSnapshot, routerStateSnapshot: RouterStateSnapshot): Observable<string> {
    let key = activatedRouteSnapshot.paramMap.get('key');

    if (!key) {
      throw(new Error('No key route parameter found'));
    }

    key = decodeURIComponent(key);

    return of(key);
  }
}
