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

package digital.inception.application;

// ~--- JDK imports ------------------------------------------------------------

import digital.inception.core.exception.RichRuntimeException;

/**
 * The <code>ApplicationException</code> exception is thrown to indicate an application error
 * condition.
 *
 * @author Marcus Portmann
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ApplicationException extends RichRuntimeException {

  private static final long serialVersionUID = 1000000;

  /** Constructs a new <code>ApplicationException</code> with <code>null</code> as its message. */
  public ApplicationException() {
    super();
  }

  /**
   * Constructs a new <code>ApplicationException</code> with the specified message.
   *
   * @param message the message saved for later retrieval by the <code>getMessage()</code> method
   */
  public ApplicationException(String message) {
    super(message);
  }

  /**
   * Constructs a new <code>ApplicationException</code> with the specified cause and a message of
   * <code>(cause==null ? null : cause.toString())</code> (which typically contains the class and
   * message of cause).
   *
   * @param cause The cause saved for later retrieval by the <code>getCause()</code> method. (A
   *     <code>null</code> value is permitted if the cause is nonexistent or unknown)
   */
  public ApplicationException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs a new <code>ApplicationException</code> with the specified code and message.
   *
   * @param code the error code for the error
   * @param message the message saved for later retrieval by the <code>getMessage()</code> method
   */
  public ApplicationException(String code, String message) {
    super(code, message);
  }

  /**
   * Constructs a new <code>ApplicationException</code> with the specified message and cause.
   *
   * @param message the message saved for later retrieval by the <code>getMessage()</code> method
   * @param cause The cause saved for later retrieval by the <code>getCause()</code> method. (A
   *     <code>null</code> value is permitted if the cause is nonexistent or unknown)
   */
  public ApplicationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new <code>ApplicationException</code> with the specified code, message and cause.
   *
   * @param code the error code for the error
   * @param message the message saved for later retrieval by the <code>getMessage()</code> method
   * @param cause The cause saved for later retrieval by the <code>getCause()</code> method. (A
   *     <code>null</code> value is permitted if the cause is nonexistent or unknown)
   */
  public ApplicationException(String code, String message, Throwable cause) {
    super(code, message, cause);
  }
}
