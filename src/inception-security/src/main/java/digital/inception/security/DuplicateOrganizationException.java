/*
 * Copyright 2020 Marcus Portmann
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

// ~--- non-JDK imports --------------------------------------------------------

import digital.inception.core.service.ServiceException;
import java.util.UUID;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// ~--- JDK imports ------------------------------------------------------------

/**
 * A <code>DuplicateOrganizationException</code> is thrown to indicate that a security operation
 * failed as a result of a duplicate organization.
 *
 * <p>NOTE: This is a checked exception to prevent the automatic rollback of the current
 * transaction.
 *
 * @author Marcus Portmann
 */
@ResponseStatus(
    value = HttpStatus.CONFLICT,
    reason = "An organization with the specified ID or name already exists")
@WebFault(
    name = "DuplicateOrganizationException",
    targetNamespace = "http://security.inception.digital",
    faultBean = "digital.inception.core.service.ServiceError")
@XmlAccessorType(XmlAccessType.PROPERTY)
@SuppressWarnings({"unused"})
public class DuplicateOrganizationException extends ServiceException {

  private static final long serialVersionUID = 1000000;

  /**
   * Constructs a new <code>DuplicateOrganizationException</code>.
   *
   * @param name the name of the organization
   */
  public DuplicateOrganizationException(String name) {
    super(
        "DuplicateOrganizationError",
        "An organization with the name (" + name + ") already exists");
  }

  /**
   * Constructs a new <code>DuplicateOrganizationException</code>.
   *
   * @param organizationId the Universally Unique Identifier (UUID) uniquely identifying the
   *                       organization
   */
  public DuplicateOrganizationException(UUID organizationId) {
    super(
        "DuplicateOrganizationError",
        "An organization with the ID (" + organizationId + ") already exists");
  }
}
