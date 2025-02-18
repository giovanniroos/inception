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

package demo.model;

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
 * The <b>Cars</b> class holds the results of a request to retrieve a list of cars.
 *
 * @author Marcus Portmann
 */
@Schema(description = "The results of a request to retrieve a list of cars")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"cars", "total", "filter", "sortDirection", "pageIndex", "pageSize"})
@XmlRootElement(name = "Cars", namespace = "http://demo")
@XmlType(
    name = "Cars",
    namespace = "http://demo",
    propOrder = {"cars", "total", "filter", "sortDirection", "pageIndex", "pageSize"})
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings({"unused"})
public class Cars implements Serializable {

  private static final long serialVersionUID = 1000000;

  /** The cars. */
  @Schema(description = "The cars", required = true)
  @JsonProperty(required = true)
  @XmlElementWrapper(name = "Cars", required = true)
  @XmlElement(name = "Car", required = true)
  private List<Car> cars;

  /** The optional filter that was applied to the cars. */
  @Schema(description = "The optional filter that was applied to the cars")
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

  /** The optional sort direction that was applied to the cars. */
  @Schema(description = "The optional sort direction that was applied to the cars")
  @JsonProperty
  @XmlElement(name = "SortDirection")
  private SortDirection sortDirection;

  /** The total number of cars. */
  @Schema(description = "The total number of cars", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Total", required = true)
  private long total;

  /** Constructs a new <b>Cars</b>. */
  public Cars() {}

  /**
   * Constructs a new <b>Cars</b>.
   *
   * @param cars the cars
   * @param total the total number of cars
   * @param filter the optional filter that was applied to the cars
   * @param sortDirection the optional sort direction that was applied to the cars
   * @param pageIndex the optional page index
   * @param pageSize the optional page size
   */
  public Cars(
      List<Car> cars,
      long total,
      String filter,
      SortDirection sortDirection,
      Integer pageIndex,
      Integer pageSize) {
    this.cars = cars;
    this.total = total;
    this.filter = filter;
    this.sortDirection = sortDirection;
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;
  }

  /**
   * Returns the cars.
   *
   * @return the cars
   */
  public List<Car> getCars() {
    return cars;
  }

  /**
   * Returns the optional filter that was applied to the cars.
   *
   * @return the optional filter that was applied to the cars
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
   * Returns the optional sort direction that was applied to the cars.
   *
   * @return the optional sort direction that was applied to the cars
   */
  public SortDirection getSortDirection() {
    return sortDirection;
  }

  /**
   * Returns the total number of cars.
   *
   * @return the total number of cars
   */
  public Long getTotal() {
    return total;
  }
}
