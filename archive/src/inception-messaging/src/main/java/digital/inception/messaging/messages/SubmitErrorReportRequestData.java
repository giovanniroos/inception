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

package digital.inception.messaging.messages;

import digital.inception.core.util.ISO8601Util;
import digital.inception.core.wbxml.Document;
import digital.inception.core.wbxml.Element;
import digital.inception.core.wbxml.Encoder;
import digital.inception.messaging.MessagePriority;
import digital.inception.messaging.MessagingException;
import digital.inception.messaging.WbxmlMessageData;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import org.springframework.util.StringUtils;

/**
 * The <b>SubmitErrorReportRequestData</b> class manages the data for a "Submit Error Report
 * Request" message.
 *
 * <p>This is an asynchronous message.
 *
 * @author Marcus Portmann
 */
public class SubmitErrorReportRequestData extends WbxmlMessageData {

  /** The message type code for the "Submit Error Report Request" message. */
  public static final String MESSAGE_TYPE = "SubmitErrorReportRequest";

  /** The ID for the application that generated the error report. */
  private String applicationId;

  /** The version of the application that generated the error report. */
  private String applicationVersion;

  /** The date and time the error report was created. */
  private LocalDateTime created;

  /** The optional base-64 encoded data associated with the error report. */
  private String data;

  /** The description of the error. */
  private String description;

  /** The error detail. */
  private String detail;

  /** The ID for the device the error report originated from. */
  private UUID deviceId;

  /** The feedback provided by the user for the error. */
  private String feedback;

  /** The ID for the error report. */
  private UUID id;

  /** The username for the user associated with the error report. */
  private String who;

  /** Constructs a new <b>SubmitErrorReportRequestData</b>. */
  public SubmitErrorReportRequestData() {
    super(MESSAGE_TYPE, MessagePriority.HIGH);
  }

  /**
   * Constructs a new <b>SubmitErrorReportRequestData</b>.
   *
   * @param id the ID for the error report
   * @param applicationId the ID for the application that generated the error report
   * @param applicationVersion the version of the application that generated the error report
   * @param description the description of the error
   * @param detail the error detail
   * @param feedback the feedback provided by the user for the error
   * @param created the date and time the error report was created
   * @param who the username for the user associated with the error report
   * @param deviceId the ID for the device the error report originated from
   * @param data the optional Base-64 encoded data associated with the error report
   */
  public SubmitErrorReportRequestData(
      UUID id,
      String applicationId,
      String applicationVersion,
      String description,
      String detail,
      String feedback,
      LocalDateTime created,
      String who,
      UUID deviceId,
      String data) {
    super(MESSAGE_TYPE, MessagePriority.HIGH);

    this.id = id;
    this.applicationId = applicationId;
    this.applicationVersion = applicationVersion;
    this.description = description;
    this.detail = detail;
    this.feedback = feedback;
    this.created = created;
    this.who = who;
    this.deviceId = deviceId;
    this.data = data;
  }

  /**
   * Extract the message data from the WBXML data for a message.
   *
   * @param messageData the WBXML data for the message
   * @return <b>true</b> if the message data was extracted successfully from the WBXML data or
   *     <b>false</b> otherwise
   */
  @Override
  public boolean fromMessageData(byte[] messageData) throws MessagingException {
    Document document = parseWBXML(messageData);

    Element rootElement = document.getRootElement();

    if (!rootElement.getName().equals("SubmitErrorReportRequest")) {
      return false;
    }

    if ((!rootElement.hasChild("Id"))
        || (!rootElement.hasChild("ApplicationId"))
        || (!rootElement.hasChild("ApplicationVersion"))
        || (!rootElement.hasChild("Description"))
        || (!rootElement.hasChild("Detail"))
        || (!rootElement.hasChild("Feedback"))
        || (!rootElement.hasChild("Created"))
        || (!rootElement.hasChild("Who"))
        || (!rootElement.hasChild("DeviceId"))) {
      return false;
    }

    rootElement.getChildText("Id").ifPresent(id -> this.id = UUID.fromString(id));

    rootElement
        .getChildText("ApplicationId")
        .ifPresent(applicationId -> this.applicationId = applicationId);

    rootElement
        .getChildText("ApplicationVersion")
        .ifPresent(applicationVersion -> this.applicationVersion = applicationVersion);

    rootElement
        .getChildText("Description")
        .ifPresent(description -> this.description = description);

    rootElement.getChildText("Detail").ifPresent(detail -> this.detail = detail);

    rootElement.getChildText("Feedback").ifPresent(feedback -> this.feedback = feedback);

    rootElement
        .getChildText("Created")
        .ifPresent(
            createdValue -> {
              if (createdValue.contains("T")) {
                try {
                  this.created = ISO8601Util.toLocalDateTime(createdValue);
                } catch (Throwable e) {
                  throw new RuntimeException(
                      "Failed to parse the When ISO8601 timestamp ("
                          + createdValue
                          + ") for the \"Submit Error Report Request\" message",
                      e);
                }
              } else {
                this.created =
                    LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(Long.parseLong(createdValue)),
                        ZoneId.systemDefault());
              }
            });

