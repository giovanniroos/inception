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

package digital.inception.codes;

//~--- non-JDK imports --------------------------------------------------------

import digital.inception.core.util.StringUtil;
import digital.inception.rs.RestControllerError;
import digital.inception.validation.InvalidArgumentException;
import digital.inception.validation.ValidationError;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//~--- JDK imports ------------------------------------------------------------

/**
 * The <code>CodesRestController</code> class.
 *
 * @author Marcus Portmann
 */
@RestController
@RequestMapping(value = "/")
@SuppressWarnings({ "unused", "WeakerAccess" })
public class CodesRestController
{
  /* Codes Service */
  @Autowired
  private ICodesService codesService;

  /* Validator */
  @Autowired
  private Validator validator;

  /**
   * Create a code.
   *
   * @param codeCategoryId the Universally Unique Identifier (UUID) used to uniquely identify the
   *                       code category
   * @param code           the code to create
   */
  @ApiOperation(value = "Create a code", notes = "Create the code")
  @ApiResponses(value = { @ApiResponse(code = 204, message = "The code was created successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 409, message = "A code with the specified ID already exists",
          response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The code category could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/api/codeCategories/{codeCategoryId}/codes", method = RequestMethod.POST,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createCode(@ApiParam(name = "codeCategoryId",
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the code category",
      required = true)
  @PathVariable UUID codeCategoryId, @ApiParam(name = "code", value = "The code", required = true)
  @RequestBody Code code)
    throws InvalidArgumentException, DuplicateCodeException, CodeCategoryNotFoundException,
        CodesServiceException
  {
    if (code == null)
    {
      throw new InvalidArgumentException("code");
    }

    if (!code.getCodeCategoryId().equals(codeCategoryId))
    {
      throw new InvalidArgumentException("codeCategoryId");
    }

    Set<ConstraintViolation<Code>> constraintViolations = validator.validate(code);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("code", ValidationError.toValidationErrors(
          constraintViolations));
    }

    codesService.createCode(code);
  }

