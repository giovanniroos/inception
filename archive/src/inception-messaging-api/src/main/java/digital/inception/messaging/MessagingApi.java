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

import digital.inception.api.SecureApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The <b>MessagingApi</b> class.
 *
 * @author Marcus Portmann
 */
@Tag(name = "Messaging")
@RestController
@RequestMapping(value = "/api/messaging")
@CrossOrigin
@SuppressWarnings({"unused", "WeakerAccess"})
// @el (isSecurityDisabled: digital.inception.api.ApiSecurityExpressionRoot.isSecurityEnabled)
public class MessagingApi extends SecureApi {

  /** The Messaging Service. */
  private final IMessagingService messagingService;

  /**
   * Constructs a new <b>MessagingRestController</b>.
   *
   * @param applicationContext the Spring application context
   * @param messagingService the Messaging Service
   */
  public MessagingApi(ApplicationContext applicationContext, IMessagingService messagingService) {
    super(applicationContext);

    this.messagingService = messagingService;
  }
}
