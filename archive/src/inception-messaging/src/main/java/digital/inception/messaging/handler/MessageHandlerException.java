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

package digital.inception.messaging.handler;

/**
 * The <b>MessageHandlerException</b> exception is thrown to indicate an error condition when a
 * message handler processes a message.
 *
 * <p>This is a checked exception to prevent the automatic rollback of the current transaction.
 *
 * @author Marcus Portmann
 */
@SuppressWarnings("WeakerAccess")
public class MessageHandlerException extends Exception {

  private static final long serialVersionUID = 1000000;

  /**
   * Constructs a new <b>MessageHandlerException</b> with the specified message.
   *
   * @param message The message saved for later retrieval by the <b>buildMessageFromResultSet ()</b>
   *     method.
   */
  public MessageHandlerException(String message) {
    super(message);
  }

  /**
   * Constructs a new <b>MessageHandlerException</b> with the specified message and cause.
   *
   * @param message The message saved for later retrieval by the <b>buildMessageFromResultSet ()</b>
   *     method.
   * @param cause The cause saved for later retrieval by the <b>getCause()</b> method. (A
   *     <b>null</b> value is permitted if the cause is nonexistent or unknown)
   */
  public MessageHandlerException(String message, Throwable cause) {
    super(message, cause);
  }
}
