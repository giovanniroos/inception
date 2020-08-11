/*
 * Copyright 2020 Marcus Portmann
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

import {UserStatus} from './user-status';

/**
 * The User class holds the information for a user.
 *
 * @author Marcus Portmann
 */
export class User {

  /**
   * The e-mail address for the user.
   */
  email: string;

  /**
   * The full name of the user.
   */
  fullName: string;

  /**
   * The preferred name for the user.
   */
  preferredName: string;

  /**
   * The mobile number for the user.
   */
  mobileNumber: string;

  /**
   * The password or password hash for the user.
   */
  password: string;

  /**
   * The number of failed authentication attempts as a result of an incorrect password for the user.
   */
  passwordAttempts?: number | undefined;

  /**
   * The date and time the password for the user expires.
   */
  passwordExpiry?: Date | undefined;

  /**
   * The phone number for the user.
   */
  phoneNumber: string;

  /**
   * The status for the user.
   */
  status: UserStatus;

  /**
   * The Universally Unique Identifier (UUID) uniquely identifying the user directory the user
   * is associated with.
   */
  userDirectoryId: string;

  /**
   * The username for the user.
   */
  username: string;

  /**
   * Constructs a new User.
   *
   * @param userDirectoryId   The Universally Unique Identifier (UUID) uniquely identifying the
   *                          user directory the user is associated with.
   * @param username          The username for the user.
   * @param fullName          The full name of the user.
   * @param preferredName     The preferred name for the user.
   * @param mobileNumber      The mobile number for the user.
   * @param phoneNumber       The phone number for the user.
   * @param email             The e-mail address for the user.
   * @param status            The status for the user.*
   * @param password          The password or password hash for the user.
   * @param passwordAttempts  The number of failed authentication attempts as a result of an
   *                          incorrect password for the user.
   * @param passwordExpiry    The date and time the password for the user expires.
   */
  constructor(userDirectoryId: string, username: string, fullName: string, preferredName: string, mobileNumber: string,
              phoneNumber: string, email: string, status: UserStatus, password: string, passwordAttempts?: number,
              passwordExpiry?: Date) {
    this.userDirectoryId = userDirectoryId;
    this.username = username;
    this.fullName = fullName;
    this.preferredName = preferredName;
    this.mobileNumber = mobileNumber;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.status = status;
    this.password = password;
    this.passwordAttempts = passwordAttempts;
    this.passwordExpiry = passwordExpiry;
  }
}
