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

package digital.inception.mail;

//~--- non-JDK imports --------------------------------------------------------

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.xml.bind.annotation.*;

/**
 * The <code>MailTemplateSummary</code> class holds the information for a mail template.
 *
 * @author Marcus Portmann
 */
@ApiModel(value = "MailTemplateSummary")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "contentType" })
@XmlRootElement(name = "MailTemplateSummary", namespace = "http://mail.inception.digital")
@XmlType(name = "MailTemplateSummary", namespace = "http://mail.inception.digital",
    propOrder = { "id", "name", "contentType" })
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(schema = "mail", name = "mail_templates")
@SuppressWarnings({ "unused" })
public class MailTemplateSummary
  implements Serializable
{
  private static final long serialVersionUID = 1000000;

  /**
   * The content type for the mail template.
   */
  @ApiModelProperty(value = "The content type for the mail template", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "ContentType", required = true)
  @NotNull
  @Column(name = "content_type", nullable = false)
  private MailTemplateContentType contentType;

  /**
   * The Universally Unique Identifier (UUID) used to uniquely identify the mail template.
   */
  @ApiModelProperty(
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the mail template",
      required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Id", required = true)
  @NotNull
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  /**
   * The name of the mail template.
   */
  @ApiModelProperty(value = "The name of the mail template", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Name", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  /**
   * Constructs a new <code>MailTemplateSummary</code>.
   */
  public MailTemplateSummary() {}

  /**
   * Constructs a new <code>MailTemplateSummary</code>.
   *
   * @param id          the Universally Unique Identifier (UUID) used to uniquely identify the mail
   *                    template
   * @param name        the name of the mail template
   * @param contentType the content type for the mail template
   */
  public MailTemplateSummary(UUID id, String name, MailTemplateContentType contentType)
  {
    this.id = id;
    this.name = name;
    this.contentType = contentType;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param object the reference object with which to compare
   *
   * @return <code>true</code> if this object is the same as the object argument otherwise
   *         <code>false</code>
   */
  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }

    if (object == null)
    {
      return false;
    }

    if (getClass() != object.getClass())
    {
      return false;
    }

    MailTemplateSummary other = (MailTemplateSummary) object;

    return (id != null) && id.equals(other.id);
  }

  /**
   * Returns the content type for the mail template.
   *
   * @return the content type for the mail template
   */
  public MailTemplateContentType getContentType()
  {
    return contentType;
  }

  /**
   * Returns the Universally Unique Identifier (UUID) used to uniquely identify the mail template.
   *
   * @return the Universally Unique Identifier (UUID) used to uniquely identify the mail template
   */
  public UUID getId()
  {
    return id;
  }

  /**
   * Returns the name of the mail template.
   *
   * @return the name of the mail template
   */
  public String getName()
  {
    return name;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for the object
   */
  @Override
  public int hashCode()
  {
    return (id == null)
        ? 0
        : id.hashCode();
  }

  /**
   * Set the content type for the mail template.
   *
   * @param contentType the content type for the mail template
   */
  public void setContentType(MailTemplateContentType contentType)
  {
    this.contentType = contentType;
  }

  /**
   * Set the Universally Unique Identifier (UUID) used to uniquely identify the mail template.
   *
   * @param id the Universally Unique Identifier (UUID) used to uniquely identify the mail template
   */
  public void setId(UUID id)
  {
    this.id = id;
  }

  /**
   * Set the name of the mail template.
   *
   * @param name the name of the mail template
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   */
  @Override
  public String toString()
  {
    return "MailTemplateSummary {id=\"" + getId() + "\", name=\"" + getName()
        + "\", contentType=\"" + getContentType() + "\"}";
  }
}
