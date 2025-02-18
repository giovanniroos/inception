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

package digital.inception.party;

import digital.inception.api.ProblemDetails;
import digital.inception.api.SecureApi;
import digital.inception.core.service.InvalidArgumentException;
import digital.inception.core.service.ServiceUnavailableException;
import digital.inception.core.sorting.SortDirection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The <b>PartyApi</b> class.
 *
 * @author Marcus Portmann
 */
@Tag(name = "Party")
@RestController
@RequestMapping(value = "/api/party")
@CrossOrigin
@SuppressWarnings({"unused", "WeakerAccess"})
// @el (isSecurityDisabled: digital.inception.api.ApiSecurityExpressionRoot.isSecurityEnabled)
public class PartyApi extends SecureApi {

  /** The Party Service. */
  private final IPartyService partyService;

  /**
   * Constructs a new <b>PartyRestController</b>.
   *
   * @param applicationContext the Spring application context
   * @param partyService the Party Service
   */
  public PartyApi(ApplicationContext applicationContext, IPartyService partyService) {
    super(applicationContext);

    this.partyService = partyService;
  }

  /**
   * Create the new association.
   *
   * @param tenantId the ID for the tenant
   * @param association the association
   * @throws InvalidArgumentException if an argument is invalid
   * @throws DuplicateAssociationException if the association already exists
   * @throws PartyNotFoundException if one or more parties for the association could not be found
   * @throws ServiceUnavailableException if the association could not be created
   */
  @Operation(summary = "Create the association", description = "Create the association")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The association was created successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "One or more parties for the association could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "409",
            description = "An association with the specified ID already exists",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/associations",
      method = RequestMethod.POST,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.AssociationAdministration')")
  public void createAssociation(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "The association to create",
              required = true)
          @RequestBody
          Association association)
      throws InvalidArgumentException, DuplicateAssociationException, PartyNotFoundException,
          ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(association.getTenantId())) {
      throw new AccessDeniedException(
          "Access denied to the tenant (" + association.getTenantId() + ")");
    }

    partyService.createAssociation(tenantId, association);
  }

  /**
   * Create the new mandate.
   *
   * @param tenantId the ID for the tenant
   * @param mandate the mandate
   * @throws InvalidArgumentException if an argument is invalid
   * @throws DuplicateMandateException if the mandate already exists
   * @throws PartyNotFoundException if one or more parties for the mandate could not be found
   * @throws ServiceUnavailableException if the mandate could not be created
   */
  @Operation(summary = "Create the mandate", description = "Create the mandate")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "The mandate was created successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "One or more parties for the mandate could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "409",
            description = "A mandate with the specified ID already exists",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(value = "/mandates", method = RequestMethod.POST, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.MandateAdministration')")
  public void createMandate(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "The mandate to create",
              required = true)
          @RequestBody
          Mandate mandate)
      throws InvalidArgumentException, DuplicateMandateException, PartyNotFoundException,
          ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(mandate.getTenantId())) {
      throw new AccessDeniedException(
          "Access denied to the tenant (" + mandate.getTenantId() + ")");
    }

    partyService.createMandate(tenantId, mandate);
  }

  /**
   * Create the new organization.
   *
   * @param tenantId the ID for the tenant
   * @param organization the organization
   * @throws InvalidArgumentException if an argument is invalid
   * @throws DuplicateOrganizationException if the organization already exists
   * @throws ServiceUnavailableException if the organization could not be created
   */
  @Operation(summary = "Create the organization", description = "Create the organization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The organization was created successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "409",
            description = "An organization with the specified ID already exists",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/organizations",
      method = RequestMethod.POST,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.OrganizationAdministration')")
  public void createOrganization(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "The organization to create",
              required = true)
          @RequestBody
          Organization organization)
      throws InvalidArgumentException, DuplicateOrganizationException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(organization.getTenantId())) {
      throw new AccessDeniedException(
          "Access denied to the tenant (" + organization.getTenantId() + ")");
    }

    partyService.createOrganization(tenantId, organization);
  }

  /**
   * Create the new person.
   *
   * @param tenantId the ID for the tenant
   * @param person the person
   * @throws InvalidArgumentException if an argument is invalid
   * @throws DuplicatePersonException if the person already exists
   * @throws ServiceUnavailableException if the person could not be created
   */
  @Operation(summary = "Create the person", description = "Create the person")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "The person was created successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "409",
            description = "A person with the specified ID already exists",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(value = "/persons", method = RequestMethod.POST, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.PersonAdministration')")
  public void createPerson(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "The person to create",
              required = true)
          @RequestBody
          Person person)
      throws InvalidArgumentException, DuplicatePersonException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(person.getTenantId())) {
      throw new AccessDeniedException("Access denied to the tenant (" + person.getTenantId() + ")");
    }

    partyService.createPerson(tenantId, person);
  }

  /**
   * Delete the association.
   *
   * @param tenantId the ID for the tenant
   * @param associationId the ID for the association
   * @throws InvalidArgumentException if an argument is invalid
   * @throws AssociationNotFoundException if the association could not be found
   * @throws ServiceUnavailableException if the association could not be deleted
   */
  @Operation(summary = "Delete the association", description = "Delete the association")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The association was deleted successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The association could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/associations/{associationId}",
      method = RequestMethod.DELETE,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.AssociationAdministration')")
  public void deleteAssociation(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(
              name = "associationId",
              description = "The ID for the association",
              required = true)
          @PathVariable
          UUID associationId)
      throws InvalidArgumentException, AssociationNotFoundException, ServiceUnavailableException {
    partyService.deleteAssociation(tenantId, associationId);
  }

  /**
   * Delete the mandate.
   *
   * @param tenantId the ID for the tenant
   * @param mandateId the ID for the mandate
   * @throws InvalidArgumentException if an argument is invalid
   * @throws MandateNotFoundException if the mandate could not be found
   * @throws ServiceUnavailableException if the mandate could not be deleted
   */
  @Operation(summary = "Delete the mandate", description = "Delete the mandate")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "The mandate was deleted successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The mandate could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/mandates/{mandateId}",
      method = RequestMethod.DELETE,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.MandateAdministration')")
  public void deleteMandate(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "mandateId", description = "The ID for the mandate", required = true)
          @PathVariable
          UUID mandateId)
      throws InvalidArgumentException, MandateNotFoundException, ServiceUnavailableException {
    partyService.deleteMandate(tenantId, mandateId);
  }

  /**
   * Delete the organization.
   *
   * @param tenantId the ID for the tenant
   * @param organizationId the ID for the organization
   * @throws InvalidArgumentException if an argument is invalid
   * @throws OrganizationNotFoundException if the organization could not be found
   * @throws ServiceUnavailableException if the organization could not be deleted
   */
  @Operation(summary = "Delete the organization", description = "Delete the organization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The organization was deleted successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The organization could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/organizations/{organizationId}",
      method = RequestMethod.DELETE,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.OrganizationAdministration')")
  public void deleteOrganization(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(
              name = "organizationId",
              description = "The ID for the organization",
              required = true)
          @PathVariable
          UUID organizationId)
      throws InvalidArgumentException, OrganizationNotFoundException, ServiceUnavailableException {
    partyService.deleteOrganization(tenantId, organizationId);
  }

  /**
   * Delete the person.
   *
   * @param tenantId the ID for the tenant
   * @param personId the ID for the person
   * @throws InvalidArgumentException if an argument is invalid
   * @throws PersonNotFoundException if the person could not be found
   * @throws ServiceUnavailableException if the person could not be deleted
   */
  @Operation(summary = "Delete the person", description = "Delete the person")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "The person was deleted successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The person could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/persons/{personId}",
      method = RequestMethod.DELETE,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.PersonAdministration')")
  public void deletePerson(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "personId", description = "The ID for the person", required = true)
          @PathVariable
          UUID personId)
      throws InvalidArgumentException, PersonNotFoundException, ServiceUnavailableException {
    partyService.deletePerson(tenantId, personId);
  }

  /**
   * Retrieve the association.
   *
   * @param tenantId the ID for the tenant
   * @param associationId the ID for the association
   * @return the association
   * @throws InvalidArgumentException if an argument is invalid
   * @throws AssociationNotFoundException if the association could not be found
   * @throws ServiceUnavailableException if the association could not be retrieved
   */
  @Operation(summary = "Retrieve the association", description = "Retrieve the association")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The association could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/associations/{associationId}",
      method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.AssociationAdministration')")
  public Association getAssociation(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(
              name = "associationId",
              description = "The ID for the association",
              required = true)
          @PathVariable
          UUID associationId)
      throws InvalidArgumentException, AssociationNotFoundException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    return partyService.getAssociation(tenantId, associationId);
  }

  /**
   * Retrieve the associations for the party.
   *
   * @param tenantId the ID for the tenant
   * @param partyId the ID for the party
   * @param sortBy the optional method used to sort the associations e.g. by type
   * @param sortDirection the optional sort direction to apply to the associations
   * @param pageIndex the optional page index
   * @param pageSize the optional page size
   * @return the associations for the party
   * @throws InvalidArgumentException if an argument is invalid
   * @throws PartyNotFoundException if the party could not be found
   * @throws ServiceUnavailableException if the associations could not be retrieved
   */
  @Operation(
      summary = "Retrieve the associations for the party",
      description = "Retrieve the associations for the party")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The party could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/parties/{partyId}/associations",
      method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.OrganizationAdministration') or hasAuthority('FUNCTION_Party.PersonAdministration') or hasAuthority('FUNCTION_Party.AssociationAdministration')")
  public AssociationsForParty getAssociationsForParty(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "partyId", description = "The ID for the party", required = true)
          @PathVariable
          UUID partyId,
      @Parameter(
              name = "sortBy",
              description = "The optional method used to sort the associations e.g. by type")
          @RequestParam(value = "sortBy", required = false)
          AssociationSortBy sortBy,
      @Parameter(
              name = "sortDirection",
              description = "The optional sort direction to apply to the associations")
          @RequestParam(value = "sortDirection", required = false)
          SortDirection sortDirection,
      @Parameter(name = "pageIndex", description = "The optional page index", example = "0")
          @RequestParam(value = "pageIndex", required = false, defaultValue = "0")
          Integer pageIndex,
      @Parameter(name = "pageSize", description = "The optional page size", example = "10")
          @RequestParam(value = "pageSize", required = false, defaultValue = "10")
          Integer pageSize)
      throws InvalidArgumentException, PartyNotFoundException, ServiceUnavailableException {

    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    if (partyId == null) {
      throw new InvalidArgumentException("partyId");
    }

    return partyService.getAssociationsForParty(
        tenantId, partyId, sortBy, sortDirection, pageIndex, pageSize);
  }

  /**
   * Retrieve the mandate.
   *
   * @param tenantId the ID for the tenant
   * @param mandateId the ID for the mandate
   * @return the mandate
   * @throws InvalidArgumentException if an argument is invalid
   * @throws MandateNotFoundException if the mandate could not be found
   * @throws ServiceUnavailableException if the mandate could not be retrieved
   */
  @Operation(summary = "Retrieve the mandate", description = "Retrieve the mandate")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The mandate could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/mandates/{mandateId}",
      method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.MandateAdministration')")
  public Mandate getMandate(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "mandateId", description = "The ID for the mandate", required = true)
          @PathVariable
          UUID mandateId)
      throws InvalidArgumentException, MandateNotFoundException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    return partyService.getMandate(tenantId, mandateId);
  }

  /**
   * Retrieve the mandates for the party.
   *
   * @param tenantId the ID for the tenant
   * @param partyId the ID for the party
   * @param sortBy the optional method used to sort the mandates e.g. by type
   * @param sortDirection the optional sort direction to apply to the mandates
   * @param pageIndex the optional page index
   * @param pageSize the optional page size
   * @return the mandates for the party
   * @throws InvalidArgumentException if an argument is invalid
   * @throws PartyNotFoundException if the party could not be found
   * @throws ServiceUnavailableException if the mandates could not be retrieved
   */
  @Operation(
      summary = "Retrieve the mandates for the party",
      description = "Retrieve the mandates for the party")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The party could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/parties/{partyId}/mandates",
      method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.OrganizationAdministration') or hasAuthority('FUNCTION_Party.PersonAdministration') or hasAuthority('FUNCTION_Party.MandateAdministration')")
  public MandatesForParty getMandatesForParty(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "partyId", description = "The ID for the party", required = true)
          @PathVariable
          UUID partyId,
      @Parameter(
              name = "sortBy",
              description = "The optional method used to sort the mandates e.g. by type")
          @RequestParam(value = "sortBy", required = false)
          MandateSortBy sortBy,
      @Parameter(
              name = "sortDirection",
              description = "The optional sort direction to apply to the mandates")
          @RequestParam(value = "sortDirection", required = false)
          SortDirection sortDirection,
      @Parameter(name = "pageIndex", description = "The optional page index", example = "0")
          @RequestParam(value = "pageIndex", required = false, defaultValue = "0")
          Integer pageIndex,
      @Parameter(name = "pageSize", description = "The optional page size", example = "10")
          @RequestParam(value = "pageSize", required = false, defaultValue = "10")
          Integer pageSize)
      throws InvalidArgumentException, PartyNotFoundException, ServiceUnavailableException {

    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    if (partyId == null) {
      throw new InvalidArgumentException("partyId");
    }

    return partyService.getMandatesForParty(
        tenantId, partyId, sortBy, sortDirection, pageIndex, pageSize);
  }

  /**
   * Retrieve the organization.
   *
   * @param tenantId the ID for the tenant
   * @param organizationId the ID for the organization
   * @return the organization
   * @throws InvalidArgumentException if an argument is invalid
   * @throws OrganizationNotFoundException if the organization could not be found
   * @throws ServiceUnavailableException if the organization could not be retrieved
   */
  @Operation(summary = "Retrieve the organization", description = "Retrieve the organization")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The organization could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/organizations/{organizationId}",
      method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.OrganizationAdministration')")
  public Organization getOrganization(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(
              name = "organizationId",
              description = "The ID for the organization",
              required = true)
          @PathVariable
          UUID organizationId)
      throws InvalidArgumentException, OrganizationNotFoundException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    return partyService.getOrganization(tenantId, organizationId);
  }

  /**
   * Retrieve the organizations.
   *
   * @param tenantId the ID for the tenant
   * @param filter the optional filter to apply to the organizations
   * @param sortBy the optional method used to sort the organizations e.g. by name
   * @param sortDirection the optional sort direction to apply to the organizations
   * @param pageIndex the optional page index
   * @param pageSize the optional page size
   * @return the organizations
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the organizations could not be retrieved
   */
  @Operation(summary = "Retrieve the organizations", description = "Retrieve the organizations")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/organizations",
      method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.OrganizationAdministration')")
  public Organizations getOrganizations(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "filter", description = "The optional filter to apply to the organizations")
          @RequestParam(value = "filter", required = false)
          String filter,
      @Parameter(
              name = "sortBy",
              description = "The optional method used to sort the organizations e.g. by name")
          @RequestParam(value = "sortBy", required = false)
          OrganizationSortBy sortBy,
      @Parameter(
              name = "sortDirection",
              description = "The optional sort direction to apply to the organizations")
          @RequestParam(value = "sortDirection", required = false)
          SortDirection sortDirection,
      @Parameter(name = "pageIndex", description = "The optional page index", example = "0")
          @RequestParam(value = "pageIndex", required = false, defaultValue = "0")
          Integer pageIndex,
      @Parameter(name = "pageSize", description = "The optional page size", example = "10")
          @RequestParam(value = "pageSize", required = false, defaultValue = "10")
          Integer pageSize)
      throws InvalidArgumentException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    if (pageIndex == null) {
      pageIndex = 0;
    }
    if (pageSize == null) {
      pageSize = 10;
    }

    return partyService.getOrganizations(
        tenantId, filter, sortBy, sortDirection, pageIndex, pageSize);
  }

  /**
   * Retrieve the parties.
   *
   * @param tenantId the ID for the tenant
   * @param filter the optional filter to apply to the parties
   * @param sortBy the optional method used to sort the parties e.g. by name
   * @param sortDirection the optional sort direction to apply to the parties
   * @param pageIndex the optional page index
   * @param pageSize the optional page size
   * @return the parties
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the parties could not be retrieved
   */
  @Operation(summary = "Retrieve the parties", description = "Retrieve the parties")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(value = "/parties", method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration')")
  public Parties getParties(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "filter", description = "The optional filter to apply to the parties")
          @RequestParam(value = "filter", required = false)
          String filter,
      @Parameter(
              name = "sortBy",
              description = "The optional method used to sort the parties e.g. by name")
          @RequestParam(value = "sortBy", required = false)
          PartySortBy sortBy,
      @Parameter(
              name = "sortDirection",
              description = "The optional sort direction to apply to the parties")
          @RequestParam(value = "sortDirection", required = false)
          SortDirection sortDirection,
      @Parameter(name = "pageIndex", description = "The optional page index", example = "0")
          @RequestParam(value = "pageIndex", required = false, defaultValue = "0")
          Integer pageIndex,
      @Parameter(name = "pageSize", description = "The optional page size", example = "10")
          @RequestParam(value = "pageSize", required = false, defaultValue = "10")
          Integer pageSize)
      throws InvalidArgumentException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    if (pageIndex == null) {
      pageIndex = 0;
    }
    if (pageSize == null) {
      pageSize = 10;
    }

    return partyService.getParties(tenantId, filter, sortBy, sortDirection, pageIndex, pageSize);
  }

  /**
   * Retrieve the party.
   *
   * @param tenantId the ID for the tenant
   * @param partyId the ID for the party
   * @return the party
   * @throws InvalidArgumentException if an argument is invalid
   * @throws PartyNotFoundException if the party could not be found
   * @throws ServiceUnavailableException if the party could not be retrieved
   */
  @Operation(summary = "Retrieve the party", description = "Retrieve the party")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The party could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/parties/{partyId}",
      method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration')")
  public Party getParty(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "partyId", description = "The ID for the party", required = true)
          @PathVariable
          UUID partyId)
      throws InvalidArgumentException, PartyNotFoundException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    return partyService.getParty(tenantId, partyId);
  }

  /**
   * Retrieve the person.
   *
   * @param tenantId the ID for the tenant
   * @param personId the ID for the person
   * @return the person
   * @throws InvalidArgumentException if an argument is invalid
   * @throws PersonNotFoundException if the person could not be found
   * @throws ServiceUnavailableException if the person could not be retrieved
   */
  @Operation(summary = "Retrieve the person", description = "Retrieve the person")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The person could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/persons/{personId}",
      method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.PersonAdministration')")
  public Person getPerson(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "personId", description = "The ID for the person", required = true)
          @PathVariable
          UUID personId)
      throws InvalidArgumentException, PersonNotFoundException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    return partyService.getPerson(tenantId, personId);
  }

  /**
   * Retrieve the persons.
   *
   * @param tenantId the ID for the tenant
   * @param filter the optional filter to apply to the persons
   * @param sortBy the optional method used to sort the persons e.g. by name
   * @param sortDirection the optional sort direction to apply to the persons
   * @param pageIndex the optional page index
   * @param pageSize the optional page size
   * @return the persons
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the persons could not be retrieved
   */
  @Operation(summary = "Retrieve the persons", description = "Retrieve the persons")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(value = "/persons", method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.PersonAdministration')")
  public Persons getPersons(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "filter", description = "The optional filter to apply to the persons")
          @RequestParam(value = "filter", required = false)
          String filter,
      @Parameter(
              name = "sortBy",
              description = "The optional method used to sort the persons e.g. by name")
          @RequestParam(value = "sortBy", required = false)
          PersonSortBy sortBy,
      @Parameter(
              name = "sortDirection",
              description = "The optional sort direction to apply to the persons")
          @RequestParam(value = "sortDirection", required = false)
          SortDirection sortDirection,
      @Parameter(name = "pageIndex", description = "The optional page index", example = "0")
          @RequestParam(value = "pageIndex", required = false, defaultValue = "0")
          Integer pageIndex,
      @Parameter(name = "pageSize", description = "The optional page size", example = "10")
          @RequestParam(value = "pageSize", required = false, defaultValue = "10")
          Integer pageSize)
      throws InvalidArgumentException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    if (pageIndex == null) {
      pageIndex = 0;
    }
    if (pageSize == null) {
      pageSize = 10;
    }

    return partyService.getPersons(tenantId, filter, sortBy, sortDirection, pageIndex, pageSize);
  }

  /**
   * Retrieve the snapshots for an entity.
   *
   * @param tenantId the ID for the tenant
   * @param entityType the type of entity
   * @param entityId the ID for the entity
   * @param from the optional date to retrieve the snapshots from
   * @param to the optional date to retrieve the snapshots to
   * @param sortDirection the optional sort direction to apply to the snapshots
   * @param pageIndex the optional page index
   * @param pageSize the optional page size
   * @return the snapshots
   * @throws InvalidArgumentException if an argument is invalid
   * @throws ServiceUnavailableException if the snapshots for the entity could not be retrieved
   */
  @Operation(summary = "Retrieve the snapshots", description = "Retrieve the snapshots")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(value = "/snapshots", method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration')")
  public Snapshots getSnapshots(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "entityType", description = "The type of entity", required = true)
          @RequestParam
          EntityType entityType,
      @Parameter(name = "entityId", description = "The ID for the entity", required = true)
          @RequestParam
          UUID entityId,
      @Parameter(name = "from", description = "The optional date to retrieve the snapshots from")
          @RequestParam(value = "from", required = false)
          LocalDate from,
      @Parameter(name = "to", description = "The optional date to retrieve the snapshots to")
          @RequestParam(value = "to", required = false)
          LocalDate to,
      @Parameter(
              name = "sortDirection",
              description = "The optional sort direction to apply to the snapshots")
          @RequestParam(value = "sortDirection", required = false)
          SortDirection sortDirection,
      @Parameter(name = "pageIndex", description = "The optional page index", example = "0")
          @RequestParam(value = "pageIndex", required = false, defaultValue = "0")
          Integer pageIndex,
      @Parameter(name = "pageSize", description = "The optional page size", example = "10")
          @RequestParam(value = "pageSize", required = false, defaultValue = "10")
          Integer pageSize)
      throws InvalidArgumentException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    if (pageIndex == null) {
      pageIndex = 0;
    }
    if (pageSize == null) {
      pageSize = 10;
    }

    return partyService.getSnapshots(
        tenantId, entityType, entityId, from, to, sortDirection, pageIndex, pageSize);
  }

  /**
   * Update the association.
   *
   * @param tenantId the ID for the tenant
   * @param associationId the ID for the association
   * @param association the association
   * @throws InvalidArgumentException if an argument is invalid
   * @throws AssociationNotFoundException if the association could not be found
   * @throws PartyNotFoundException if one or more parties for the association could not be found
   * @throws ServiceUnavailableException if the association could not be updated
   */
  @Operation(summary = "Update the association", description = "Update the association")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The association was updated successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description =
                "The association could not be found or one or more parties for the association could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/associations/{associationId}",
      method = RequestMethod.PUT,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.AssociationAdministration')")
  public void updateAssociation(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(
              name = "associationId",
              description = "The ID for the association",
              required = true)
          @PathVariable
          UUID associationId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "The association to update",
              required = true)
          @RequestBody
          Association association)
      throws InvalidArgumentException, AssociationNotFoundException, PartyNotFoundException,
          ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    if (associationId == null) {
      throw new InvalidArgumentException("associationId");
    }

    if (association == null) {
      throw new InvalidArgumentException("association");
    }

    if (!associationId.equals(association.getId())) {
      throw new InvalidArgumentException("association");
    }

    partyService.updateAssociation(tenantId, association);
  }

  /**
   * Update the mandate.
   *
   * @param tenantId the ID for the tenant
   * @param mandateId the ID for the mandate
   * @param mandate the mandate
   * @throws InvalidArgumentException if an argument is invalid
   * @throws MandateNotFoundException if the mandate could not be found
   * @throws PartyNotFoundException if one or more parties for the mandate could not be found
   * @throws ServiceUnavailableException if the mandate could not be updated
   */
  @Operation(summary = "Update the mandate", description = "Update the mandate")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "The mandate was updated successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description =
                "The mandate could not be found or one or more parties for the mandate could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/mandates/{mandateId}",
      method = RequestMethod.PUT,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.MandateAdministration')")
  public void updateMandate(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "mandateId", description = "The ID for the mandate", required = true)
          @PathVariable
          UUID mandateId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "The mandate to update",
              required = true)
          @RequestBody
          Mandate mandate)
      throws InvalidArgumentException, MandateNotFoundException, PartyNotFoundException,
          ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    if (mandateId == null) {
      throw new InvalidArgumentException("mandateId");
    }

    if (mandate == null) {
      throw new InvalidArgumentException("mandate");
    }

    if (!mandateId.equals(mandate.getId())) {
      throw new InvalidArgumentException("mandate");
    }

    partyService.updateMandate(tenantId, mandate);
  }

  /**
   * Update the organization.
   *
   * @param tenantId the ID for the tenant
   * @param organizationId the ID for the organization
   * @param organization the organization
   * @throws InvalidArgumentException if an argument is invalid
   * @throws OrganizationNotFoundException if the organization could not be found
   * @throws ServiceUnavailableException if the organization could not be updated
   */
  @Operation(summary = "Update the organization", description = "Update the organization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "The organization was updated successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The organization could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/organizations/{organizationId}",
      method = RequestMethod.PUT,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.OrganizationAdministration')")
  public void updateOrganization(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(
              name = "organizationId",
              description = "The ID for the organization",
              required = true)
          @PathVariable
          UUID organizationId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "The organization to update",
              required = true)
          @RequestBody
          Organization organization)
      throws InvalidArgumentException, OrganizationNotFoundException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    if (organizationId == null) {
      throw new InvalidArgumentException("organizationId");
    }

    if (organization == null) {
      throw new InvalidArgumentException("organization");
    }

    if (!organizationId.equals(organization.getId())) {
      throw new InvalidArgumentException("organization");
    }

    partyService.updateOrganization(tenantId, organization);
  }

  /**
   * Update the person.
   *
   * @param tenantId the ID for the tenant
   * @param personId the ID for the person
   * @param person the person
   * @throws InvalidArgumentException if an argument is invalid
   * @throws PersonNotFoundException if the person could not be found
   * @throws ServiceUnavailableException if the person could not be updated
   */
  @Operation(summary = "Update the person", description = "Update the person")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "The person was updated successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid argument",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Access denied",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "404",
            description = "The person could not be found",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(
            responseCode = "500",
            description =
                "An error has occurred and the request could not be processed at this time",
            content =
                @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetails.class)))
      })
  @RequestMapping(
      value = "/persons/{personId}",
      method = RequestMethod.PUT,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "isSecurityDisabled() or hasRole('Administrator') or hasAuthority('FUNCTION_Party.PartyAdministration') or hasAuthority('FUNCTION_Party.PersonAdministration')")
  public void updatePerson(
      @Parameter(
              name = "Tenant-ID",
              description = "The ID for the tenant",
              example = "00000000-0000-0000-0000-000000000000")
          @RequestHeader(
              name = "Tenant-ID",
              defaultValue = "00000000-0000-0000-0000-000000000000",
              required = false)
          UUID tenantId,
      @Parameter(name = "personId", description = "The ID for the person", required = true)
          @PathVariable
          UUID personId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "The person to update",
              required = true)
          @RequestBody
          Person person)
      throws InvalidArgumentException, PersonNotFoundException, ServiceUnavailableException {
    tenantId = (tenantId == null) ? IPartyService.DEFAULT_TENANT_ID : tenantId;

    if (!hasAccessToTenant(tenantId)) {
      throw new AccessDeniedException("Access denied to the tenant (" + tenantId + ")");
    }

    if (personId == null) {
      throw new InvalidArgumentException("personId");
    }

    if (person == null) {
      throw new InvalidArgumentException("person");
    }

    if (!personId.equals(person.getId())) {
      throw new InvalidArgumentException("person");
    }

    partyService.updatePerson(tenantId, person);
  }
}
