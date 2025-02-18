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

package digital.inception.messaging.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.github.f4b6a3.uuid.UuidCreator;
import digital.inception.codes.Code;
import digital.inception.codes.CodeCategory;
import digital.inception.codes.ICodesService;
import digital.inception.core.util.Base64Util;
import digital.inception.messaging.IMessagingService;
import digital.inception.messaging.Message;
import digital.inception.messaging.MessageTranslator;
import digital.inception.messaging.messages.AnotherTestRequestData;
import digital.inception.messaging.messages.AnotherTestResponseData;
import digital.inception.messaging.messages.AuthenticateRequestData;
import digital.inception.messaging.messages.AuthenticateResponseData;
import digital.inception.messaging.messages.CheckUserExistsRequestData;
import digital.inception.messaging.messages.CheckUserExistsResponseData;
import digital.inception.messaging.messages.CodeCategoryData;
import digital.inception.messaging.messages.CodeData;
import digital.inception.messaging.messages.GetCodeCategoryRequestData;
import digital.inception.messaging.messages.GetCodeCategoryResponseData;
import digital.inception.messaging.messages.SubmitErrorReportRequestData;
import digital.inception.messaging.messages.SubmitErrorReportResponseData;
import digital.inception.messaging.messages.TenantData;
import digital.inception.messaging.messages.TestRequestData;
import digital.inception.messaging.messages.TestResponseData;
import digital.inception.security.SecurityService;
import digital.inception.test.InceptionExtension;
import digital.inception.test.TestConfiguration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * The <b>SystemMessageTest</b> class contains the implementation of the JUnit tests for the
 * "system" messages supported by the messaging infrastructure.
 *
 * @author Marcus Portmann
 */
@SuppressWarnings("unused")
@ExtendWith(SpringExtension.class)
@ExtendWith(InceptionExtension.class)
@ContextConfiguration(
    classes = {TestConfiguration.class},
    initializers = {ConfigDataApplicationContextInitializer.class})
@TestExecutionListeners(
    listeners = {
      DependencyInjectionTestExecutionListener.class,
      DirtiesContextTestExecutionListener.class,
      TransactionalTestExecutionListener.class
    })
@BootstrapWith(SpringBootTestContextBootstrapper.class)
public class SystemMessageTest {

  private static final UUID DEVICE_ID = UuidCreator.getShortPrefixComb();

  private static final String PASSWORD = "Password1";

  private static final String USERNAME = "Administrator";

  /** The Codes Service. */
  @Autowired private ICodesService codesService;

  /** The Messaging Service. */
  @Autowired private IMessagingService messagingService;

  /** Test the "Another Test" request and response message functionality. */
  @Test
  public void anotherTestMessageTest() throws Exception {
    String testValue = "This is the test value";
    byte[] testData = "This is test data".getBytes();

    AnotherTestRequestData requestData = new AnotherTestRequestData(testValue, testData);

    assertEquals(testValue, requestData.getTestValue());
    assertArrayEquals(testData, requestData.getTestData());

    MessageTranslator messageTranslator = new MessageTranslator(USERNAME, DEVICE_ID);

    Message requestMessage =
        messageTranslator.toMessage(requestData, UuidCreator.getShortPrefixComb());

    Optional<Message> responseMessageOptional = messagingService.processMessage(requestMessage);

    if (responseMessageOptional.isEmpty()) {
      fail("Failed to retrieve the response message");
    }

    Message responseMessage = responseMessageOptional.get();

    AnotherTestResponseData responseData =
        messageTranslator.fromMessage(responseMessage, new AnotherTestResponseData());

    assertEquals(testValue, responseData.getTestValue());
    assertArrayEquals(testData, responseData.getTestData());
  }

