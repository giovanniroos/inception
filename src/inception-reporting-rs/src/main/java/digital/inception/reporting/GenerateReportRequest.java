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

package digital.inception.reporting;

//~--- non-JDK imports --------------------------------------------------------

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

//~--- JDK imports ------------------------------------------------------------

/**
 * The <code>ReportDefinition</code> class holds the information for a report definition.
 *
 * @author Marcus Portmann
 */
@ApiModel(value = "GenerateReportRequest")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "reportDefinitionId", "reportParameters" })
@SuppressWarnings({ "unused", "WeakerAccess" })
public class GenerateReportRequest
  implements Serializable
{
  private static final long serialVersionUID = 1000000;

  /**
   * The Universally Unique Identifier (UUID) used to uniquely identify the report definition.
   */
  @ApiModelProperty(
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the report definition",
      required = true)
  @JsonProperty(required = true)
  private UUID reportDefinitionId;

  /**
   * The report parameters.
   */
  @ApiModelProperty(value = "The report parameters", required = true)
  @JsonProperty(required = true)
  private List<ReportParameter> reportParameters;

  /**
   * Constructs a new <code>GenerateReportRequest</code>.
   */
  public GenerateReportRequest() {}

  /**
   * Constructs a new <code>GenerateReportRequest</code>.
   *
   * @param reportDefinitionId the Universally Unique Identifier (UUID) used to uniquely identify
   *                           the report definition
   * @param reportParameters   the report parameters
   */
  public GenerateReportRequest(UUID reportDefinitionId, List<ReportParameter> reportParameters)
  {
    this.reportDefinitionId = reportDefinitionId;
    this.reportParameters = reportParameters;
  }

  /**
   * Returns the Universally Unique Identifier (UUID) used to uniquely identify the report
   * definition.
   *
   * @return the Universally Unique Identifier (UUID) used to uniquely identify the report
   *         definition
   */
  public UUID getReportDefinitionId()
  {
    return reportDefinitionId;
  }

  /**
   * Returns the report parameters.
   *
   * @return the report parameters
   */
  public List<ReportParameter> getReportParameters()
  {
    return reportParameters;
  }

  /**
   * Set the Universally Unique Identifier (UUID) used to uniquely identify the report definition.
   *
   * @param reportDefinitionId the Universally Unique Identifier (UUID) used to uniquely identify
   *                           the report definition
   */
  public void setReportDefinitionId(UUID reportDefinitionId)
  {
    this.reportDefinitionId = reportDefinitionId;
  }

  /**
   * Set the report parameters.
   *
   * @param reportParameters the report parameters
   */
  public void setReportParameters(List<ReportParameter> reportParameters)
  {
    this.reportParameters = reportParameters;
  }
}
