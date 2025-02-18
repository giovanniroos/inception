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

import digital.inception.core.wbxml.Document;
import digital.inception.core.wbxml.Element;
import digital.inception.core.wbxml.Encoder;
import java.util.UUID;

/**
 * The <b>MessageReceivedRequest</b> class represents a request sent by a mobile device to
 * acknowledge the successful download of a message.
 *
 * @author Marcus Portmann
 */
@SuppressWarnings({"WeakerAccess"})
public class MessageReceivedRequest {

  /** The ID for the device the message received request originated from. */
  private UUID deviceId;

  /** The ID for the message that was successfully downloaded. */
  private UUID messageId;

  /**
   * Constructs a new <b>MessageReceivedRequest</b> and populates it from the information stored in
   * the specified WBXML document.
   *
   * @param document the WBXML document containing the message received request information
   */
  public MessageReceivedRequest(Document document) {
    Element rootElement = document.getRootElement();

    rootElement
        .getAttributeValue("deviceId")
        .ifPresent(
            deviceId -> {
              this.deviceId = UUID.fromString(deviceId);
            });

    rootElement
        .getAttributeValue("messageId")
        .ifPresent(
            messageId -> {
              this.messageId = UUID.fromString(messageId);
            });
  }

  /**
   * Constructs a new <b>MessageReceivedRequest</b>.
   *
   * @param deviceId the ID for the device the message received request originated from
   * @param messageId the ID for the message that was successfully downloaded
   */
  public MessageReceivedRequest(UUID deviceId, UUID messageId) {
    this.deviceId = deviceId;
    this.messageId = messageId;
  }

  /**
   * Returns <b>true</b> if the WBXML document contains valid message received request information
   * or <b>false</b> otherwise.
   *
   * @param document the WBXML document to validate
   * @return <b>true</b> if the WBXML document contains valid message received request information
   *     or <b>false</b> otherwise
   */
  public static boolean isValidWBXML(Document document) {
    Element rootElement = document.getRootElement();

    return rootElement.getName().equals("MessageReceivedRequest")
        && (rootElement.getAttributes().size() == 2)
        && rootElement.hasAttribute("deviceId")
        && rootElement.hasAttribute("messageId");
  }

  /**
   * Returns the ID for the device the message received request originated from.
   *
   * @return the ID for the device the message received request originated from
   */
  public UUID getDeviceId() {
    return deviceId;
  }

  /**
   * Returns the ID for the message that was successfully downloaded.
   *
   * @return the ID for the message that was successfully downloaded
   */
  public UUID getMessageId() {
    return messageId;
  }

  /**
   * Returns the String representation of the message received request.
   *
   * @return the String representation of the message received request.
   */
  @Override
  public String toString() {
    return String.format(
        "<MessageReceivedRequest deviceId=\"%s\" messageId=\"%s\"/>", deviceId, messageId);
  }

  /**
   * Returns the WBXML representation of the message received request.
   *
   * @return the WBXML representation of the message received request
   */
  public byte[] toWBXML() {
    Element rootElement = new Element("MessageReceivedRequest");

    rootElement.setAttribute("deviceId", deviceId.toString());
    rootElement.setAttribute("messageId", messageId.toString());

    Encoder encoder = new Encoder(new Document(rootElement));

    return encoder.getData();
  }
}
