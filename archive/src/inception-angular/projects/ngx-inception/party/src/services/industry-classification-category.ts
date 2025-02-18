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
 * The IndustryClassificationCategory class holds the information for an industry classification
 * category.
 *
 * @author Marcus Portmann
 */
export class IndustryClassificationCategory {

  /**
   * The code for the industry classification category.
   */
  code: string;

  /**
   * The description for the industry classification category.
   */
  description: string;

  /**
   * The Unicode locale identifier for the industry classification category.
   */
  localeId: string;

  /**
   * The name of the industry classification category.
   */
  name: string;

  /**
   * The sort index for the industry classification category.
   */
  sortIndex: number;

  /**
   * The code for the industry classification system for the industry classification category.
   */
  system: string;

  /**
   * The ID for the tenant the industry classification category is specific to.
   */
  tenantId?: string;

  /**
   * Constructs a new IndustryClassificationCategory.
   *
   * @param system      The code for the industry classification system for the industry
   *                    classification category.
   * @param code        The code for the industry classification category.
   * @param localeId    The Unicode locale identifier for the industry classification category.
   * @param sortIndex   The sort index for the industry classification category.
   * @param name        The name of the industry classification category.
   * @param description The description for the industry classification category.
   * @param tenantId    The ID for the tenant the industry classification category is specific to.
   */
  constructor(system: string, code: string, localeId: string, sortIndex: number, name: string,
              description: string, tenantId?: string) {
    this.system = system;
    this.code = code;
    this.localeId = localeId;
    this.sortIndex = sortIndex;
    this.name = name;
    this.description = description;
    this.tenantId = tenantId;
  }
}
