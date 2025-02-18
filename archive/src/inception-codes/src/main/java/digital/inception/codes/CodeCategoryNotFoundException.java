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

package digital.inception.codes;

import digital.inception.core.service.Problem;
import digital.inception.core.service.ServiceException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 * The <b>CodeCategoryNotFoundException</b> exception is thrown to indicate an error condition as a
 * result of a code category that could not be found.
 *
 * <p>This is a checked exception to prevent the automatic rollback of the current transaction.
 *
 * @author Marcus Portmann
 */
@Problem(
    type = "http://inception.digital/problems/codes/code-category-not-found",
    title = "The code category could not be found.",
    status = 404)
@WebFault(
    name = "CodeCategoryNotFoundException",
    targetNamespace = "http://inception.digital/codes",
    faultBean = "digital.inception.core.service.ServiceError")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CodeCategoryNotFoundException extends ServiceException {

  private static final long serialVersionUID = 1000000;

  /**
   * Constructs a new <b>CodeCategoryNotFoundException</b>.
   *
   * @param codeCategoryId the ID for the code category
   */
  public CodeCategoryNotFoundException(String codeCategoryId) {
    super("The code category with ID (" + codeCategoryId + ") could not be found");
  }
}
