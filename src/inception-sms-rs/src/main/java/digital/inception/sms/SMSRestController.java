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

package digital.inception.sms;

//~--- non-JDK imports --------------------------------------------------------

import digital.inception.rs.SecureRestController;
import io.swagger.annotations.Api;
import javax.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//~--- JDK imports ------------------------------------------------------------

/**
 * The <code>SMSRestController</code> class.
 *
 * @author Marcus Portmann
 */
@Api(tags = "SMS API")
@RestController
@RequestMapping(value = "/api/sms")
@SuppressWarnings({"unused", "WeakerAccess"})
public class SMSRestController extends SecureRestController {

  /**
   * The SMS Service.
   */
  private ISMSService smsService;

  /**
   * The JSR-303 validator.
   */
  private Validator validator;

  /**
   * Constructs a new <code>SMSRestController</code>.
   *
   * @param smsService the SMS Service
   * @param validator  the JSR-303 validator
   */
  public SMSRestController(ISMSService smsService, Validator validator) {
    this.smsService = smsService;
    this.validator = validator;
  }
}
