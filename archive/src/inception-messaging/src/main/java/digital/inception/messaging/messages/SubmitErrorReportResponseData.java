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

import digital.inception.core.wbxml.Document;
import digital.inception.core.wbxml.Element;
import digital.inception.core.wbxml.Encoder;
import digital.inception.messaging.MessagePriority;
import digital.inception.messaging.MessagingException;
import digital.inception.messaging.WbxmlMessageData;
import java.util.UUID;
import org.springframework.util.StringUtils;

/**
 * The <b>SubmitErrorReportResponseData</b> class manages the data for a "Submit Error Report
 * Response" message.
 *
 * <p>This is an asynchronous message.
 *
 * @author Marcus Portmann
 */
public class SubmitErrorReportResponseData extends WbxmlMessageData {

  /** The message returned when a submitted error report is successfully processed. */
  public static final String ERROR_MESSAGE_SUCCESS = "Success";

  /** The message type code for the "Submit Error Report Response" message. */
  public static final String MESSAGE_TYPE = "SubmitErrorReportResponse";

  /**
   * The error code indicating the result of processing the submitted error report where a code of
   * '0' indicates success and a non-zero code indicates an error condition.
   */
  private int errorCode;

  /** The error message describing the result of processing the submitted error report. */
  private String errorMessage;

  /** The ID for the error report that was submitted for processing. */
  private UUID errorReportId;

  /** Constructs a new <b>SubmitErrorReportResponseData</b>. */
  public SubmitErrorReportResponseData() {
    super(MESSAGE_TYPE, MessagePriority.HIGH);
  }

  /**
   * Constructs a new <b>SubmitErrorReportResponseData</b>.
   *
   * @param errorCode the error code indicating the result of processing the submitted error report
   * @param errorMessage the error message describing the result of processing the submitted error
   *     report
   * @param errorReportId the ID for the error report that was submitted for processing
   */
  public SubmitErrorReportResponseData(int errorCode, String errorMessage, UUID errorReportId) {
    super(MESSAGE_TYPE, MessagePriority.HIGH);

    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errorReportId = errorReportId;
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

    if (!rootElement.getName().equals("SubmitErrorReportResponse")) {
      return false;
    }

    if ((!rootElement.hasChild("ErrorCode"))
        || (!rootElement.hasChild("ErrorMessage"))
        || (!rootElement.hasChild("ErrorReportId"))) {
      return false;
    }

    rootElement
        .getChildText("ErrorCode")
        .ifPresent(errorCode -> this.errorCode = Integer.parseInt(errorCode));

    rootElement
        .getChildText("ErrorMessage")
        .ifPresent(errorMessage -> this.errorMessage = errorMessage);

    rootElement
        .getChildText("ErrorReportId")
        .ifPresent(errorReportId -> this.errorReportId = UUID.fromString(errorReportId));

    return true;
  }

  /**
   * Returns the error code indicating the result of processing the submitted error report where a
   * code of '0' indicates success and a non-zero code indicates an error condition.
   *
   * @return the error code indicating the result of processing the submitted error report where a
   *     code of '0' indicates success and a non-zero code indicates an error condition
   */
  public int getErrorCode() {
    return errorCode;
  }

  /**
   * Returns the error message describing the result of processing the submitted error report.
   *
   * @return the error message describing the result of processing the submitted error report
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Returns the ID for the error report that was submitted for processing.
   *
   * @return the ID for the error report that was submitted for processing
   */
  public UUID getErrorReportId() {
    return errorReportId;
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
    Element rootElement = new Element("SubmitErrorReportResponse");

    rootElement.addContent(new Element("ErrorCode", String.valueOf(errorCode)));
    rootElement.addContent(
        new Element("ErrorMessage", StringUtils.hasText(errorMessage) ? errorMessage : ""));
    rootElement.addContent(new Element("ErrorReportId", errorReportId.toString()));

    Document document = new Document(rootElement);

    Encoder encoder = new Encoder(document);

    return encoder.getData();
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   */
  @Override
  public String toString() {
    return "SubmitErrorReportResponseData {errorReportId=\""
        + errorReportId
        + "\", "
        + "errorCode=\""
        + errorCode
        + "\", errorMessage=\""
        + (StringUtils.hasText(errorMessage) ? errorMessage : "")
        + "\"}";
  }
}
