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

import {Error} from "../../errors/error";

/**
 * The CodesServiceError class holds the information for a Codes Service error.
 *
 * @author Marcus Portmann
 */
export class CodesServiceError extends Error {

  /**
   * Constructs a new CodesServiceError.
   *
   * @param message The error message.
   * @param cause   The optional cause of the error.
   */
  constructor(message: string, cause?: any) {
    super(message, cause);
  }
}

/**
 * The DuplicateCodeCategory class holds the information for a duplicate code category error.
 *
 * @author Marcus Portmann
 */
export class DuplicateCodeCategoryError extends Error {

  /**
   * Constructs a new DuplicateCodeCategoryError.
   *
   * @param message The error message.
   * @param cause   The optional cause of the error.
   */
  constructor(message: string, cause?: any) {
    super(message, cause);
  }
}
