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

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * The <b>MessagingWebService</b> class.
 *
 * @author Marcus Portmann
 */
@WebService(
    serviceName = "MessagingService",
    name = "IMessagingService",
    targetNamespace = "http://inception.digital/messaging")
@SOAPBinding
@SuppressWarnings({"unused", "WeakerAccess", "ValidExternallyBoundObject"})
public class MessagingWebService {

  /** The Messaging Service. */
  private final IMessagingService messagingService;

  /**
   * Constructs a new <b>MessagingWebService</b>.
   *
   * @param messagingService the Messaging Service
   */
  public MessagingWebService(IMessagingService messagingService) {
    this.messagingService = messagingService;
  }
}
