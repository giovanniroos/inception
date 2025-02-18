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

/**
 * The MaritalStatus class holds the information for a marital status.
 *
 * @author Marcus Portmann
 */
export class MaritalStatus {

  /**
   * The code for the marital status.
   */
  code: string;

  /**
   * The description for the marital status.
   */
  description: string;

  /**
   * The Unicode locale identifier for the marital status.
   */
  localeId: string;

  /**
   * The name of the marital status.
   */
  name: string;

  /**
   * The sort index for the marital status.
   */
  sortIndex: number;

  /**
   * The ID for the tenant the marital status is specific to.
   */
  tenantId?: string;

  /**
   * Constructs a new MaritalStatus.
   *
   * @param code        The code for the marital status.
   * @param localeId    The Unicode locale identifier for the marital status.
   * @param sortIndex   The sort index for the marital status.
   * @param name        The name of the marital status.
   * @param description The description for the marital status.
   * @param tenantId    The ID for the tenant the marital status is specific to.
   */
  constructor(code: string, localeId: string, sortIndex: number, name: string, description: string,
              tenantId?: string) {
    this.code = code;
    this.localeId = localeId;
    this.sortIndex = sortIndex;
    this.name = name;
    this.description = description;
    this.tenantId = tenantId;
  }
}
