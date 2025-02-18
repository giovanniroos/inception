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
import {GroupMember} from './group-member';

/**
 * The GroupMembers class holds the results of a request to retrieve a list of group members.
 *
 * @author Marcus Portmann
 */
export class GroupMembers {

  /**
   * The optional filter that was applied to the group members.
   */
  filter: string | null = null;

  /**
   * The group members.
   */
  groupMembers: GroupMember[];

  /**
   * The name of the group the group members are associated with.
   */
  groupName: string;

  /**
   * The optional page index.
   */
  pageIndex: number | null = null;

  /**
   * The optional page size.
   */
  pageSize: number | null = null;

  /**
   * The optional sort direction that was applied to the group members.
   */
  sortDirection: SortDirection | null = null;

  /**
   * The total number of group members.
   */
  total: number;

  /**
   * The ID for the user directory the group members are associated with.
   */
  userDirectoryId: string;

  /**
   * Constructs a new GroupMembers.
   *
   * @param userDirectoryId The ID for the user directory the group members are associated with.
   * @param groupName       The name of the group the group members are associated with.
   * @param groupMembers    The group members.
   * @param total           The total number of groups.
   * @param filter          The optional filter that was applied to the group members.
   * @param sortDirection   The optional sort direction that was applied to the group members.
   * @param pageIndex       The optional page index.
   * @param pageSize        The optional page size.
   */
  constructor(userDirectoryId: string, groupName: string, groupMembers: GroupMember[], total: number,
              filter?: string, sortDirection?: SortDirection, pageIndex?: number, pageSize?: number) {
    this.userDirectoryId = userDirectoryId;
    this.groupName = groupName;
    this.groupMembers = groupMembers;
    this.total = total;
    this.filter = !!filter ? filter : null;
    this.sortDirection = !!sortDirection ? sortDirection : null;
    this.pageIndex = !!pageIndex ? pageIndex : null;
    this.pageSize = !!pageSize ? pageSize : null;
  }
}
