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

package digital.inception.party;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import digital.inception.core.sorting.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The <b>MandatesForParty</b> class holds the results of a request to retrieve a list of mandates
 * for a party.
 *
 * @author Marcus Portmann
 */
@Schema(description = "The results of a request to retrieve a list of mandates for a party")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "tenantId",
  "partyId",
  "mandates",
  "total",
  "sortBy",
  "sortDirection",
  "pageIndex",
  "pageSize"
})
@XmlRootElement(name = "MandatesForParty", namespace = "http://inception.digital/party")
@XmlType(
    name = "MandatesForParty",
    namespace = "http://inception.digital/party",
    propOrder = {
      "tenantId",
      "partyId",
      "mandates",
      "total",
      "sortBy",
      "sortDirection",
      "pageIndex",
      "pageSize"
    })
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings({"unused"})
public class MandatesForParty implements Serializable {

  private static final long serialVersionUID = 1000000;

  /** The mandates. */
  @Schema(description = "The mandates", required = true)
  @JsonProperty(required = true)
  @XmlElementWrapper(name = "Mandates", required = true)
  @XmlElement(name = "Mandate", required = true)
  private List<Mandate> mandates;

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

  /** The ID for the party the mandates are associated with. */
  @Schema(description = "The ID for the party the mandates are associated with", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "PartyId", required = true)
  private UUID partyId;

  /** The optional method used to sort the mandates e.g. by name. */
  @Schema(description = "The optional method used to sort the mandates e.g. by name")
  @JsonProperty
  @XmlElement(name = "SortBy")
  private MandateSortBy sortBy;

  /** The optional sort direction that was applied to the mandates. */
  @Schema(description = "The optional sort direction that was applied to the mandates")
  @JsonProperty
  @XmlElement(name = "SortDirection")
  private SortDirection sortDirection;

  /** The ID for the tenant the mandates are associated with. */
  @Schema(description = "The ID for the tenant the mandates are associated with", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "TenantId", required = true)
  private UUID tenantId;

  /** The total number of mandates. */
  @Schema(description = "The total number of mandates", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Total", required = true)
  private long total;

  /** Constructs a new <b>MandatesForParty</b>. */
  public MandatesForParty() {}

  /**
   * Constructs a new <b>MandatesForParty</b>.
   *
   * @param tenantId the ID for the tenant the mandates are associated with
   * @param partyId the ID for the party the mandates are associated with
   * @param mandates the mandates
   * @param total the total number of mandates
   * @param sortBy the optional method used to sort the mandates e.g. by name
   * @param sortDirection the optional sort direction that was applied to the mandates
   * @param pageIndex the optional page index
   * @param pageSize the optional page size
   */
  public MandatesForParty(
      UUID tenantId,
      UUID partyId,
      List<Mandate> mandates,
      long total,
      MandateSortBy sortBy,
      SortDirection sortDirection,
      Integer pageIndex,
      Integer pageSize) {
    this.tenantId = tenantId;
    this.partyId = partyId;
    this.mandates = mandates;
    this.total = total;
    this.sortBy = sortBy;
    this.sortDirection = sortDirection;
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;
  }

  /**
   * Returns the mandates.
   *
   * @return the mandates
   */
  public List<Mandate> getMandates() {
    return mandates;
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
   * Returns the ID for the party the mandates are associated with.
   *
   * @return the ID for the party the mandates are associated with
   */
  public UUID getPartyId() {
    return partyId;
  }

  /**
   * Returns the optional method used to sort the mandates e.g. by name.
   *
   * @return the optional method used to sort the mandates
   */
  public MandateSortBy getSortBy() {
    return sortBy;
  }

  /**
   * Returns the optional sort direction that was applied to the mandates.
   *
   * @return the optional sort direction that was applied to the mandates
   */
  public SortDirection getSortDirection() {
    return sortDirection;
  }

  /**
   * Returns the ID for the tenant the mandates are associated with.
   *
   * @return the ID for the tenant the mandates are associated with
   */
  public UUID getTenantId() {
    return tenantId;
  }

  /**
   * Returns the total number of mandates.
   *
   * @return the total number of mandates
   */
  public Long getTotal() {
    return total;
  }
}
