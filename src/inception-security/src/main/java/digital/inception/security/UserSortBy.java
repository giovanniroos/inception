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

package digital.inception.security;

// ~--- non-JDK imports --------------------------------------------------------

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

// ~--- JDK imports ------------------------------------------------------------

/**
 * The <code>UserSortBy</code> enumeration defines the possible methods used to sort a list of
 * users.
 *
 * @author Marcus Portmann
 */
@Schema(description = "The method used to sort the list of users")
@XmlEnum
@XmlType(name = "UserSortBy", namespace = "http://security.inception.digital")
public enum UserSortBy {
  /** Sort by name. */
  @XmlEnumValue("Name")
  NAME("name", "Sort By Name"),

  /** Sort by preferred name. */
  @XmlEnumValue("PreferredName")
  PREFERRED_NAME("preferred_name", "Sort By Preferred Name"),

  /** Sort by username. */
  @XmlEnumValue("Username")
  USERNAME("username", "Sort By Username");

  private final String code;

  private final String description;

  UserSortBy(String code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Returns the method used to sort a list of users given by the specified code value.
   *
   * @param code the code value identifying the method used to sort a list of users
   * @return the method used to sort a list of users given by the specified code value
   */
  @JsonCreator
  public static UserSortBy fromCode(String code) {
    switch (code) {
      case "name":
        return UserSortBy.NAME;
      case "preferred_name":
        return UserSortBy.PREFERRED_NAME;
      case "username":
        return UserSortBy.USERNAME;
      default:
        throw new RuntimeException(
            "Failed to determine the user sort by with the invalid code (" + code + ")");
    }
  }

  /**
   * Returns the code value identifying the method used to sort a list of users.
   *
   * @return the code value identifying the method used to sort a list of users
   */
  @JsonValue
  public String code() {
    return code;
  }

  /**
   * Returns the description for the method used to sort a list of users.
   *
   * @return the description for the method used to sort a list of users
   */
  public String description() {
    return description;
  }

  /**
   * Return the string representation of the method used to sort a list of users enumeration value.
   *
   * @return the string representation of the method used to sort a list of users enumeration value
   */
  public String toString() {
    return description;
  }
}
