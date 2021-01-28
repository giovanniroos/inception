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

package demo;

import digital.inception.codes.CodeCategory;
import digital.inception.codes.CodesService;
import digital.inception.codes.ICodesService;
import digital.inception.core.util.CryptoUtil;
import digital.inception.ws.security.WebServiceClientSecurityHelper;
import java.security.KeyStore;
import java.util.List;

/**
 * The <b>SimpleDemoClient</b> class.
 *
 * @author Marcus Portmann
 */
public class SimpleDemoClient {

  /** The Codes Service endpoint. */
  public static final String CODES_SERVICE_ENDPOINT = "http://localhost:20000/service/CodesService";

  /** The path to the classpath resource containing the WSDL for the Codes Service. */
  public static final String CODES_SERVICE_WSDL = "META-INF/wsdl/CodesService.wsdl";

  /** Enable the web services security X509 certificate token profile for the demo client. */
  private static final boolean DEMO_CLIENT_ENABLE_X509_CERTIFICATE_TOKEN_PROFILE = false;

  /** The keystore alias for the demo client. */
  private static final String DEMO_CLIENT_KEYSTORE_ALIAS = "demo-client";

  /** The keystore password for the demo client. */
  private static final String DEMO_CLIENT_KEYSTORE_PASSWORD = "Password1";

  /** The keystore path for the demo client. */
  private static final String DEMO_CLIENT_KEYSTORE_PATH = "META-INF/demo-client.p12";

  /** The keystore type for the demo client. */
  private static final String DEMO_CLIENT_KEYSTORE_TYPE = "pkcs12";

  /**
   * The main method.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    try {
      KeyStore keyStore =
          CryptoUtil.loadKeyStore(
              DEMO_CLIENT_KEYSTORE_TYPE,
              DEMO_CLIENT_KEYSTORE_PATH,
              DEMO_CLIENT_KEYSTORE_PASSWORD,
              DEMO_CLIENT_KEYSTORE_ALIAS);

      ICodesService codesService;

      if (DEMO_CLIENT_ENABLE_X509_CERTIFICATE_TOKEN_PROFILE) {
        codesService =
            WebServiceClientSecurityHelper.getWSSecurityX509CertificateServiceProxy(
                CodesService.class,
                ICodesService.class,
                CODES_SERVICE_WSDL,
                CODES_SERVICE_ENDPOINT,
                keyStore,
                DEMO_CLIENT_KEYSTORE_PASSWORD,
                DEMO_CLIENT_KEYSTORE_ALIAS,
                keyStore);
      } else {
        codesService =
            WebServiceClientSecurityHelper.getServiceProxy(
                CodesService.class,
                ICodesService.class,
                CODES_SERVICE_WSDL,
                CODES_SERVICE_ENDPOINT);
      }

      List<CodeCategory> codeCategories = codesService.getCodeCategories();

      for (CodeCategory codeCategory : codeCategories) {
        System.out.println(codeCategory.getName());
      }
    } catch (Throwable e) {
      System.err.println("[ERROR] " + e.getMessage());
      e.printStackTrace(System.err);
    }
  }
}