  /** Test the "Authentication" message. */
  @Test
  public void authenticationTest() throws Exception {
    AuthenticateRequestData requestData =
        new AuthenticateRequestData(USERNAME, PASSWORD, DEVICE_ID);

    MessageTranslator messageTranslator = new MessageTranslator(USERNAME, DEVICE_ID);

    Message requestMessage =
        messageTranslator.toMessage(requestData, UuidCreator.getShortPrefixComb());

    Optional<Message> responseMessageOptional = messagingService.processMessage(requestMessage);

    if (responseMessageOptional.isEmpty()) {
      fail("Failed to retrieve the response message");
    }

    Message responseMessage = responseMessageOptional.get();

    AuthenticateResponseData responseData =
        messageTranslator.fromMessage(responseMessage, new AuthenticateResponseData());

    assertEquals(0, responseData.getErrorCode());
    assertNotNull(responseData.getErrorMessage());

    List<TenantData> tenants = responseData.getTenants();

    assertEquals(1, tenants.size());

    TenantData tenant = tenants.get(0);

    assertEquals(SecurityService.DEFAULT_TENANT_ID, tenant.getId());
    assertEquals("Default", tenant.getName());
    assertNotNull(responseData.getUserEncryptionKey());
    assertNotNull(responseData.getUserProperties());

    Map<String, Object> userProperties = responseData.getUserProperties();

    assertEquals(0, userProperties.size());

    assertEquals(requestMessage.getCorrelationId(), responseMessage.getCorrelationId());
  }

  /** Test the "Check User Exists" message. */
  @Test
  public void checkUserExistsTest() throws Exception {
    CheckUserExistsRequestData requestData = new CheckUserExistsRequestData(USERNAME);

    MessageTranslator messageTranslator = new MessageTranslator(USERNAME, DEVICE_ID);

    Message requestMessage =
        messageTranslator.toMessage(requestData, UuidCreator.getShortPrefixComb());

    Optional<Message> responseMessageOptional = messagingService.processMessage(requestMessage);

    if (responseMessageOptional.isEmpty()) {
      fail("Failed to retrieve the response message");
    }

    Message responseMessage = responseMessageOptional.get();

    CheckUserExistsResponseData responseData =
        messageTranslator.fromMessage(responseMessage, new CheckUserExistsResponseData());

    assertEquals(0, responseData.getErrorCode());
    assertNotNull(responseData.getErrorMessage());
    assertTrue(responseData.getUserExists());
  }

  /** Test the "Get Code Category" message. */
  @Test
  public void getGetCodeCategoryTest() throws Exception {
    CodeCategory testStandardCodeCategory =
        new CodeCategory("TestStandardCodeCategory1", "Test Standard Code Category 1", "");

    if (testStandardCodeCategory != null) {
      codesService.createCodeCategory(testStandardCodeCategory);

      List<Code> testCodes = new ArrayList<>();

      for (int i = 100; i <= 110; i++) {
        Code testStandardCode =
            new Code(
                "Test Standard Code ID " + i,
                testStandardCodeCategory.getId(),
                "Test Standard Code Name " + i,
                "Test Standard Code Value " + i);

        codesService.createCode(testStandardCode);

        testCodes.add(testStandardCode);
      }

      GetCodeCategoryRequestData requestData =
          new GetCodeCategoryRequestData(
              testStandardCodeCategory.getId(), LocalDateTime.now(), true);

      MessageTranslator messageTranslator = new MessageTranslator(USERNAME, DEVICE_ID);

      Message requestMessage =
          messageTranslator.toMessage(requestData, UuidCreator.getShortPrefixComb());

      Optional<Message> responseMessageOptional = messagingService.processMessage(requestMessage);

      if (responseMessageOptional.isEmpty()) {
        fail("Failed to retrieve the response message");
      }

      Message responseMessage = responseMessageOptional.get();

      GetCodeCategoryResponseData responseData =
          messageTranslator.fromMessage(responseMessage, new GetCodeCategoryResponseData());

      assertEquals(0, responseData.getErrorCode());
      assertNotNull(responseData.getErrorMessage());

      CodeCategoryData codeCategory = responseData.getCodeCategory();

      assertEquals(testStandardCodeCategory.getId(), codeCategory.getId());
      assertEquals(testStandardCodeCategory.getName(), codeCategory.getName());
      assertEquals(testCodes.size(), codeCategory.getCodes().size());

      boolean foundMatchingCode = false;

      for (Code testCode : testCodes) {
        for (CodeData code : codeCategory.getCodes()) {
          if (testCode.getId().equals(code.getId())) {
            assertEquals(testCode.getCodeCategoryId(), code.getCodeCategoryId());
            assertEquals(testCode.getName(), code.getName());
            assertEquals(testCode.getValue(), code.getValue());

            foundMatchingCode = true;

            break;
          }
        }

        if (!foundMatchingCode) {
          fail(String.format("Failed to find the matching code (%s)", testCode));
        }

        foundMatchingCode = false;
      }
    }

    CodeCategory testCustomCodeCategory =
        new CodeCategory("TestCustomCodeCategory2", "Test Custom Code Category 2", "");

    if (testCustomCodeCategory != null) {
      codesService.createCodeCategory(testCustomCodeCategory);

      GetCodeCategoryRequestData requestData =
          new GetCodeCategoryRequestData(testCustomCodeCategory.getId(), LocalDateTime.now(), true);

      MessageTranslator messageTranslator = new MessageTranslator(USERNAME, DEVICE_ID);

      Message requestMessage =
          messageTranslator.toMessage(requestData, UuidCreator.getShortPrefixComb());

      Optional<Message> responseMessageOptional = messagingService.processMessage(requestMessage);

      if (responseMessageOptional.isEmpty()) {
        fail("Failed to retrieve the response message");
      }

      Message responseMessage = responseMessageOptional.get();

      GetCodeCategoryResponseData responseData =
          messageTranslator.fromMessage(responseMessage, new GetCodeCategoryResponseData());

      assertEquals(0, responseData.getErrorCode());
      assertNotNull(responseData.getErrorMessage());

      CodeCategoryData codeCategory = responseData.getCodeCategory();

      assertEquals(testCustomCodeCategory.getId(), codeCategory.getId());
      assertEquals(testCustomCodeCategory.getName(), codeCategory.getName());
    }
  }

