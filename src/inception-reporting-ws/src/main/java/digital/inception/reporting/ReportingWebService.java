/*
 * Copyright 2018 Marcus Portmann
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

package digital.inception.reporting;

//~--- non-JDK imports --------------------------------------------------------

import digital.inception.validation.InvalidArgumentException;
import digital.inception.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.sql.DataSource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.annotation.XmlElement;
import java.sql.Connection;
import java.util.*;

//~--- JDK imports ------------------------------------------------------------

/**
 * The <code>ReportingWebService</code> class.
 *
 * @author Marcus Portmann
 */
@WebService(serviceName = "ReportingService", name = "IReportingService",
    targetNamespace = "http://reporting.inception.digital")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL,
    parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@SuppressWarnings({ "unused", "WeakerAccess" })
public class ReportingWebService
{
  /* Reporting Service */
  @Autowired
  private IReportingService reportingService;

  /* Validator */
  @Autowired
  private Validator validator;

  /**
   * The data source used to provide connections to the application database.
   */
  @Autowired
  @Qualifier("applicationDataSource")
  private DataSource dataSource;

  /**
   * Create a report definition.
   *
   * @param reportDefinition the report definition to create
   */
  @WebMethod(operationName = "CreateReportDefinition")
  public void createReportDefinition(@WebParam(name = "ReportDefinition")
  @XmlElement(required = true) ReportDefinition reportDefinition)
    throws InvalidArgumentException, DuplicateReportDefinitionException, ReportingServiceException
  {
    if (reportDefinition == null)
    {
      throw new InvalidArgumentException("reportDefinition");
    }

    Set<ConstraintViolation<ReportDefinition>> constraintViolations = validator.validate(
        reportDefinition);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("reportDefinition", ValidationError.toValidationErrors(
          constraintViolations));
    }

    reportingService.createReportDefinition(reportDefinition);
  }

  /**
   * Delete the report definition.
   *
   * @param reportDefinitionId the Universally Unique Identifier (UUID) used to uniquely identify
   *                           the report definition
   */
  @WebMethod(operationName = "DeleteReportDefinition")
  public void deleteReportDefinition(@WebParam(name = "ReportDefinitionId")
  @XmlElement(required = true) UUID reportDefinitionId)
    throws InvalidArgumentException, ReportDefinitionNotFoundException, ReportingServiceException
  {
    if (reportDefinitionId == null)
    {
      throw new InvalidArgumentException("reportDefinitionId");
    }

    reportingService.deleteReportDefinition(reportDefinitionId);
  }

  /**
   * Generate the PDF report.
   *
   * @param reportDefinitionId the Universally Unique Identifier (UUID) used to uniquely identify
   *                           the report definition
   * @param reportParameters   the report parameters
   *
   * @return the PDF report
   */
  @WebMethod(operationName = "GenerateReport")
  public byte[] generateReport(@WebParam(name = "ReportDefinitionId")
  @XmlElement(required = true) UUID reportDefinitionId, @WebParam(name = "ReportParameters")
  @XmlElement(required = true) List<ReportParameter> reportParameters)
    throws InvalidArgumentException, ReportDefinitionNotFoundException, ReportingServiceException
  {
    if (reportDefinitionId == null)
    {
      throw new InvalidArgumentException("reportDefinition");
    }

    Map<String, Object> parameters = new HashMap<>();

    for (ReportParameter reportParameter : reportParameters)
    {
      parameters.put(reportParameter.getName(), reportParameter.getValue());
    }

    try (Connection connection = dataSource.getConnection())
    {
      return reportingService.createReportPDF(reportDefinitionId, parameters, connection);
    }
    catch (ReportDefinitionNotFoundException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new ReportingServiceException("Failed to generate the PDF report", e);
    }
  }

  /**
   * Retrieve the report definition summaries.
   *
   * @return the report definition summaries
   */
  @WebMethod(operationName = "GetReportDefinitionSummaries")
  @WebResult(name = "ReportDefinitionSummary")
  public List<ReportDefinitionSummary> getReportDefinitionSummaries()
    throws ReportingServiceException
  {
    return reportingService.getReportDefinitionSummaries();
  }

  /**
   * Retrieve the report definitions.
   *
   * @return the report definitions
   */
  @WebMethod(operationName = "GetReportDefinitions")
  @WebResult(name = "ReportDefinition")
  public List<ReportDefinition> getReportDefinitions()
    throws ReportingServiceException
  {
    return reportingService.getReportDefinitions();
  }

  /**
   * Update the report definition.
   *
   * @param reportDefinition the report definition
   */
  @WebMethod(operationName = "UpdateReportDefinition")
  public void updateReportDefinition(@WebParam(name = "ReportDefinition")
  @XmlElement(required = true) ReportDefinition reportDefinition)
    throws InvalidArgumentException, ReportDefinitionNotFoundException, ReportingServiceException
  {
    if (reportDefinition == null)
    {
      throw new InvalidArgumentException("reportDefinition");
    }

    Set<ConstraintViolation<ReportDefinition>> constraintViolations = validator.validate(
        reportDefinition);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("reportDefinition", ValidationError.toValidationErrors(
          constraintViolations));
    }

    reportingService.updateReportDefinition(reportDefinition);
  }
}
