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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.springframework.util.StringUtils;

/**
 * The <b>Title</b> class holds the information for a title.
 *
 * @author Marcus Portmann
 */
@Schema(description = "An honorific prefixing a person's name")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "code",
  "localeId",
  "tenantId",
  "sortIndex",
  "name",
  "abbreviation",
  "description",
  "genders"
})
@XmlRootElement(name = "Title", namespace = "http://inception.digital/party")
@XmlType(
    name = "Title",
    namespace = "http://inception.digital/party",
    propOrder = {
      "code",
      "localeId",
      "tenantId",
      "sortIndex",
      "name",
      "abbreviation",
      "description",
      "genders"
    })
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(schema = "party", name = "titles")
@IdClass(TitleId.class)
public class Title implements Serializable {

  private static final long serialVersionUID = 1000000;

  /** The abbreviation for the title. */
  @Schema(description = "The abbreviation for the title", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Abbreviation", required = true)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "abbreviation", length = 20, nullable = false)
  private String abbreviation;

  /** The code for the title. */
  @Schema(description = "The code for the title", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Code", required = true)
  @NotNull
  @Size(min = 1, max = 30)
  @Id
  @Column(name = "code", length = 30, nullable = false)
  private String code;

  /** The description for the title. */
  @Schema(description = "The description for the title", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Description", required = true)
  @NotNull
  @Size(max = 200)
  @Column(name = "description", length = 200, nullable = false)
  private String description;

  /** The comma-delimited codes for the genders the title is applicable to. */
  @JsonIgnore
  @XmlTransient
  @NotNull
  @Size(min = 1, max = 310)
  @Column(name = "genders", length = 310, nullable = false)
  private String genders;

  /** The Unicode locale identifier for the title. */
  @Schema(description = "The Unicode locale identifier for the title", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "LocaleId", required = true)
  @NotNull
  @Size(min = 2, max = 10)
  @Id
  @Column(name = "locale_id", length = 10, nullable = false)
  private String localeId;

  /** The name of the title. */
  @Schema(description = "The name of the title", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Name", required = true)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "name", length = 50, nullable = false)
  private String name;

  /** The sort index for the title. */
  @Schema(description = "The sort index for the title")
  @JsonProperty
  @XmlElement(name = "SortIndex")
  @Column(name = "sort_index")
  private Integer sortIndex;

  /** The ID for the tenant the title is specific to. */
  @Schema(description = "The ID for the tenant the title is specific to")
  @JsonProperty
  @XmlElement(name = "TenantId")
  @Column(name = "tenant_id")
  private UUID tenantId;

  /** Constructs a new <b>Title</b>. */
  public Title() {}

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

    Title other = (Title) object;

    return Objects.equals(code, other.code) && Objects.equals(localeId, other.localeId);
  }

  /**
   * Returns the abbreviation for the title.
   *
   * @return the abbreviation for the title
   */
  public String getAbbreviation() {
    return abbreviation;
  }

  /**
   * Returns the code for the title.
   *
   * @return the code for the title
   */
  public String getCode() {
    return code;
  }

  /**
   * Returns the description for the title.
   *
   * @return the description for the title
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the codes for the genders the title is applicable to.
   *
   * @return the codes for the genders the title is applicable to
   */
  @Schema(description = "The codes for the genders the title is applicable to", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Genders", required = true)
  public String[] getGenders() {
    return StringUtils.commaDelimitedListToStringArray(genders);
  }

  /**
   * Returns the Unicode locale identifier for the title.
   *
   * @return the Unicode locale identifier for the title
   */
  public String getLocaleId() {
    return localeId;
  }

  /**
   * Returns the name of the title.
   *
   * @return the name of the title
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the sort index for the title.
   *
   * @return the sort index for the title
   */
  public Integer getSortIndex() {
    return sortIndex;
  }

  /**
   * Returns the ID for the tenant the title is specific to.
   *
   * @return the ID for the tenant the title is specific to
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
   * Set the abbreviation for the title.
   *
   * @param abbreviation the abbreviation for the title
   */
  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  /**
   * Set the code for the title.
   *
   * @param code the code for the title
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Set the description for the title.
   *
   * @param description the description for the title
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Set the codes for the genders the title is applicable to.
   *
   * @param genders the codes for the genders the title is applicable to
   */
  public void setGenders(String[] genders) {
    this.genders = StringUtils.arrayToCommaDelimitedString(genders);
  }

  /**
   * Set the codes for the genders the title is applicable to.
   *
   * @param genders the codes for the genders the title is applicable to
   */
  @JsonIgnore
  public void setGenders(Collection<String> genders) {
    this.genders = StringUtils.collectionToDelimitedString(genders, ",");
  }

  /**
   * Set the Unicode locale identifier for the title.
   *
   * @param localeId the Unicode locale identifier for the title
   */
  public void setLocaleId(String localeId) {
    this.localeId = localeId;
  }

  /**
   * Set the name of the title.
   *
   * @param name the name of the title
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set the sort index for the title.
   *
   * @param sortIndex the sort index for the title
   */
  public void setSortIndex(Integer sortIndex) {
    this.sortIndex = sortIndex;
  }

  /**
   * Set the ID for the tenant the title is specific to.
   *
   * @param tenantId the ID for the tenant the title is specific to
   */
  public void setTenantId(UUID tenantId) {
    this.tenantId = tenantId;
  }
}
