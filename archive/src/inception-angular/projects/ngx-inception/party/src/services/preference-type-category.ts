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
 * The PreferenceTypeCategory class holds the information for a preference type category.
 *
 * @author Marcus Portmann
 */
export class PreferenceTypeCategory {

  /**
   * The code for the preference type category.
   */
  code: string;

  /**
   * The description for the preference type category.
   */
  description: string;

  /**
   * The Unicode locale identifier for the preference type category.
   */
  localeId: string;

  /**
   * The name of the preference type category.
   */
  name: string;

  /**
   * The codes for the party types the preference type category is associated with.
   */
  partyTypes: string[];

  /**
   * The sort index for the preference type category.
   */
  sortIndex: number;

  /**
   * The ID for the tenant the preference type category is specific to.
   */
  tenantId?: string;

  /**
   * Constructs a new PreferenceTypeCategory.
   *
   * @param code        The code for the preference type category.
   * @param localeId    The Unicode locale identifier for the preference type category.
   * @param sortIndex   The sort index for the preference type category.
   * @param name        The name of the preference type category.
   * @param description The description for the preference type category.
   * @param partyTypes  The codes for the party types the attribute type category is associated with.
   * @param tenantId    The ID for the tenant the preference type category is specific to.
   */
  constructor(code: string, localeId: string, sortIndex: number, name: string, description: string,
              partyTypes: string[], tenantId?: string) {
    this.code = code;
    this.localeId = localeId;
    this.sortIndex = sortIndex;
    this.name = name;
    this.description = description;
    this.partyTypes = partyTypes;
    this.tenantId = tenantId;
  }
}
