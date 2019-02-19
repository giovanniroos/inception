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

package digital.inception.security;

//~--- non-JDK imports --------------------------------------------------------

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A <code>FunctionNotFoundException</code> is thrown to indicate that a security operation failed
 * as a result of an authorised function that could not be found.
 * <p/>
 * NOTE: This is a checked exception to prevent the automatic rollback of the current transaction.
 *
 * @author Marcus Portmann
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The function could not be found")
@SuppressWarnings({ "unused", "WeakerAccess" })
public class FunctionNotFoundException extends Exception
{
  private static final long serialVersionUID = 1000000;

  /**
   * Constructs a new <code>FunctionNotFoundException</code>.
   *
   * @param code the code identifying the authorised function
   */
  public FunctionNotFoundException(String code)
  {
    super(String.format("A function with the code (%s) could not be found", code));
  }
}
