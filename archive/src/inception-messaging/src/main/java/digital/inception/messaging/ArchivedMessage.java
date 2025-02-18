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

package digital.inception.messaging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import digital.inception.core.util.ISO8601Util;
import digital.inception.core.xml.LocalDateTimeAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The <b>ArchivedMessage</b> class holds the information for an archived message.
 *
 * @author Marcus Portmann
 */
@Schema(description = "An archived message")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "id",
  "type",
  "username",
  "deviceId",
  "correlationId",
  "created",
  "archived",
  "data"
})
@XmlRootElement(name = "ArchivedMessage", namespace = "http://inception.digital/messaging")
@XmlType(
    name = "ArchivedMessage",
    namespace = "http://inception.digital/messaging",
    propOrder = {
      "id",
      "type",
      "username",
      "deviceId",
      "correlationId",
      "created",
      "archived",
      "data"
    })
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(schema = "messaging", name = "archived_messages")
@SuppressWarnings({"WeakerAccess"})
public class ArchivedMessage {

  /** The date and time the message was archived. */
  @Schema(description = "The date and time the message was archived", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Archived", required = true)
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  @XmlSchemaType(name = "dateTime")
  @Column(name = "archived", nullable = false)
  private LocalDateTime archived;

  /** The optional ID used to correlate the message. */
  @Schema(description = "The optional ID used to correlate the message")
  @JsonProperty
  @XmlElement(name = "CorrelationId")
  @Column(name = "correlation_id")
  private UUID correlationId;

  /** The date and time the message was created. */
  @Schema(description = "The date and time the message was created", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Created", required = true)
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  @XmlSchemaType(name = "dateTime")
  @NotNull
  @Column(name = "created", nullable = false)
  private LocalDateTime created;

  /** The data for the message. */
  @Schema(description = "The data for the message", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Data", required = true)
  @NotNull
  @Column(name = "data", nullable = false)
  private byte[] data;

  /** The ID for the device associated with the message. */
  @Schema(description = "The ID for the device associated with the message", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "DeviceId", required = true)
  @NotNull
  @Column(name = "device_id", nullable = false)
  private UUID deviceId;

  /** The ID for the message. */
  @Schema(description = "The ID for the message", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Id", required = true)
  @NotNull
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  /** The code for the message type. */
  @Schema(description = "The code for the message type", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Type", required = true)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "type", nullable = false)
  private String type;

  /** The username for the user associated with the message. */
  @Schema(description = "The username for the user associated with the message", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Username", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "username", length = 100, nullable = false)
  private String username;

  /** Constructs a new <b>ArchivedMessage</b>. */
  public ArchivedMessage() {}

  /**
   * Constructs a new <b>ArchivedMessage</b>.
   *
   * @param message the message to archive
   */
  public ArchivedMessage(Message message) {
    this.id = message.getId();
    this.type = message.getType();
    this.username = message.getUsername();
    this.deviceId = message.getDeviceId();
    this.correlationId = message.getCorrelationId();
    this.created = message.getCreated();
    this.archived = LocalDateTime.now();
    this.data = message.getData();
  }

  /**
   * Constructs a new <b>ArchivedMessage</b>.
   *
   * @param id the ID for the message
   * @param type the code for the message type
   * @param username the username for the user associated with the message
   * @param deviceId the ID for the device associated with the message
   * @param correlationId the optional ID used to correlate the message
   * @param created the date and time the message was created
   * @param archived the date and time the message was archived
   * @param data the data for the message which is NOT encrypted
   */
  public ArchivedMessage(
      UUID id,
      String type,
      String username,
      UUID deviceId,
      UUID correlationId,
      LocalDateTime created,
      LocalDateTime archived,
      byte[] data) {
    this.id = id;
    this.type = type;
    this.username = username;
    this.deviceId = deviceId;
    this.correlationId = correlationId;
    this.created = created;
    this.archived = archived;
    this.data = data;
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

    ArchivedMessage other = (ArchivedMessage) object;

    return Objects.equals(id, other.id);
  }

  /**
   * Returns the date and time the message was archived.
   *
   * @return the date and time the message was archived
   */
  public LocalDateTime getArchived() {
    return archived;
  }

  /**
   * Returns the ID used to correlate the message.
   *
   * @return the ID used to correlate the message
   */
  public UUID getCorrelationId() {
    return correlationId;
  }

  /**
   * Returns the date and time the message was created.
   *
   * @return the date and time the message was created
   */
  public LocalDateTime getCreated() {
    return created;
  }

  /**
   * Returns the data for the message which may be encrypted.
   *
   * @return the data for the message which may be encrypted
   */
  public byte[] getData() {
    return data;
  }

  /**
   * The ID for the device associated with the message.
   *
   * @return the ID for the device associated with the message
   */
  public UUID getDeviceId() {
    return deviceId;
  }

  /**
   * Returns the ID for the message.
   *
   * @return the ID for the message
   */
  public UUID getId() {
    return id;
  }

  /**
   * Returns the code for the message type.
   *
   * @return the code for the message type
   */
  public String getType() {
    return type;
  }

  /**
   * Returns the username for the user associated with the message.
   *
   * @return the username for the user associated with the message
   */
  public String getUsername() {
    return username;
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
   * Set the date and time the message was archived.
   *
   * @param archived the date and time the message was archived
   */
  public void setArchived(LocalDateTime archived) {
    this.archived = archived;
  }

  /**
   * Set the ID used to correlate the message.
   *
   * @param correlationId the ID used to correlate the message
   */
  public void setCorrelationId(UUID correlationId) {
    this.correlationId = correlationId;
  }

  /**
   * Set the date and time the message was created.
   *
   * @param created the date and time the message was created
   */
  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  /**
   * Set the data for the message which may be encrypted.
   *
   * @param data the data for the message which may be encrypted
   */
  public void setData(byte[] data) {
    this.data = data;
  }

  /**
   * Set the ID for the device associated with the message.
   *
   * @param deviceId the ID for the device associated with the message
   */
  public void setDeviceId(UUID deviceId) {
    this.deviceId = deviceId;
  }

  /**
   * Set the ID for the message.
   *
   * @param id the ID for the message
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**
   * Set the code for the message type.
   *
   * @param type the code for the message type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Set the username for the user associated with the message.
   *
   * @param username the username for the user associated with the message
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the String representation of the message.
   *
   * @return the String representation of the message
   */
  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder("<ArchivedMessage");

    buffer.append(" id=\"").append(id).append("\"");
    buffer.append(" type=\"").append(type).append("\"");
    buffer.append(" username=\"").append(username).append("\"");
    buffer.append(" deviceId=\"").append(deviceId).append("\"");

    buffer
        .append(" correlationId=\"")
        .append((correlationId != null) ? correlationId.toString() : "")
        .append("\"");
    buffer.append(" created=\"").append(ISO8601Util.fromLocalDateTime(created)).append("\"");
    buffer.append(" archived=\"").append(ISO8601Util.fromLocalDateTime(archived)).append("\"");

    buffer.append(">").append(data.length).append(" bytes of data</Message>");

    return buffer.toString();
  }
}
