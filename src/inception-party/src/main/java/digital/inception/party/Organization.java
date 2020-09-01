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

package digital.inception.party;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * The <code>Organization</code> class holds the information for an organization.
 *
 * @author Marcus Portmann
 */
@Schema(description = "Organization")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name"})
@XmlRootElement(name = "Organization", namespace = "http://party.inception.digital")
@XmlType(
    name = "Organization",
    namespace = "http://party.inception.digital",
    propOrder = {"id", "name"})
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(schema = "party", name = "parties")
@SecondaryTable(
    schema = "party",
    name = "organizations",
    pkJoinColumns = {@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")})
public class Organization {

  /** The type of party for the organization. */
  @JsonIgnore
  @XmlTransient
  @NotNull
  @Column(name = "type", nullable = false)
  private final PartyType partyType = PartyType.ORGANIZATION;

  /** The date and time the organization was created. */
  @JsonIgnore
  @XmlTransient
  @Column(table = "organizations", name = "created", nullable = false, updatable = false)
  private LocalDateTime created;

  /** The Universally Unique Identifier (UUID) uniquely identifying the organization. */
  @Schema(
      description =
          "The Universally Unique Identifier (UUID) uniquely identifying the organization",
      required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Id", required = true)
  @NotNull
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  /** The name of the organization. */
  @Schema(description = "The name of the organization", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Name", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  /** The date and time the party associated with the organization was created. */
  @JsonIgnore
  @XmlTransient
  @Column(table = "parties", name = "created", nullable = false, updatable = false)
  private LocalDateTime partyCreated;

  /** The date and time the party associated with the organization was last updated. */
  @JsonIgnore
  @XmlTransient
  @Column(table = "parties", name = "updated", insertable = false)
  private LocalDateTime partyUpdated;

  /** The date and time the organization was last updated. */
  @JsonIgnore
  @XmlTransient
  @Column(table = "organizations", name = "updated", insertable = false)
  private LocalDateTime updated;

  /** Constructs a new <code>Organization</code>. */
  public Organization() {}

  /**
   * Returns the date and time the organization was created.
   *
   * @return the date and time the organization was created
   */
  public LocalDateTime getCreated() {
    return created;
  }

  /**
   * Returns the Universally Unique Identifier (UUID) uniquely identifying the organization.
   *
   * @return the Universally Unique Identifier (UUID) uniquely identifying the organization
   */
  public UUID getId() {
    return id;
  }

  /**
   * Returns the name of the organization.
   *
   * @return the name of the organization
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the date and time the organization was last updated.
   *
   * @return the date and time the organization was last updated
   */
  public LocalDateTime getUpdated() {
    return updated;
  }

  /**
   * Set the Universally Unique Identifier (UUID) uniquely identifying the organization.
   *
   * @param id the Universally Unique Identifier (UUID) uniquely identifying the organization
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**
   * Set the name of the organization.
   *
   * @param name the name of the organization
   */
  public void setName(String name) {
    this.name = name;
  }

  @PrePersist
  protected void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    created = now;
    partyCreated = now;
  }

  @PreUpdate
  protected void preUpdate() {
    LocalDateTime now = LocalDateTime.now();
    updated = now;
    partyUpdated = now;
  }
}
