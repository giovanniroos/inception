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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import digital.inception.core.xml.LocalDateAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The <b>SourceOfWealth</b> class holds the information for a source of wealth for a person.
 *
 * @author Marcus Portmann
 */
@Schema(description = "A source of wealth for a person")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"type", "effectiveFrom", "effectiveTo", "description"})
@XmlRootElement(name = "SourceOfWealth", namespace = "http://inception.digital/party")
@XmlType(
    name = "SourceOfWealth",
    namespace = "http://inception.digital/party",
    propOrder = {"type", "effectiveFrom", "effectiveTo", "description"})
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(schema = "party", name = "sources_of_wealth")
@IdClass(SourceOfWealthId.class)
public class SourceOfWealth implements Serializable {

  private static final long serialVersionUID = 1000000;

  /** The description for the source of wealth. */
  @Schema(description = "The description for the source of wealth")
  @JsonProperty
  @XmlElement(name = "Description")
  @Column(name = "description", length = 100)
  private String description;

  /** The date the source of wealth is effective from. */
  @Schema(description = "The ISO 8601 format date the source of wealth is effective from")
  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @XmlElement(name = "EffectiveFrom")
  @XmlJavaTypeAdapter(LocalDateAdapter.class)
  @XmlSchemaType(name = "date")
  @Column(name = "effective_from")
  private LocalDate effectiveFrom;

  /** The date the source of wealth is effective to. */
  @Schema(description = "The ISO 8601 format date the source of wealth is effective to")
  @JsonProperty
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @XmlElement(name = "EffectiveTo")
  @XmlJavaTypeAdapter(LocalDateAdapter.class)
  @XmlSchemaType(name = "date")
  @Column(name = "effective_to")
  private LocalDate effectiveTo;

  /** The person the source of wealth is associated with. */
  @Schema(hidden = true)
  @JsonBackReference("sourceOfWealthReference")
  @XmlTransient
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "person_id")
  private Person person;

  /** The code for the source of wealth type. */
  @Schema(description = "The code for the source of wealth type", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Type", required = true)
  @NotNull
  @Size(min = 1, max = 30)
  @Id
  @Column(name = "type", length = 30, nullable = false)
  private String type;

  /** Constructs a new <b>SourceOfWealth</b>. */
  public SourceOfWealth() {}

  /**
   * Constructs a new <b>SourceOfWealth</b>.
   *
   * @param type the code for the source of wealth type
   */
  public SourceOfWealth(String type) {
    this.type = type;
  }

  /**
   * Constructs a new <b>SourceOfWealth</b>.
   *
   * @param type the code for the source of wealth type
   * @param effectiveFrom the date the source of wealth is effective from
   */
  public SourceOfWealth(String type, LocalDate effectiveFrom) {
    this.type = type;
    this.effectiveFrom = effectiveFrom;
  }

  /**
   * Constructs a new <b>SourceOfWealth</b>.
   *
   * @param type the code for the source of wealth type
   * @param effectiveFrom the date the source of wealth is effective from
   * @param effectiveTo the date the source of wealth is effective to
   */
  public SourceOfWealth(String type, LocalDate effectiveFrom, LocalDate effectiveTo) {
    this.type = type;
    this.effectiveFrom = effectiveFrom;
    this.effectiveTo = effectiveTo;
  }

  /**
   * Constructs a new <b>SourceOfWealth</b>.
   *
   * @param type the code for the source of wealth type
   * @param description the description for the source of wealth
   */
  public SourceOfWealth(String type, String description) {
    this.type = type;
    this.description = description;
  }

  /**
   * Constructs a new <b>SourceOfWealth</b>.
   *
   * @param type the code for the source of wealth type
   * @param description the description for the source of wealth
   * @param effectiveFrom the date the source of wealth is effective from
   */
  public SourceOfWealth(String type, String description, LocalDate effectiveFrom) {
    this.type = type;
    this.description = description;
    this.effectiveFrom = effectiveFrom;
  }

  /**
   * Constructs a new <b>SourceOfWealth</b>.
   *
   * @param type the code for the source of wealth type
   * @param description the description for the source of wealth
   * @param effectiveFrom the date the source of wealth is effective from
   * @param effectiveTo the date the source of wealth is effective to
   */
  public SourceOfWealth(
      String type, String description, LocalDate effectiveFrom, LocalDate effectiveTo) {
    this.type = type;
    this.description = description;
    this.effectiveFrom = effectiveFrom;
    this.effectiveTo = effectiveTo;
  }

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

    SourceOfWealth other = (SourceOfWealth) object;

    return Objects.equals(person, other.person) && Objects.equals(type, other.type);
  }

  /**
   * Returns the description for the source of wealth.
   *
   * @return the description for the source of wealth
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the date the source of wealth is effective from.
   *
   * @return the date the source of wealth is effective from
   */
  public LocalDate getEffectiveFrom() {
    return effectiveFrom;
  }

  /**
   * Returns the date the source of wealth is effective to.
   *
   * @return the date the source of wealth is effective to
   */
  public LocalDate getEffectiveTo() {
    return effectiveTo;
  }

  /**
   * Returns the person the source of wealth is associated with.
   *
   * @return the person the source of wealth is associated with
   */
  @Schema(hidden = true)
  public Person getPerson() {
    return person;
  }

  /**
   * Returns the code for the source of wealth type.
   *
   * @return the code for the source of wealth type
   */
  public String getType() {
    return type;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for the object
   */
  @Override
  public int hashCode() {
    return (((person == null) || (person.getId() == null)) ? 0 : person.getId().hashCode())
        + ((type == null) ? 0 : type.hashCode());
  }

  /**
   * Set the description for the source of wealth.
   *
   * @param description the description for the source of wealth
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Set the date the source of wealth is effective from.
   *
   * @param effectiveFrom the date the source of wealth is effective from
   */
  public void setEffectiveFrom(LocalDate effectiveFrom) {
    this.effectiveFrom = effectiveFrom;
  }

  /**
   * Set the date the source of wealth is effective to.
   *
   * @param effectiveTo the date the source of wealth is effective to
   */
  public void setEffectiveTo(LocalDate effectiveTo) {
    this.effectiveTo = effectiveTo;
  }

  /**
   * Set the person the source of wealth is associated with.
   *
   * @param person the person the source of wealth is associated with
   */
  @Schema(hidden = true)
  public void setPerson(Person person) {
    this.person = person;
  }

  /**
   * Set the code for the source of wealth type.
   *
   * @param type the code for the source of wealth type
   */
  public void setType(String type) {
    this.type = type;
  }
}
