/*
 * Copyright 2022 Marcus Portmann
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

import {SortDirection} from 'ngx-inception/core';
import {User} from './user';
import {UserSortBy} from './user-sort-by';

/**
 * The Users class holds the results of a request to retrieve a list of users.
 *
 * @author Marcus Portmann
 */
export class Users {

  /**
   * The optional filter that was applied to the users.
   */
  filter: string | null = null;

  /**
   * The optional page index.
   */
  pageIndex: number | null = null;

  /**
   * The optional page size.
   */
  pageSize: number | null = null;

  /**
   * The optional method used to sort the users e.g. by name.
   */
  sortBy: UserSortBy | null = null;

  /**
   * The optional sort direction that was applied to the users.
   */
  sortDirection: SortDirection | null = null;

  /**
   * The total number of users.
   */
  total: number;

  /**
   * The ID for the user directory the users are associated with.
   */
  userDirectoryId: string;

  /**
   * The users.
   */
  users: User[];

  /**
   * Constructs a new Users.
   *
   * @param userDirectoryId The ID for the user directory the users are associated with.
   * @param users           The users.
   * @param total           The total number of users.
   * @param filter          The optional filter that was applied to the users.
   * @param sortBy          The optional method used to sort the users e.g. by name.
   * @param sortDirection   The optional sort direction that was applied to the users.
   * @param pageIndex       The optional page index.
   * @param pageSize        The optional page size.
   */
  constructor(userDirectoryId: string, users: User[], total: number, filter?: string,
              sortBy?: UserSortBy, sortDirection?: SortDirection, pageIndex?: number,
              pageSize?: number) {
    this.userDirectoryId = userDirectoryId;
    this.users = users;
    this.total = total;
    this.filter = !!filter ? filter : null;
    this.sortBy = !!sortBy ? sortBy : null;
    this.sortDirection = !!sortDirection ? sortDirection : null;
    this.pageIndex = !!pageIndex ? pageIndex : null;
    this.pageSize = !!pageSize ? pageSize : null;
  }
}
