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

package digital.inception.mail;

import digital.inception.core.service.Problem;
import digital.inception.core.service.ServiceException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 * The <b>MailTemplateNotFoundException</b> exception is thrown to indicate an error condition as a
 * result of a mail template that could not be found.
 *
 * <p>This is a checked exception to prevent the automatic rollback of the current transaction.
 *
 * @author Marcus Portmann
 */
@Problem(
    type = "http://inception.digital/problems/mail/mail-template-not-found",
    title = "The mail template could not be found.",
    status = 404)
@WebFault(
    name = "MailTemplateNotFoundException",
    targetNamespace = "http://inception.digital/mail",
    faultBean = "digital.inception.core.service.ServiceError")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class MailTemplateNotFoundException extends ServiceException {

  private static final long serialVersionUID = 1000000;

  /**
   * w Constructs a new <b>MailTemplateNotFoundException</b>.
   *
   * @param mailTemplateId the ID for the mail template
   */
  public MailTemplateNotFoundException(String mailTemplateId) {
    super("The mail template with ID (" + mailTemplateId + ") could not be found");
  }
}
