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

package digital.inception.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import digital.inception.core.sorting.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The <b>UserDirectories</b> class holds the results of a request to retrieve a list of user
 * directories.
 *
 * @author Marcus Portmann
 */
@Schema(description = "The results of a request to retrieve a list of user directories")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"userDirectories", "total", "filter", "sortDirection", "pageIndex", "pageSize"})
@XmlRootElement(name = "UserDirectories", namespace = "http://inception.digital/security")
@XmlType(
    name = "UserDirectories",
    namespace = "http://inception.digital/security",
    propOrder = {"userDirectories", "total", "filter", "sortDirection", "pageIndex", "pageSize"})
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings({"unused"})
public class UserDirectories implements Serializable {

  private static final long serialVersionUID = 1000000;

  /** The optional filter that was applied to the user directories. */
  @Schema(description = "The optional filter that was applied to the user directories")
  @JsonProperty
  @XmlElement(name = "Filter")
  private String filter;

  /** The optional page index. */
  @Schema(description = "The optional page index")
  @JsonProperty
  @XmlElement(name = "PageIndex")
  private Integer pageIndex;

  /** The optional page size. */
  @Schema(description = "The optional page size")
  @JsonProperty
  @XmlElement(name = "PageSize")
  private Integer pageSize;

  /** The optional sort direction that was applied to the user directories. */
  @Schema(description = "The optional sort direction that was applied to the user directories")
  @JsonProperty
  @XmlElement(name = "SortDirection")
  private SortDirection sortDirection;

  /** The total number of user directories. */
  @Schema(description = "The total number of user directories", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Total", required = true)
  private long total;

  /** The user directories. */
  @Schema(description = "The user directories", required = true)
  @JsonProperty(required = true)
  @XmlElementWrapper(name = "UserDirectories", required = true)
  @XmlElement(name = "UserDirectory", required = true)
  private List<UserDirectory> userDirectories;

  /** Constructs a new <b>UserDirectories</b>. */
  public UserDirectories() {}

  /**
   * Constructs a new <b>UserDirectories</b>.
   *
   * @param userDirectories the user directories
   * @param total the total number of user directories
   * @param filter the optional filter that was applied to the user directories
   * @param sortDirection the optional sort direction that was applied to the user directories
   * @param pageIndex the optional page index
   * @param pageSize the optional page size
   */
  public UserDirectories(
      List<UserDirectory> userDirectories,
      long total,
      String filter,
      SortDirection sortDirection,
      Integer pageIndex,
      Integer pageSize) {
    this.userDirectories = userDirectories;
    this.total = total;
    this.filter = filter;
    this.sortDirection = sortDirection;
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;
  }

  /**
   * Returns the optional filter that was applied to the user directories.
   *
   * @return the optional filter that was applied to the user directories
   */
  public String getFilter() {
    return filter;
  }

  /**
   * Returns the optional page index.
   *
   * @return the optional page index
   */
  public Integer getPageIndex() {
    return pageIndex;
  }

  /**
   * Returns the optional page size.
   *
   * @return the optional page size
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * Returns the optional sort direction that was applied to the user directories.
   *
   * @return the optional sort direction that was applied to the user directories
   */
  public SortDirection getSortDirection() {
    return sortDirection;
  }

  /**
   * Returns the total number of user directories.
   *
   * @return the total number of user directories
   */
  public Long getTotal() {
    return total;
  }

  /**
   * Returns the user directories.
   *
   * @return the user directories
   */
  public List<UserDirectory> getUserDirectories() {
    return userDirectories;
  }
}
