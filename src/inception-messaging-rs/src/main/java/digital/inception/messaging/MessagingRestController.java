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

package digital.inception.messaging;

//~--- non-JDK imports --------------------------------------------------------

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;

//~--- JDK imports ------------------------------------------------------------

/**
 * The <code>MessagingRestController</code> class.
 *
 * @author Marcus Portmann
 */
@RestController
@RequestMapping(value = "/api/messaging")
@SuppressWarnings({ "unused", "WeakerAccess" })
public class MessagingRestController
{
  /* Messaging Service */
  @Autowired
  private IMessagingService messagingService;

  /* Validator */
  @Autowired
  private Validator validator;
}