    rootElement.getChildText("Who").ifPresent(who -> this.who = who);

    rootElement
        .getChildText("DeviceId")
        .ifPresent(deviceId -> this.deviceId = UUID.fromString(deviceId));

    rootElement.getChildText("Data").ifPresent(data -> this.data = data);

    return true;
  }

  /**
   * Returns the ID for the application that generated the error report.
   *
   * @return the ID for the application that generated the error report
   */
  public String getApplicationId() {
    return applicationId;
  }

  /**
   * Returns the version of the application that generated the error report.
   *
   * @return the version of the application that generated the error report
   */
  public String getApplicationVersion() {
    return applicationVersion;
  }

  /**
   * Returns the date and time the error report was created.
   *
   * @return the date and time the error report was created
   */
  public LocalDateTime getCreated() {
    return created;
  }

  /**
   * Returns the optional base-64 encoded data associated with the error report.
   *
   * @return the optional base-64 encoded data associated with the error report
   */
  public String getData() {
    return data;
  }

  /**
   * Returns the description of the error.
   *
   * @return the description of the error
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the error detail.
   *
   * @return the error detail
   */
  public String getDetail() {
    return detail;
  }

  /**
   * Returns the ID for the device the error report originated from.
   *
   * @return the ID for the device the error report originated from
   */
  public UUID getDeviceId() {
    return deviceId;
  }

  /**
   * Returns the feedback provided by the user for the error.
   *
   * @return the feedback provided by the user for the error
   */
  public String getFeedback() {
    return feedback;
  }

  /**
   * Returns the ID for the error report.
   *
   * @return the ID for the error report
   */
  public UUID getId() {
    return id;
  }

  /**
   * Returns the username for the user associated with the error report.
   *
   * @return the username for the user associated with the error report
   */
  public String getWho() {
    return who;
  }

  /**
   * Returns the WBXML data representation of the message data that will be sent as part of a
   * message.
   *
   * @return the WBXML data representation of the message data that will be sent as part of a
   *     message
   */
  @Override
  public byte[] toMessageData() {
    Element rootElement = new Element("SubmitErrorReportRequest");

    rootElement.addContent(new Element("Id", id.toString()));
    rootElement.addContent(new Element("ApplicationId", applicationId));
    rootElement.addContent(new Element("ApplicationVersion", applicationVersion));
    rootElement.addContent(
        new Element("Description", StringUtils.hasText(description) ? description : ""));
    rootElement.addContent(new Element("Detail", StringUtils.hasText(detail) ? detail : ""));
    rootElement.addContent(new Element("Feedback", StringUtils.hasText(feedback) ? feedback : ""));
    rootElement.addContent(
        new Element(
            "Created",
            (created == null) ? ISO8601Util.now() : ISO8601Util.fromLocalDateTime(created)));
    rootElement.addContent(new Element("Who", StringUtils.hasText(who) ? who : ""));
    rootElement.addContent(new Element("DeviceId", deviceId.toString()));

    if (data != null) {
      rootElement.addContent(new Element("Data", data));
    }

    Document document = new Document(rootElement);

    Encoder encoder = new Encoder(document);

    return encoder.getData();
  }
}
