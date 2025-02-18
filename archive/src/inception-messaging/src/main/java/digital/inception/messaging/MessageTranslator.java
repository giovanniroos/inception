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

import digital.inception.core.util.Base64Util;
import digital.inception.core.util.CryptoUtil;
import java.security.MessageDigest;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.util.StringUtils;

/**
 * The <b>MessageTranslator</b> class provides the facilities to create messages containing WBXML
 * message data. It also provides facilities to retrieve the WBXML message data from a message.
 *
 * @author Marcus Portmann
 */
@SuppressWarnings({"WeakerAccess"})
public class MessageTranslator {

  private static final ThreadLocal<MessageDigest> threadLocalMessageDigest =
      ThreadLocal.withInitial(
          () -> {
            try {
              return MessageDigest.getInstance("SHA-256");
            } catch (Throwable e) {
              throw new RuntimeException("Failed to initialize the SHA-256 message digest", e);
            }
          });

  /** The ID for the device associated with the message. */
  private final UUID deviceId;

  /** The encryption key used to encrypt or decrypt the message data. */
  private final byte[] encryptionKey;

  /** The username for the user responsible for the message. */
  private final String username;

  /**
   * Constructs a new <b>MessageTranslator</b>.
   *
   * @param username the username for the username responsible for the message
   * @param deviceId the ID for the device associated with the message
   */
  public MessageTranslator(String username, UUID deviceId) {
    this.username = username;
    this.deviceId = deviceId;
    this.encryptionKey = null;
  }

  /**
   * Constructs a new <b>MessageTranslator</b>.
   *
   * @param username the username for the user responsible for the message
   * @param deviceId the ID for the device associated with the message
   * @param encryptionKey the key used to encrypt or decrypt the message data
   */
  public MessageTranslator(String username, UUID deviceId, byte[] encryptionKey) {
    this.username = username;
    this.deviceId = deviceId;
    this.encryptionKey = encryptionKey;
  }

  /**
   * Decrypt the message data.
   *
   * @param encryptionKey the encryption key to use to decrypt the message data
   * @param encryptionIV the encryption initialization vector
   * @param data the message data to decrypt
   * @return the decrypted message data
   * @throws MessagingException if the message data could not be decrypted
   */
  public static byte[] decryptMessageData(byte[] encryptionKey, byte[] encryptionIV, byte[] data)
      throws MessagingException {
    if ((encryptionKey == null) || (encryptionKey.length == 0)) {
      throw new MessagingException("Failed to decrypt the message data: Invalid encryption key");
    }

    try {
      SecretKey secretKey = new SecretKeySpec(encryptionKey, CryptoUtil.AES_KEY_SPEC);
      IvParameterSpec iv = new IvParameterSpec(encryptionIV);
      Cipher cipher = Cipher.getInstance(CryptoUtil.AES_TRANSFORMATION_NAME);

      cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

      return cipher.doFinal(data);
    } catch (Throwable e) {
      throw new MessagingException("Failed to decrypt the message data", e);
    }
  }

  /**
   * Encrypt the message data.
   *
   * @param encryptionKey the encryption key to use to encrypt the message data
   * @param encryptionIV the encryption initialization vector
   * @param data the message data to encrypt
   * @return the encrypted message data
   * @throws MessagingException if the message data could not be encrypted
   */
  public static byte[] encryptMessageData(byte[] encryptionKey, byte[] encryptionIV, byte[] data)
      throws MessagingException {
    if (encryptionKey == null) {
      throw new MessagingException("Failed to encrypt the message data: Invalid encryption key");
    }

    try {
      SecretKey secretKey = new SecretKeySpec(encryptionKey, CryptoUtil.AES_KEY_SPEC);
      IvParameterSpec iv = new IvParameterSpec(encryptionIV);
      Cipher cipher = Cipher.getInstance(CryptoUtil.AES_TRANSFORMATION_NAME);

      cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

      return cipher.doFinal(data);
    } catch (Throwable e) {
      throw new MessagingException("Failed to encrypt the message data", e);
    }
  }

