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

package digital.inception.security;

//~--- non-JDK imports --------------------------------------------------------

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//~--- JDK imports ------------------------------------------------------------

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.xml.bind.annotation.*;

/**
 * The <code>UserDirectorySummary</code> class holds the summary information for a user directory.
 *
 * @author Marcus Portmann
 */
@ApiModel(value = "UserDirectorySummary")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "type", "name" })
@XmlRootElement(name = "UserDirectorySummary", namespace = "http://security.inception.digital")
@XmlType(name = "UserDirectorySummary", namespace = "http://security.inception.digital",
    propOrder = { "id", "type", "name" })
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings({ "unused", "WeakerAccess" })
public class UserDirectorySummary
  implements java.io.Serializable
{
  private static final long serialVersionUID = 1000000;

  /**
   * The Universally Unique Identifier (UUID) used to uniquely identify the user directory.
   */
  @ApiModelProperty(value = "The Universally Unique Identifier (UUID) used to uniquely identify the user directory", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Id", required = true)
  @NotNull
  private String id;

  /**
   * The name of the user directory.
   */
  @ApiModelProperty(value = "The name of the user directory", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Name", required = true)
  @NotNull
  @Size(min = 1, max = 4000)
  private String name;

  /**
   * The code used to uniquely identify the user directory type.
   */
  @ApiModelProperty(value = "The code used to uniquely identify the user directory type",
      required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Type", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  private String type;

  /**
   * Constructs a new <code>UserDirectorySummary</code>.
   */
  public UserDirectorySummary() {}

  /**
   * Returns the Universally Unique Identifier (UUID) used to uniquely identify the user directory.
   *
   * @return the Universally Unique Identifier (UUID) used to uniquely identify the user directory
   */
  public String getId()
  {
    return id;
  }

  /**
   * Returns the name of the user directory.
   *
   * @return the name of the user directory
   */
  public String getName()
  {
    return name;
  }

  /**
   * Returns the code used to uniquely identify the user directory type.
   *
   * @return the code used to uniquely identify the user directory type
   */
  public String getType()
  {
    return type;
  }

  /**
   * Set the Universally Unique Identifier (UUID) used to uniquely identify the user directory.
   *
   * @param id the Universally Unique Identifier (UUID) used to uniquely identify the user directory
   */
  public void setId(String id)
  {
    this.id = id;
  }

  /**
   * Set the name of the user directory.
   *
   * @param name the name of the user directory
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Set the code used to uniquely identify the user directory type.
   *
   * @param type the code used to uniquely identify the user directory type
   */
  public void setType(String type)
  {
    this.type = type;
  }
}
