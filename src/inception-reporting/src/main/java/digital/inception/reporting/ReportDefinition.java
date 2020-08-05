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

package digital.inception.reporting;

// ~--- non-JDK imports --------------------------------------------------------

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// ~--- JDK imports ------------------------------------------------------------

/**
 * The <code>ReportDefinition</code> class holds the information for a report definition.
 *
 * @author Marcus Portmann
 */
@Schema(description = "ReportDefinition")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "template"})
@XmlRootElement(name = "ReportDefinition", namespace = "http://reporting.inception.digital")
@XmlType(
    name = "ReportDefinition",
    namespace = "http://reporting.inception.digital",
    propOrder = {"id", "name", "template"})
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(schema = "reporting", name = "report_definitions")
@SuppressWarnings({"unused", "WeakerAccess"})
public class ReportDefinition implements Serializable {

  private static final long serialVersionUID = 1000000;

  /**
   * The ID uniquely identifying the report definition.
   */
  @Schema(description = "The ID uniquely identifying the report definition", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Id", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  /**
   * The name of the report definition.
   */
  @Schema(description = "The name of the report definition", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Name", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  /**
   * The JasperReports template for the report definition.
   */
  @Schema(description = "The JasperReports template for the report definition", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Template", required = true)
  @NotNull
  @Size(min = 1)
  @Column(name = "template", nullable = false)
  private byte[] template;

  /**
   * Constructs a new <code>ReportDefinition</code>.
   */
  public ReportDefinition() {
  }

  /**
   * Constructs a new <code>ReportDefinition</code>.
   *
   * @param id       the ID uniquely identifying the report definition
   * @param name     the name of the report definition
   * @param template the JasperReports template for the report definition
   */
  public ReportDefinition(String id, String name, byte[] template) {
    this.id = id;
    this.name = name;
    this.template = template;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param object the reference object with which to compare
   *
   * @return <code>true</code> if this object is the same as the object argument otherwise <code>
   * false</code>
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

    ReportDefinition other = (ReportDefinition) object;

    return (id != null) && id.equals(other.id);
  }

  /**
   * Returns the ID uniquely identifying the report definition.
   *
   * @return the ID uniquely identifying the report definition
   */
  public String getId() {
    return id;
  }

  /**
   * Set the ID uniquely identifying the report definition.
   *
   * @param id the ID uniquely identifying the report definition
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Returns the name of the report definition.
   *
   * @return the name of the report definition
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of the report definition.
   *
   * @param name the name of the report definition
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the JasperReports template for the report definition.
   *
   * @return the JasperReports template for the report definition
   */
  public byte[] getTemplate() {
    return template;
  }

  /**
   * Set the JasperReports template for the report definition.
   *
   * @param template the JasperReports template for the report definition
   */
  public void setTemplate(byte[] template) {
    this.template = template;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for the object
   */
  @Override
  public int hashCode() {
    return (id == null) ? 0 : id.hashCode();
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   */
  @Override
  public String toString() {
    return "ReportDefinition {id=\"" + getId() + "\", name=\"" + getName() + "\"}";
  }
}
