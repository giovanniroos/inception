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

import digital.inception.core.service.InvalidArgumentException;
import digital.inception.core.service.ServiceUnavailableException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The <b>IMessagingService</b> interface defines the interface for the Messaging Service for the
 * messaging infrastructure.
 *
 * @author Marcus Portmann
 */
@SuppressWarnings("unused")
public interface IMessagingService {

  /**
   * Have all the parts been queued for assembly for the message?
   *
   * @param messageId the ID for the message
   * @param totalParts the total number of parts for the message
   * @return <b>true</b> if all the parts for the message have been queued for assembly or
   *     <b>false</b> otherwise
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the check for whether all parts of the message have been
   *     queued for assembly failed
   */
  boolean allMessagePartsForMessageQueuedForAssembly(UUID messageId, int totalParts)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Archive the message.
   *
   * @param message the message to archive
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message could not be archived
   */
  void archiveMessage(Message message) throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Assemble the message from the message parts that have been queued for assembly.
   *
   * @param messageId the ID for the message
   * @param totalParts the total number of parts for the message
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message could not be assembled
   */
  void assembleMessage(UUID messageId, int totalParts)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Returns <b>true</b> if the message processor is capable of processing the specified message or
   * <b>false</b> otherwise.
   *
   * @param message the message to process
   * @return <b>true</b> if the message processor is capable of processing the specified message or
   *     <b>false</b> otherwise
   */
  boolean canProcessMessage(Message message);

  /**
   * Returns <b>true</b> if the message processor is capable of queueing the specified message part
   * for assembly or <b>false</b> otherwise.
   *
   * @param messagePart the message part to queue for assembly
   * @return <b>true</b> if the message processor is capable of queueing the specified message part
   *     for assembly or <b>false</b> otherwise
   */
  boolean canQueueMessagePartForAssembly(MessagePart messagePart);

  /**
   * Create the new message.
   *
   * @param message the <b>Message</b> instance containing the information for the message
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message could not be created
   */
  void createMessage(Message message) throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Create the new message part.
   *
   * @param messagePart the <b>MessagePart</b> instance containing the information for the message
   *     part
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message part could not be created
   */
  void createMessagePart(MessagePart messagePart)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Decrypt the message.
   *
   * @param message the message to decrypt
   * @return <b>true</b> if the message data was decrypted successfully or <b>false</b> otherwise
   * @throws MessagingException if the message could not be decrypted
   */
  boolean decryptMessage(Message message) throws MessagingException;

  /**
   * Delete the message.
   *
   * @param message the message to delete
   * @throws InvalidArgumentException if an argument is invalid
   * @throws MessageNotFoundException if the message could not be found
   * @throws ServiceUnavailableException if the message could not be deleted
   */
  void deleteMessage(Message message)
      throws InvalidArgumentException, MessageNotFoundException, ServiceUnavailableException;

  /**
   * Delete the message.
   *
   * @param messageId the ID for the message
   * @throws InvalidArgumentException if an argument is invalid
   * @throws MessageNotFoundException if the message could not be found
   * @throws ServiceUnavailableException if the message could not be deleted
   */
  void deleteMessage(UUID messageId)
      throws InvalidArgumentException, MessageNotFoundException, ServiceUnavailableException;

  /**
   * Delete the message part.
   *
   * @param messagePartId the ID for the message part
   * @throws InvalidArgumentException if an argument is invalid
   * @throws MessagePartNotFoundException if the message part could not be found
   * @throws ServiceUnavailableException if the message part could not be deleted
   */
  void deleteMessagePart(UUID messagePartId)
      throws InvalidArgumentException, MessagePartNotFoundException, ServiceUnavailableException;

  /**
   * Delete the message parts for the message.
   *
   * @param messageId the ID for the message
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message parts could not be deleted for the message
   */
  void deleteMessagePartsForMessage(UUID messageId)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Derive the user-device encryption key.
   *
   * @param username the username for the user e.g. test1
   * @param deviceId the ID for the device
   * @return the user-device encryption key
   * @throws MessagingException if the user-device encryption key could not be derived
   */
  byte[] deriveUserDeviceEncryptionKey(String username, UUID deviceId) throws MessagingException;

  /**
   * Encrypt the message.
   *
   * @param message the message to encrypt
   * @return <b>true</b> if the message data was encrypted successfully or <b>false</b> otherwise
   * @throws MessagingException if the message could not be encrypted
   */
  boolean encryptMessage(Message message) throws MessagingException;

  /**
   * Returns the maximum number of times processing will be attempted for a message.
   *
   * @return the maximum number of times processing will be attempted for a message
   */
  int getMaximumProcessingAttempts();

  /**
   * Retrieve the message.
   *
   * @param messageId the ID for the message
   * @return the message
   * @throws InvalidArgumentException if an argument is invalid
   * @throws MessageNotFoundException if the message could not be found
   * @throws ServiceUnavailableException if the message could not be retrieved
   */
  Message getMessage(UUID messageId)
      throws InvalidArgumentException, MessageNotFoundException, ServiceUnavailableException;