  /**
   * Retrieve the WBXML-based message data from the message.
   *
   * @param message the message
   * @param messageData the WBXML-based message data object to populate
   * @param <T> the message data type for the WBXML-based message data
   * @return the WBXML-based message data
   * @throws MessagingException if the WBXML-based message data could not be retrieved from the
   *     message
   */
  public <T extends WbxmlMessageData> T fromMessage(Message message, T messageData)
      throws MessagingException {
    byte[] data = message.getData();

    // Decrypt the message if required
    if (message.isEncrypted()) {
      data =
          decryptMessageData(
              encryptionKey,
              StringUtils.hasText(message.getEncryptionIV())
                  ? Base64Util.decode(message.getEncryptionIV())
                  : new byte[0],
              message.getData());

      // Retrieve the SHA-256 hash of the unencrypted message data
      String dataHash = getMessageDataHash(data);

      if (!message.getDataHash().equals(dataHash)) {
        throw new MessagingException(
            String.format(
                "Failed to decrypt the message data since the data hash for the message (%s) does not match the hash for the message data (%s)",
                message.getDataHash(), dataHash));
      }
    }

    // Check that the message type for the message data and the specified message match
    if (!messageData.getMessageType().equals(message.getType())) {
      throw new MessagingException(
          String.format(
              "The message type for the message (%s) does not match the message type for the message data (%s)",
              message.getType(), messageData.getMessageType()));
    }

    /*
     * Populate the message data instance with the information contained in the WBXML data for the
     * message.
     */
    if (messageData.fromMessageData(data)) {
      return messageData;
    } else {
      throw new MessagingException(
          String.format(
              "Failed to populate the instance of the message data class (%s) from the WBXML data for the message",
              messageData.getClass().getName()));
    }
  }

  /**
   * Returns the message containing the WBXML-based message data.
   *
   * @param messageData the WBXML-based message data
   * @param correlationId the ID used to correlate the message
   * @return the message that can be sent via the messaging infrastructure
   * @throws MessagingException if the message containing the WBXML-based message data could not be
   *     created
   */
  public Message toMessage(WbxmlMessageData messageData, UUID correlationId)
      throws MessagingException {
    if (!StringUtils.hasText(username)) {
      throw new MessagingException(
          String.format(
              "Failed to create the message with type (%s): A username has not been specified",
              messageData.getMessageType()));
    }

    if (deviceId == null) {
      throw new MessagingException(
          String.format(
              "Failed to create the message with type (%s): A device ID has not been specified",
              messageData.getMessageType()));
    }

    byte[] data = messageData.toMessageData();

    // Encrypt the message data
    if (encryptionKey != null) {
      // Retrieve the SHA-256 hash of the unencrypted message data
      String dataHash = getMessageDataHash(data);

      byte[] encryptionIV = CryptoUtil.createRandomEncryptionIV(CryptoUtil.AES_BLOCK_SIZE);

      data = encryptMessageData(encryptionKey, encryptionIV, data);

      return new Message(
          messageData.getMessageType(),
          username,
          deviceId,
          correlationId,
          messageData.getMessageTypePriority(),
          data,
          dataHash,
          (encryptionIV.length == 0) ? "" : Base64Util.encodeBytes(encryptionIV));
    } else {
      return new Message(
          messageData.getMessageType(),
          username,
          deviceId,
          correlationId,
          messageData.getMessageTypePriority(),
          data);
    }
  }

  /**
   * Returns the message containing the WBXML-based message data.
   *
   * @param messageData the WBXML-based message data
   * @return the message that can be sent via the messaging infrastructure
   * @throws MessagingException if the message containing the WBXML-based message data could not be
   *     created
   */
  public Message toMessage(WbxmlMessageData messageData) throws MessagingException {
    return toMessage(messageData, null);
  }

  /**
   * Generate the SHA-256 hash for the message data.
   *
   * @param data the message data to return the SHA-256 hash for
   * @return the SHA-256 hash for the message data
   * @throws MessagingException if the SHA-256 hash for the message data could not be generated
   */
  private String getMessageDataHash(byte[] data) throws MessagingException {
    try {
      return Base64Util.encodeBytes(threadLocalMessageDigest.get().digest(data));
    } catch (Throwable e) {
      throw new MessagingException("Failed to generate the SHA-256 hash for the message data", e);
    }
  }
}