  /**
   * Create a code category.
   *
   * @param codeCategory the code category to create
   */
  @ApiOperation(value = "Create a code category", notes = "Create the code category")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The code category was created successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 409, message = "A code category with the specified ID already exists",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/api/codeCategories", method = RequestMethod.POST,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void createCodeCategory(@ApiParam(name = "codeCategory", value = "The code category",
      required = true)
  @RequestBody CodeCategory codeCategory)
    throws InvalidArgumentException, DuplicateCodeCategoryException, CodesServiceException
  {
    if (codeCategory == null)
    {
      throw new InvalidArgumentException("codeCategory");
    }

    Set<ConstraintViolation<CodeCategory>> constraintViolations = validator.validate(codeCategory);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("codeCategory", ValidationError.toValidationErrors(
          constraintViolations));
    }

    codesService.createCodeCategory(codeCategory);
  }

  /**
   * Delete the code.
   *
   * @param codeCategoryId the Universally Unique Identifier (UUID) used to uniquely identify the
   *                       code category
   */
  @ApiOperation(value = "Delete a code", notes = "Delete the code")
  @ApiResponses(value = { @ApiResponse(code = 204, message = "The code was deleted successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The code could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/api/codeCategories/{codeCategoryId}/codes/{codeId}",
      method = RequestMethod.DELETE, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCode(@ApiParam(name = "codeCategoryId",
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the code category",
      required = true)
  @PathVariable UUID codeCategoryId, @ApiParam(name = "codeId",
      value = "The ID used to uniquely identify the code", required = true)
  @PathVariable String codeId)
    throws InvalidArgumentException, CodeNotFoundException, CodesServiceException
  {
    if (codeCategoryId == null)
    {
      throw new InvalidArgumentException("codeCategoryId");
    }

    if (StringUtil.isNullOrEmpty(codeId))
    {
      throw new InvalidArgumentException("codeId");
    }

    codesService.deleteCode(codeCategoryId, codeId);
  }

  /**
   * Delete the code category.
   *
   * @param codeCategoryId the Universally Unique Identifier (UUID) used to uniquely identify the
   *                       code category
   */
  @ApiOperation(value = "Delete a code category", notes = "Delete the code category")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The code category was deleted successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The code category could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/api/codeCategories/{codeCategoryId}", method = RequestMethod.DELETE,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCodeCategory(@ApiParam(name = "codeCategoryId",
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the code category",
      required = true)
  @PathVariable UUID codeCategoryId)
    throws InvalidArgumentException, CodeCategoryNotFoundException, CodesServiceException
  {
    if (codeCategoryId == null)
    {
      throw new InvalidArgumentException("codeCategoryId");
    }

    codesService.deleteCodeCategory(codeCategoryId);
  }

  /**
   * Retrieve the code categories.
   *
   * @return the code categories
   */
  @ApiOperation(value = "Retrieve the code categories", notes = "Retrieve the code categories")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/api/codeCategories", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAuthority('Application.CodeCategoryAdministration')")
  public List<CodeCategory> getCodeCategories()
    throws CodesServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return codesService.getCodeCategories();
  }

  /**
   * Retrieve the codes for a code category
   *
   * @param codeCategoryId the Universally Unique Identifier (UUID) used to uniquely identify the
   *                       code category
   *
   * @return the codes for the code category
   */
  @ApiOperation(value = "Retrieve the codes for a code category",
      notes = "Retrieve the codes for a code category")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The code category could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/api/codeCategories/{codeCategoryId}/codes", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public List<Code> getCodeCategoryCodes(@ApiParam(name = "codeCategoryId",
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the code category",
      required = true)
  @PathVariable UUID codeCategoryId)
    throws InvalidArgumentException, CodeCategoryNotFoundException, CodesServiceException
  {
    if (codeCategoryId == null)
    {
      throw new InvalidArgumentException("codeCategoryId");
    }

    return codesService.getCodeCategoryCodes(codeCategoryId);
  }

  /**
   * Retrieve the XML or JSON data for a code category
   *
   * @param codeCategoryId the Universally Unique Identifier (UUID) used to uniquely identify the
   *                       code category
   *
   * @return the XML or JSON data for the code category
   */
  @ApiOperation(value = "Retrieve the XML or JSON data for a code category",
      notes = "Retrieve the XML or JSON data for a code category")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The code category could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/api/codeCategories/{codeCategoryId}/data", method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public String getCodeCategoryData(@ApiParam(name = "codeCategoryId",
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the code category",
      required = true)
  @PathVariable UUID codeCategoryId)
    throws InvalidArgumentException, CodeCategoryNotFoundException, CodesServiceException
  {
    if (codeCategoryId == null)
    {
      throw new InvalidArgumentException("codeCategoryId");
    }

    return StringUtil.notNull(codesService.getCodeCategoryData(codeCategoryId));
  }

  /**
   * Returns the date and time the code category was last updated.
   *
   * @param codeCategoryId the Universally Unique Identifier (UUID) used to uniquely identify the
   *                       code category
   *
   * @return the date and time the code category was last updated
   */
  @ApiOperation(value = "Retrieve the date and time the code category was last updated",
      notes = "Retrieve the date and time the code category was last updated")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The code category could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/api/codeCategories/{codeCategoryId}/updated",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public LocalDateTime getCodeCategoryUpdated(@ApiParam(name = "codeCategoryId",
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the code category",
      required = true)
  @PathVariable UUID codeCategoryId)
    throws InvalidArgumentException, CodeCategoryNotFoundException, CodesServiceException
  {
    if (codeCategoryId == null)
    {
      throw new InvalidArgumentException("codeCategoryId");
    }

    return codesService.getCodeCategoryUpdated(codeCategoryId);
  }

  /**
   * Update the code.
   *
   * @param codeCategoryId the Universally Unique Identifier (UUID) used to uniquely identify the
   *                       code category
   * @param codeId         the ID used to uniquely identify the code
   * @param code           the code to create
   */
  @ApiOperation(value = "Update a code", notes = "Update the code")
  @ApiResponses(value = { @ApiResponse(code = 204, message = "The code was updated successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 409, message = "A code with the specified ID already exists",
          response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The code could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/api/codeCategories/{codeCategoryId}/codes/{codeId}",
      method = RequestMethod.PUT, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateCode(@ApiParam(name = "codeCategoryId",
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the code category",
      required = true)
  @PathVariable UUID codeCategoryId, @ApiParam(name = "codeId",
      value = "The ID used to uniquely identify the code", required = true)
  @PathVariable String codeId, @ApiParam(name = "code", value = "The code", required = true)
  @RequestBody Code code)
    throws InvalidArgumentException, CodeNotFoundException, CodesServiceException
  {
    if (code == null)
    {
      throw new InvalidArgumentException("code");
    }

    if (!code.getCodeCategoryId().equals(codeCategoryId))
    {
      throw new InvalidArgumentException("codeCategoryId");
    }

    if (!code.getId().equals(codeId))
    {
      throw new InvalidArgumentException("codeId");
    }

    Set<ConstraintViolation<Code>> constraintViolations = validator.validate(code);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("code", ValidationError.toValidationErrors(
          constraintViolations));
    }

    codesService.updateCode(code);
  }

  /**
   * Update the code category.
   *
   * @param codeCategoryId the Universally Unique Identifier (UUID) used to uniquely identify the
   *                       code category
   * @param codeCategory   the code category
   */
  @ApiOperation(value = "Update a code category", notes = "Update the code category")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The code category was updated successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The code category could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/api/codeCategories/{codeCategoryId}", method = RequestMethod.PUT,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateCodeCategory(@ApiParam(name = "codeCategoryId",
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the code category",
      required = true)
  @PathVariable UUID codeCategoryId, @ApiParam(name = "codeCategory", value = "The code category",
      required = true)
  @RequestBody CodeCategory codeCategory)
    throws InvalidArgumentException, CodeCategoryNotFoundException, CodesServiceException
  {
    if (codeCategory == null)
    {
      throw new InvalidArgumentException("codeCategory");
    }

    if (!codeCategory.getId().equals(codeCategoryId))
    {
      throw new InvalidArgumentException("codeCategoryId");
    }

    Set<ConstraintViolation<CodeCategory>> constraintViolations = validator.validate(codeCategory);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("codeCategory", ValidationError.toValidationErrors(
          constraintViolations));
    }

    codesService.updateCodeCategory(codeCategory);
  }
}
