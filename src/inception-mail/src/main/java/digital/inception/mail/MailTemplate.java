/*
 * Copyright 2021 Marcus Portmann
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

package digital.inception.mail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
 * The <b>MailTemplate</b> class holds the information for a mail template.
 *
 * @author Marcus Portmann
 */
@Schema(description = "A template for an e-mail sent by an application")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "contentType", "template"})
@XmlRootElement(name = "MailTemplate", namespace = "http://inception.digital/mail")
@XmlType(
    name = "MailTemplate",
    namespace = "http://inception.digital/mail",
    propOrder = {"id", "name", "contentType", "template"})
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(schema = "mail", name = "mail_templates")
@SuppressWarnings({"unused"})
public class MailTemplate implements Serializable {

  private static final long serialVersionUID = 1000000;

  /** The content type for the mail template. */
  @Schema(description = "The content type for the mail template", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "ContentType", required = true)
  @NotNull
  @Column(name = "content_type", nullable = false)
  private MailTemplateContentType contentType;

  /** The date and time the mail template was created. */
  @JsonIgnore
  @XmlTransient
  @Column(name = "created", nullable = false, updatable = false)
  private LocalDateTime created;

  /** The ID for the mail template. */
  @Schema(description = "The ID for the mail template", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Id", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Id
  @Column(name = "id", length = 100, nullable = false)
  private String id;

  /** The name of the mail template. */
  @Schema(description = "The name of the mail template", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Name", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name", length = 100, nullable = false)
  private String name;

  /** The Apache FreeMarker template for the mail template. */
  @Schema(description = "The Apache FreeMarker template for the mail template", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Template", required = true)
  @NotNull
  @Size(min = 1, max = 10485760)
  @Column(name = "template", nullable = false)
  private byte[] template;

  /** The date and time the mail template was last updated. */
  @JsonIgnore
  @XmlTransient
  @Column(name = "updated", insertable = false)
  private LocalDateTime updated;

  /** Constructs a new <b>MailTemplate</b>. */
  public MailTemplate() {}

  /**
   * Constructs a new <b>MailTemplate</b>.
   *
   * @param id the ID for the mail template
   * @param name the name of the mail template
   * @param contentType the content type for the mail template
   * @param template the Apache FreeMarker template for the mail template
   */
  public MailTemplate(
      String id, String name, MailTemplateContentType contentType, byte[] template) {
    this.id = id;
    this.name = name;
    this.contentType = contentType;
    this.template = template;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param object the reference object with which to compare
   * @return <b>true</b> if this object is the same as the object argument otherwise <b> false</b>
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

    MailTemplate other = (MailTemplate) object;

    return Objects.equals(id, other.id);
  }

  /**
   * Returns the content type for the mail template.
   *
   * @return the content type for the mail template
   */
  public MailTemplateContentType getContentType() {
    return contentType;
  }

  /**
   * Returns the date and time the mail template was created.
   *
   * @return the date and time the mail template was created
   */
  public LocalDateTime getCreated() {
    return created;
  }

  /**
   * Returns the ID for the mail template.
   *
   * @return the ID for the mail template
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the name of the mail template.
   *
   * @return the name of the mail template
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the Apache FreeMarker template for the mail template.
   *
   * @return the Apache FreeMarker template for the mail template
   */
  public byte[] getTemplate() {
    return template;
  }

  /**
   * Returns the date and time the mail template was last updated.
   *
   * @return the date and time the mail template was last updated
   */
  public LocalDateTime getUpdated() {
    return updated;
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
   * Set the content type for the mail template.
   *
   * @param contentType the content type for the mail template
   */
  public void setContentType(MailTemplateContentType contentType) {
    this.contentType = contentType;
  }

  /**
   * Set the ID for the mail template.
   *
   * @param id the ID for the mail template
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Set the name of the mail template.
   *
   * @param name the name of the mail template
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set the Apache FreeMarker template for the mail template.
   *
   * @param template the Apache FreeMarker template for the mail template
   */
  public void setTemplate(byte[] template) {
    this.template = template;
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   */
  @Override
  public String toString() {
    return "MailTemplate {id=\""
        + getId()
        + "\", name=\""
        + getName()
        + "\", contentType=\""
        + getContentType()
        + "\"}";
  }

  @PrePersist
  protected void onCreate() {
    created = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updated = LocalDateTime.now();
  }
}
