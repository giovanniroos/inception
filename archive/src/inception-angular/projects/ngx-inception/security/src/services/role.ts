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
 * The Role class holds the information for a role.
 *
 * @author Marcus Portmann
 */
export class Role {

  /**
   * The code for the role.
   */
  code: string;

  /**
   *  The description for the role.
   */
  description: string | null = null;

  /**
   * The name of the role.
   */
  name: string;

  /**
   * Constructs a new Role.
   *
   * @param code        The code for the role.
   * @param name        The name of the role.
   * @param description The description for the group.
   */
  constructor(code: string, name: string, description?: string) {
    this.code = code;
    this.name = name;
    this.description = !!description ? description : null;
  }
}