  /**
   * Retrieve the message parts queued for assembly for the message.
   *
   * @param messageId the ID for the message
   * @param lockName the name of the lock that should be applied to the message parts queued for
   *     assembly when they are retrieved
   * @return the message parts queued for assembly for the message
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message parts queued for assembly could not be
   *     retrieved
   */
  List<MessagePart> getMessagePartsQueuedForAssembly(UUID messageId, String lockName)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Get the message parts for a user that have been queued for download by a particular remote
   * device.
   *
   * @param username the username for the user
   * @param deviceId the ID for the device
   * @return the message parts that have been queued for download by a particular remote device
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message parts queued for download could not be
   *     retrieved
   */
  List<MessagePart> getMessagePartsQueuedForDownload(String username, UUID deviceId)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Get the messages for a user that have been queued for download by a particular remote device.
   *
   * @param username the username for the user
   * @param deviceId the ID for the device
   * @return the messages for a user that have been queued for download by a particular remote
   *     device
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the messages for a user that have been queued for
   *     download could not be retrieved
   */
  List<Message> getMessagesQueuedForDownload(String username, UUID deviceId)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Retrieve the next message that has been queued for processing.
   *
   * <p>The message will be locked to prevent duplicate processing.
   *
   * @return an Optional containing the next message that has been queued for processing or an empty
   *     Optional if no messages are currently queued for processing
   * @throws ServiceUnavailableException if the next message queued for processing could not be
   *     retrieved
   */
  Optional<Message> getNextMessageQueuedForProcessing() throws ServiceUnavailableException;

  /**
   * Should the specified message be archived?
   *
   * @param message the message
   * @return <b>true</b> if the message should be archived or <b>false</b> otherwise
   */
  boolean isArchivableMessage(Message message);

  /**
   * Can the specified message be processed asynchronously?
   *
   * @param message the message
   * @return <b>true</b> if the message can be processed asynchronously or <b>false</b> otherwise
   */
  boolean isAsynchronousMessage(Message message);

  /**
   * Has the message already been archived?
   *
   * @param messageId the ID for the message
   * @return <b>true</b> if the message has already been archived or <b>false</b> otherwise
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the check for the archived message failed
   */
  boolean isMessageArchived(UUID messageId)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Has the message part already been queued for assembly?
   *
   * @param messagePartId the ID for the message part
   * @return <b>true</b> if the message part has already been queued for assembly or <b> false</b>
   *     otherwise
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the check for the message part queued for assembly
   *     failed
   */
  boolean isMessagePartQueuedForAssembly(UUID messagePartId)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Should the specified message be be processed securely?
   *
   * @param message the message
   * @return <b>true</b> if the message is secure or <b>false</b> otherwise
   */
  boolean isSecureMessage(Message message);

  /**
   * Can the specified message be processed synchronously?
   *
   * @param message the message
   * @return <b>true</b> if the message can be processed synchronously or <b>false</b> otherwise
   */
  boolean isSynchronousMessage(Message message);

  /**
   * Process the message.
   *
   * @param message the message to process
   * @return an Optional containing the response message or an empty Optional if no response message
   *     exists
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message could not be processed
   */
  Optional<Message> processMessage(Message message)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Queue the specified message for download by a remote device.
   *
   * @param message the message to queue
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message could not be queued for download
   */
  void queueMessageForDownload(Message message)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Queue the specified message for processing.
   *
   * @param message the message to queue
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message could not be queued for processing
   */
  void queueMessageForProcessing(Message message)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Queue the specified message for processing and process the message using the Background Message
   * Processor.
   *
   * @param message the message to queue
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message could not be queued for processing and
   *     processed
   */
  void queueMessageForProcessingAndProcessMessage(Message message)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Queue the specified message part for assembly.
   *
   * @param messagePart the message part to queue
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message part could not be queued for assembly
   */
  void queueMessagePartForAssembly(MessagePart messagePart)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Queue the specified message part for assembly and if all the parts of the message have been
   * queued for assembly then assemble the message using the Background Message Part Assembler and
   * process the message using the Background Message Processor.
   *
   * @param messagePart the message part to queue
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message part could not be queued for assembly and
   *     then the message assembled and processed
   */
  void queueMessagePartForAssemblyAndAssembleAndProcessMessage(MessagePart messagePart)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Reset the locks for the messages.
   *
   * @param status the current status of the messages that have been locked
   * @param newStatus the new status for the messages that have been unlocked
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message locks could not be reset
   */
  void resetMessageLocks(MessageStatus status, MessageStatus newStatus)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Reset the locks for the message parts.
   *
   * @param status the current status of the message parts that have been locked
   * @param newStatus the new status for the message parts that have been unlocked
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message part locks could not be reset
   */
  void resetMessagePartLocks(MessagePartStatus status, MessagePartStatus newStatus)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Set the status for a message part.
   *
   * @param messagePartId the ID for the message part
   * @param status the new status
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message part status could not be set
   */
  void setMessagePartStatus(UUID messagePartId, MessagePartStatus status)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Set the status for a message.
   *
   * @param messageId the ID for the message
   * @param status the new status
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message status could not be set
   */
  void setMessageStatus(UUID messageId, MessageStatus status)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Unlock the message.
   *
   * @param message the message to unlock
   * @param status the new status for the unlocked message
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message could not be unlocked
   */
  void unlockMessage(Message message, MessageStatus status)
      throws InvalidArgumentException, ServiceUnavailableException;

  /**
   * Unlock a locked message part.
   *
   * @param messagePartId the ID for the message part
   * @param status the new status for the unlocked message part
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the message part could not be unlocked
   */
  void unlockMessagePart(UUID messagePartId, MessagePartStatus status)
      throws InvalidArgumentException, ServiceUnavailableException;
}