  /** Test the "Get Code Category With Parameters" message. */
  @Test
  public void getGetCodeCategoryWithParametersTest() throws Exception {
    Map<String, String> parameters = new HashMap<>();

    parameters.put("Parameter Name 1", "Parameter Value 1");
    parameters.put("Parameter Name 2", "Parameter Value 2");

    CodeCategory testStandardCodeCategory =
        new CodeCategory("TestStandardCodeCategory2", "Test Standard Code Category 2", "");

    if (testStandardCodeCategory != null) {
      codesService.createCodeCategory(testStandardCodeCategory);

      List<Code> testCodes = new ArrayList<>();

      for (int i = 200; i <= 210; i++) {
        Code testStandardCode =
            new Code(
                "Test Standard Code ID " + i,
                testStandardCodeCategory.getId(),
                "Test Standard Code Name " + i,
                "Test Standard Code Value " + i);

        codesService.createCode(testStandardCode);

        testCodes.add(testStandardCode);
      }

      GetCodeCategoryRequestData requestData =
          new GetCodeCategoryRequestData(
              testStandardCodeCategory.getId(), LocalDateTime.now(), parameters, true);

      MessageTranslator messageTranslator = new MessageTranslator(USERNAME, DEVICE_ID);

      Message requestMessage =
          messageTranslator.toMessage(requestData, UuidCreator.getShortPrefixComb());

      Optional<Message> responseMessageOptional = messagingService.processMessage(requestMessage);

      if (responseMessageOptional.isEmpty()) {
        fail("Failed to retrieve the response message");
      }

      Message responseMessage = responseMessageOptional.get();

      GetCodeCategoryResponseData responseData =
          messageTranslator.fromMessage(responseMessage, new GetCodeCategoryResponseData());

      assertEquals(0, responseData.getErrorCode());
      assertNotNull(responseData.getErrorMessage());

      CodeCategoryData codeCategory = responseData.getCodeCategory();

      assertEquals(testStandardCodeCategory.getId(), codeCategory.getId());
      assertEquals(testStandardCodeCategory.getName(), codeCategory.getName());
      assertEquals(testCodes.size(), codeCategory.getCodes().size());

      boolean foundMatchingCode = false;

      for (Code testCode : testCodes) {
        for (CodeData code : codeCategory.getCodes()) {
          if (testCode.getId().equals(code.getId())) {
            assertEquals(testCode.getCodeCategoryId(), code.getCodeCategoryId());
            assertEquals(testCode.getName(), code.getName());
            assertEquals(testCode.getValue(), code.getValue());

            foundMatchingCode = true;

            break;
          }
        }

        if (!foundMatchingCode) {
          fail(String.format("Failed to find the matching code (%s)", testCode));
        }

        foundMatchingCode = false;
      }
    }

    CodeCategory testCustomCodeCategory =
        new CodeCategory("TestCustomCodeCategory1", "Test Custom Code Category 1", "");

    codesService.createCodeCategory(testCustomCodeCategory);

    GetCodeCategoryRequestData requestData =
        new GetCodeCategoryRequestData(
            testCustomCodeCategory.getId(), LocalDateTime.now(), parameters, true);

    MessageTranslator messageTranslator = new MessageTranslator(USERNAME, DEVICE_ID);

    Message requestMessage =
        messageTranslator.toMessage(requestData, UuidCreator.getShortPrefixComb());

    Optional<Message> responseMessageOptional = messagingService.processMessage(requestMessage);

    if (responseMessageOptional.isEmpty()) {
      fail("Failed to retrieve the response message");
    }

    Message responseMessage = responseMessageOptional.get();

    GetCodeCategoryResponseData responseData =
        messageTranslator.fromMessage(responseMessage, new GetCodeCategoryResponseData());

    assertEquals(0, responseData.getErrorCode());
    assertNotNull(responseData.getErrorMessage());

    CodeCategoryData codeCategory = responseData.getCodeCategory();

    assertEquals(testCustomCodeCategory.getId(), codeCategory.getId());
    assertEquals(testCustomCodeCategory.getName(), codeCategory.getName());
  }

