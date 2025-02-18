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
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The <b>TimeToContact</b> class holds the information for a time to contact.
 *
 * @author Marcus Portmann
 */
@Schema(description = "A time to contact")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "localeId", "tenantId", "sortIndex", "name", "description"})
@XmlRootElement(name = "TimeToContact", namespace = "http://inception.digital/party")
@XmlType(
    name = "TimeToContact",
    namespace = "http://inception.digital/party",
    propOrder = {"code", "localeId", "tenantId", "sortIndex", "name", "description"})
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(schema = "party", name = "times_to_contact")
@IdClass(TimeToContactId.class)
public class TimeToContact implements Serializable {

  private static final long serialVersionUID = 1000000;

  /** The code for the time to contact. */
  @Schema(description = "The code for the time to contact", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Code", required = true)
  @NotNull
  @Size(min = 1, max = 30)
  @Id
  @Column(name = "code", length = 30, nullable = false)
  private String code;

  /** The description for the time to contact. */
  @Schema(description = "The description for the time to contact", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Description", required = true)
  @NotNull
  @Size(max = 200)
  @Column(name = "description", length = 200, nullable = false)
  private String description;

  /** The Unicode locale identifier for the time to contact. */
  @Schema(description = "The Unicode locale identifier for the time to contact", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "LocaleId", required = true)
  @NotNull
  @Size(min = 2, max = 10)
  @Id
  @Column(name = "locale_id", length = 10, nullable = false)
  private String localeId;

  /** The name of the time to contact. */
  @Schema(description = "The name of the time to contact", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Name", required = true)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "name", length = 50, nullable = false)
  private String name;

  /** The sort index for the time to contact. */
  @Schema(description = "The sort index for the time to contact", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "SortIndex", required = true)
  @NotNull
  @Column(name = "sort_index", nullable = false)
  private Integer sortIndex;

  /** The ID for the tenant the time to contact is specific to. */
  @Schema(description = "The ID for the tenant the time to contact is specific to")
  @JsonProperty
  @XmlElement(name = "TenantId")
  @Column(name = "tenant_id")
  private UUID tenantId;

  /** Constructs a new <b>TimeToContact</b>. */
  public TimeToContact() {}

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param object the reference object with which to compare
   * @return <b>true</b> if this object is the same as the object argument otherwise <b>false</b>
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (object == null) {
      return false;
    }

    if (getClass() != object.getClass()) {
      return false;
    }

    TimeToContact other = (TimeToContact) object;

    return Objects.equals(code, other.code) && Objects.equals(localeId, other.localeId);
  }

  /**
   * Returns the code for the time to contact.
   *
   * @return the code for the time to contact
   */
  public String getCode() {
    return code;
  }

  /**
   * Returns the description for the time to contact.
   *
   * @return the description for the time to contact
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the Unicode locale identifier for the time to contact.
   *
   * @return the Unicode locale identifier for the time to contact
   */
  public String getLocaleId() {
    return localeId;
  }

  /**
   * Returns the name of the time to contact.
   *
   * @return the name of the time to contact
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the sort index for the time to contact.
   *
   * @return the sort index for the time to contact
   */
  public Integer getSortIndex() {
    return sortIndex;
  }

  /**
   * Returns the ID for the tenant the time to contact is specific to.
   *
   * @return the ID for the tenant the time to contact is specific to
   */
  public UUID getTenantId() {
    return tenantId;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for the object
   */
  @Override
  public int hashCode() {
    return ((code == null) ? 0 : code.hashCode()) + ((localeId == null) ? 0 : localeId.hashCode());
  }

  /**
   * Set the code for the time to contact.
   *
   * @param code the code for the time to contact
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Set the description for the time to contact.
   *
   * @param description the description for the time to contact
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Set the Unicode locale identifier for the time to contact.
   *
   * @param localeId the Unicode locale identifier for the time to contact
   */
  public void setLocaleId(String localeId) {
    this.localeId = localeId;
  }

  /**
   * Set the name of the time to contact.
   *
   * @param name the name of the time to contact
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set the sort index for the time to contact.
   *
   * @param sortIndex the sort index for the time to contact
   */
  public void setSortIndex(Integer sortIndex) {
    this.sortIndex = sortIndex;
  }

  /**
   * Set the ID for the tenant the time to contact is specific to.
   *
   * @param tenantId the ID for the tenant the time to contact is specific to
   */
  public void setTenantId(UUID tenantId) {
    this.tenantId = tenantId;
  }
}