  /** Test the "Submit Error Report" message. */
  @Test
  public void submitErrorReportTest() throws Exception {
    SubmitErrorReportRequestData requestData =
        new SubmitErrorReportRequestData(
            UuidCreator.getShortPrefixComb(),
            "ApplicationId",
            "1.0.0",
            "Test Description",
            "Test Detail",
            "Test Feedback",
            LocalDateTime.now(),
            "Administrator",
            UuidCreator.getShortPrefixComb(),
            Base64Util.encodeBytes("Test Data".getBytes()));

    MessageTranslator messageTranslator = new MessageTranslator(USERNAME, DEVICE_ID);

    Message requestMessage =
        messageTranslator.toMessage(requestData, UuidCreator.getShortPrefixComb());

    Optional<Message> responseMessageOptional = messagingService.processMessage(requestMessage);

    if (responseMessageOptional.isEmpty()) {
      fail("Failed to retrieve the response message");
    }

    Message responseMessage = responseMessageOptional.get();

    SubmitErrorReportResponseData responseData =
        messageTranslator.fromMessage(responseMessage, new SubmitErrorReportResponseData());

    assertEquals(0, responseData.getErrorCode());
    assertNotNull(responseData.getErrorMessage());
    assertEquals(requestData.getId(), responseData.getErrorReportId());
  }

  /** Test the "Test" request and response message functionality. */
  @Test
  public void testMessageTest() throws Exception {
    String testValue = "This is the test value";

    TestRequestData requestData = new TestRequestData(testValue);

    assertEquals(testValue, requestData.getTestValue());

    MessageTranslator messageTranslator = new MessageTranslator(USERNAME, DEVICE_ID);

    Message requestMessage =
        messageTranslator.toMessage(requestData, UuidCreator.getShortPrefixComb());

    Optional<Message> responseMessageOptional = messagingService.processMessage(requestMessage);

    if (responseMessageOptional.isEmpty()) {
      fail("Failed to retrieve the response message");
    }

    Message responseMessage = responseMessageOptional.get();

    TestResponseData responseData =
        messageTranslator.fromMessage(responseMessage, new TestResponseData());

    assertEquals(testValue, responseData.getTestValue());
  }
}
