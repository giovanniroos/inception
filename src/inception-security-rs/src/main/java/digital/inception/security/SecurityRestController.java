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

package digital.inception.security;

//~--- non-JDK imports --------------------------------------------------------

import digital.inception.rs.RestControllerError;
import digital.inception.rs.RestUtil;
import digital.inception.rs.SecureRestController;
import digital.inception.validation.InvalidArgumentException;
import digital.inception.validation.ValidationError;

import io.swagger.annotations.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 * The <code>SecurityRestController</code> class.
 *
 * @author Marcus Portmann
 */
@Api(tags = "Security API")
@RestController
@RequestMapping(value = "/api/security")
@SuppressWarnings({ "unused", "WeakerAccess" })
public class SecurityRestController extends SecureRestController
{
  /**
   * The Security Service.
   */
  private ISecurityService securityService;

  /**
   * The JSR-303 validator.
   */
  private Validator validator;

  /**
   * Constructs a new <code>SecurityRestController</code>.
   *
   * @param securityService the Security Service
   * @param validator       the JSR-303 validator
   */
  public SecurityRestController(ISecurityService securityService, Validator validator)
  {
    this.securityService = securityService;
    this.validator = validator;
  }

  /**
   * Add the group member to the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   * @param groupMember     the group member
   */
  @ApiOperation(value = "Add the group member to the group",
      notes = "Add the group member to the group")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The group member was successfully added to the group") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or group or user could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 409, message = "The group member already exists",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups/{groupName}/members",
      method = RequestMethod.POST, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public void addMemberToGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "groupName",
      value = "The name identifying the group", required = true)
  @PathVariable String groupName, @ApiParam(name = "groupMember", value = "The group member",
      required = true)
  @RequestBody GroupMember groupMember)
    throws InvalidArgumentException, UserDirectoryNotFoundException, GroupNotFoundException,
        UserNotFoundException, ExistingGroupMemberException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (groupMember == null)
    {
      throw new InvalidArgumentException("groupMember");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    Set<ConstraintViolation<GroupMember>> constraintViolations = validator.validate(groupMember);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("groupMember", ValidationError.toValidationErrors(
          constraintViolations));
    }

    securityService.addMemberToGroup(userDirectoryId, groupName, groupMember.getMemberType(),
        groupMember.getMemberName());
  }

  /**
   * Add the role to the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   * @param groupRole       the group role
   */
  @ApiOperation(value = "Add the role to the group", notes = "Add the role to the group")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The role was successfully added to the group") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or group or role could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 409, message = "The group role already exists",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups/{groupName}/roles",
      method = RequestMethod.POST, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public void addRoleToGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "groupName",
      value = "The name identifying the group", required = true)
  @PathVariable String groupName, @ApiParam(name = "groupRole", value = "The group role",
      required = true)
  @RequestBody GroupRole groupRole)
    throws InvalidArgumentException, UserDirectoryNotFoundException, GroupNotFoundException,
        RoleNotFoundException, ExistingGroupRoleException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (groupRole == null)
    {
      throw new InvalidArgumentException("groupRole");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    Set<ConstraintViolation<GroupRole>> constraintViolations = validator.validate(groupRole);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("groupRole", ValidationError.toValidationErrors(
          constraintViolations));
    }

    if (groupRole.getRoleCode().equalsIgnoreCase(SecurityService.ADMINISTRATOR_ROLE_CODE)
        && (!hasRole(authentication, SecurityService.ADMINISTRATOR_ROLE_CODE)))
    {
      throw new AccessDeniedException("Insufficient authority to add the " + SecurityService
          .ADMINISTRATOR_ROLE_CODE + " role to the group (" + groupName
          + ") for the user directory (" + userDirectoryId + ")");
    }

    securityService.addRoleToGroup(userDirectoryId, groupName, groupRole.getRoleCode());
  }

  /**
   * Administratively change the password for the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   * @param passwordChange  the password change
   */
  @ApiOperation(value = "Administratively change the password for the user",
      notes = "Administratively change the password for the user")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The password for the user was changed successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or user could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/users/{username}/password",
      method = RequestMethod.PUT, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.UserAdministration') or hasAuthority('FUNCTION_Security.ResetUserPassword')")
  public void adminChangePassword(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "username",
      value = "The username identifying the user", required = true)
  @PathVariable String username, @ApiParam(name = "passwordChange", value = "The password change",
      required = true)
  @RequestBody PasswordChange passwordChange)
    throws InvalidArgumentException, UserDirectoryNotFoundException, UserNotFoundException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(username))
    {
      throw new InvalidArgumentException("username");
    }

    if (passwordChange == null)
    {
      throw new InvalidArgumentException("passwordChange");
    }

    if (StringUtils.isEmpty(passwordChange.getNewPassword()))
    {
      throw new InvalidArgumentException("passwordChange");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    Set<ConstraintViolation<PasswordChange>> constraintViolations = validator.validate(
        passwordChange);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("passwordChange", ValidationError.toValidationErrors(
          constraintViolations));
    }

    securityService.adminChangePassword(userDirectoryId, username, passwordChange.getNewPassword(),
        (passwordChange.getExpirePassword() == null)
        ? false
        : passwordChange.getExpirePassword(),
        (passwordChange.getLockUser() == null)
        ? false
        : passwordChange.getLockUser(),
        (passwordChange.getResetPasswordHistory() == null)
        ? false
        : passwordChange.getResetPasswordHistory(), passwordChange.getReason());
  }

  /**
   * Change the password for the user.
   *
   * @param username        the username identifying the user
   * @param passwordChange  the password change
   */
  @ApiOperation(value = "Change the password for the user",
      notes = "Change the password for the user")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The password for the user was changed successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 401, message = "Authentication failed",
          response = RestControllerError.class) ,
      @ApiResponse(code = 403,
          message = "The user has exceeded the maximum number of failed password attempts and has been locked",
          response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or user could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 409,
          message = "The new password for the user has been used recently and is not valid",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/users/{username}/password", method = RequestMethod.PUT,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void changePassword(@ApiParam(name = "username",
      value = "The username identifying the user", required = true)
  @PathVariable String username, @ApiParam(name = "passwordChange", value = "The password change",
      required = true)
  @RequestBody PasswordChange passwordChange)
    throws InvalidArgumentException, UserDirectoryNotFoundException, UserNotFoundException,
        AuthenticationFailedException, ExistingPasswordException, UserLockedException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (StringUtils.isEmpty(username))
    {
      throw new InvalidArgumentException("username");
    }

    if (passwordChange == null)
    {
      throw new InvalidArgumentException("passwordChange");
    }

    UUID userDirectoryId = securityService.getUserDirectoryIdForUser(username);

    if (userDirectoryId == null)
    {
      throw new UserNotFoundException(username);
    }

    Set<ConstraintViolation<PasswordChange>> constraintViolations = validator.validate(
        passwordChange);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("passwordChange", ValidationError.toValidationErrors(
          constraintViolations));
    }

    if (passwordChange.getReason() == PasswordChangeReason.ADMINISTRATIVE)
    {
      if (!hasAccessToUserDirectory(authentication, userDirectoryId))
      {
        throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
            + ")");
      }

      if (hasRole(authentication, "Administrator")
          || hasAuthority(authentication, "FUNCTION_Security.OrganizationAdministration")
          || hasAuthority(authentication, "FUNCTION_Security.UserAdministration")
          || hasAuthority(authentication, "FUNCTION_Security.ResetUserPassword"))
      {
        securityService.adminChangePassword(userDirectoryId, username,
            passwordChange.getNewPassword(), (passwordChange.getExpirePassword() == null)
            ? false
            : passwordChange.getExpirePassword(), (passwordChange.getLockUser() == null)
            ? false
            : passwordChange.getLockUser(), (passwordChange.getResetPasswordHistory() == null)
            ? false
            : passwordChange.getResetPasswordHistory(), passwordChange.getReason());
      }
      else
      {
        throw new AccessDeniedException("Insufficient access to change the password for the user ("
            + username + ")");
      }
    }
    else if (passwordChange.getReason() == PasswordChangeReason.USER)
    {
      securityService.changePassword(username, passwordChange.getPassword(),
          passwordChange.getNewPassword());
    }
    else if (passwordChange.getReason() == PasswordChangeReason.FORGOTTEN)
    {
      // TODO: PERFORM A FORGOTTEN PASSWORD RESET

    }
  }

  /**
   * Create the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param group           the group
   */
  @ApiOperation(value = "Create the group", notes = "Create the group")
  @ApiResponses(value = { @ApiResponse(code = 204, message = "The group was created successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 409, message = "A group with the specified name already exists",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups", method = RequestMethod.POST,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public void createGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "group", value = "The group",
      required = true)
  @RequestBody Group group)
    throws InvalidArgumentException, UserDirectoryNotFoundException, DuplicateGroupException,
        SecurityServiceException
  {
    if (StringUtils.isEmpty(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (group == null)
    {
      throw new InvalidArgumentException("group");
    }

    if (!hasAccessToUserDirectory(SecurityContextHolder.getContext().getAuthentication(),
        userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    Set<ConstraintViolation<Group>> constraintViolations = validator.validate(group);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("group", ValidationError.toValidationErrors(
          constraintViolations));
    }

    if (!group.getUserDirectoryId().equals(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    securityService.createGroup(userDirectoryId, group);
  }

  /**
   * Create the new organization.
   *
   * @param organization        the organization
   * @param createUserDirectory should a new internal user directory be created for the organization
   */
  @ApiOperation(value = "Create the organization", notes = "Create the organization")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The organization was created successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 409,
          message = "An organization with the specified ID or name already exists",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/organizations", method = RequestMethod.POST,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration')")
  public void createOrganization(@ApiParam(name = "organization", value = "The organization",
      required = true)
  @RequestBody Organization organization, @ApiParam(name = "createUserDirectory",
      value = "Should a new internal user directory be created for the organization")
  @RequestParam(value = "createUserDirectory", required = false) Boolean createUserDirectory)
    throws InvalidArgumentException, DuplicateOrganizationException, SecurityServiceException
  {
    if (organization == null)
    {
      throw new InvalidArgumentException("organization");
    }

    Set<ConstraintViolation<Organization>> constraintViolations = validator.validate(organization);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("organization", ValidationError.toValidationErrors(
          constraintViolations));
    }

    securityService.createOrganization(organization, (createUserDirectory != null)
        && createUserDirectory);
  }

  /**
   * Create the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param user            the user
   * @param expiredPassword create the user with its password expired
   * @param userLocked      create the user locked
   */
  @ApiOperation(value = "Create the user", notes = "Create the user")
  @ApiResponses(value = { @ApiResponse(code = 204, message = "The user was created successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 409, message = "A user with the specified username already exists",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/users", method = RequestMethod.POST,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.UserAdministration')")
  public void createUser(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "user", value = "The user", required = true)
  @RequestBody User user, @ApiParam(name = "expiredPassword",
      value = "Create the user with its password expired")
  @RequestParam(value = "expiredPassword", required = false) Boolean expiredPassword, @ApiParam(
      name = "userLocked",
      value = "Create the user locked")
  @RequestParam(value = "userLocked", required = false) Boolean userLocked)
    throws InvalidArgumentException, UserDirectoryNotFoundException, DuplicateUserException,
        SecurityServiceException
  {
    if (StringUtils.isEmpty(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (user == null)
    {
      throw new InvalidArgumentException("user");
    }

    if (!hasAccessToUserDirectory(SecurityContextHolder.getContext().getAuthentication(),
        userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("user", ValidationError.toValidationErrors(
          constraintViolations));
    }

    securityService.createUser(userDirectoryId, user, (expiredPassword != null)
        && expiredPassword, (userLocked != null) && userLocked);
  }

  /**
   * Create the new user directory.
   *
   * @param userDirectory the user directory
   */
  @ApiOperation(value = "Create the user directory", notes = "Create the user directory")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The user directory was created successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 409,
          message = "A user directory with the specified ID or name already exists",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories", method = RequestMethod.POST,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.UserDirectoryAdministration')")
  public void createUserDirectory(@ApiParam(name = "userDirectory", value = "The user directory",
      required = true)
  @RequestBody UserDirectory userDirectory)
    throws InvalidArgumentException, DuplicateUserDirectoryException, SecurityServiceException
  {
    if (userDirectory == null)
    {
      throw new InvalidArgumentException("userDirectory");
    }

    Set<ConstraintViolation<UserDirectory>> constraintViolations = validator.validate(
        userDirectory);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("userDirectory", ValidationError.toValidationErrors(
          constraintViolations));
    }

    securityService.createUserDirectory(userDirectory);
  }

  /**
   * Delete the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   */
  @ApiOperation(value = "Delete the group", notes = "Delete the group")
  @ApiResponses(value = { @ApiResponse(code = 204, message = "The group was deleted successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or group could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 409,
          message = "The group could not be deleted since it is still associated with 1 or more user(s)",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups/{groupName}",
      method = RequestMethod.DELETE, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public void deleteGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "groupName",
      value = "The name identifying the group", required = true)
  @PathVariable String groupName)
    throws InvalidArgumentException, UserDirectoryNotFoundException, GroupNotFoundException,
        ExistingGroupMembersException, SecurityServiceException
  {
    if (StringUtils.isEmpty(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (!hasAccessToUserDirectory(SecurityContextHolder.getContext().getAuthentication(),
        userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    securityService.deleteGroup(userDirectoryId, groupName);
  }

  /**
   * Delete the organization.
   *
   * @param organizationId the ID used to uniquely identify the organization
   */
  @ApiOperation(value = "Delete the organization", notes = "Delete the organization")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The organization was deleted successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The organization could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/organizations/{organizationId}", method = RequestMethod.DELETE,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration')")
  public void deleteOrganization(@ApiParam(name = "organizationId",
      value = "The ID used to uniquely identify the organization", required = true)
  @PathVariable UUID organizationId)
    throws InvalidArgumentException, OrganizationNotFoundException, SecurityServiceException
  {
    if (StringUtils.isEmpty(organizationId))
    {
      throw new InvalidArgumentException("organizationId");
    }

    securityService.deleteOrganization(organizationId);
  }

  /**
   * Delete the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   */
  @ApiOperation(value = "Delete the user", notes = "Delete the user")
  @ApiResponses(value = { @ApiResponse(code = 204, message = "The user was deleted successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or user could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/users/{username}",
      method = RequestMethod.DELETE, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.UserAdministration')")
  public void deleteUser(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "username",
      value = "The username identifying the user", required = true)
  @PathVariable String username)
    throws InvalidArgumentException, UserDirectoryNotFoundException, UserNotFoundException,
        SecurityServiceException
  {
    if (StringUtils.isEmpty(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(username))
    {
      throw new InvalidArgumentException("username");
    }

    if (!hasAccessToUserDirectory(SecurityContextHolder.getContext().getAuthentication(),
        userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    securityService.deleteUser(userDirectoryId, username);
  }

  /**
   * Delete the user directory.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   */
  @ApiOperation(value = "Delete the user directory", notes = "Delete the user directory")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The user directory was deleted successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}", method = RequestMethod.DELETE,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.UserDirectoryAdministration')")
  public void deleteUserDirectory(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId)
    throws InvalidArgumentException, UserDirectoryNotFoundException, SecurityServiceException
  {
    if (StringUtils.isEmpty(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    securityService.deleteUserDirectory(userDirectoryId);
  }

  /**
   * Retrieve the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   *
   * @return the group
   */
  @ApiOperation(value = "Retrieve the group", notes = "Retrieve the group")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or group could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups/{groupName}",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public Group getGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "groupName",
      value = "The name identifying the group", required = true)
  @PathVariable String groupName)
    throws InvalidArgumentException, UserDirectoryNotFoundException, GroupNotFoundException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (StringUtils.isEmpty(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    return securityService.getGroup(userDirectoryId, groupName);
  }

  /**
   * Retrieve all the group names.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the group names
   */
  @ApiOperation(value = "Retrieve the group names", notes = "Retrieve the group names")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/group-names",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public List<String> getGroupNames(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId)
    throws InvalidArgumentException, UserDirectoryNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (StringUtils.isEmpty(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    if (!hasAccessToUserDirectory(SecurityContextHolder.getContext().getAuthentication(),
        userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    return securityService.getGroupNames(userDirectoryId);
  }

  /**
   * Retrieve the names identifying the groups the user is a member of.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   *
   * @return the names identifying the groups the user is a member of
   */
  @ApiOperation(value = "Retrieve the names identifying the groups the user is a member of",
      notes = "Retrieve the names identifying the groups the user is a member of")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or user could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/users/{username}/group-names",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration')")
  public List<String> getGroupNamesForUser(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "username",
      value = "The username identifying the user", required = true)
  @PathVariable String username)
    throws InvalidArgumentException, UserDirectoryNotFoundException, UserNotFoundException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (StringUtils.isEmpty(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(username))
    {
      throw new InvalidArgumentException("username");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    return securityService.getGroupNamesForUser(userDirectoryId, username);
  }

  /**
   * Retrieve the groups.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param filter          the optional filter to apply to the groups
   * @param sortDirection   the optional sort direction to apply to the groups
   * @param pageIndex       the optional page index
   * @param pageSize        the optional page size
   *
   * @return the groups
   */
  @ApiOperation(value = "Retrieve the groups", notes = "Retrieve the groups")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public ResponseEntity<List<Group>> getGroups(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "filter",
      value = "The optional filter to apply to the groups")
  @RequestParam(value = "filter", required = false) String filter, @ApiParam(name = "sortDirection",
      value = "The optional sort direction to apply to the groups")
  @RequestParam(value = "sortDirection", required = false) SortDirection sortDirection, @ApiParam(
      name = "pageIndex",
      value = "The optional page index", example = "0")
  @RequestParam(value = "pageIndex", required = false) Integer pageIndex, @ApiParam(
      name = "pageSize",
      value = "The optional page size", example = "0")
  @RequestParam(value = "pageSize", required = false) Integer pageSize)
    throws InvalidArgumentException, UserDirectoryNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (StringUtils.isEmpty(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    var groups = securityService.getGroups(userDirectoryId, filter, sortDirection, pageIndex,
        pageSize);

    var numberOfGroups = securityService.getNumberOfGroups(userDirectoryId, filter);

    var httpHeaders = new HttpHeaders();
    httpHeaders.add("x-total-count", String.valueOf(numberOfGroups));

    return new ResponseEntity<>(groups, httpHeaders, HttpStatus.OK);
  }

  /**
   * Retrieve the group members.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   * @param filter          the optional filter to apply to the group members
   * @param sortDirection   the optional sort direction to apply to the group members
   * @param pageIndex       the optional page index
   * @param pageSize        the optional page size
   */
  @ApiOperation(value = "Retrieve the group members", notes = "Retrieve the group members")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or group could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups/{groupName}/members",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public ResponseEntity<List<GroupMember>> getMembersForGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "groupName",
      value = "The name identifying the group", required = true)
  @PathVariable String groupName, @ApiParam(name = "filter",
      value = "The optional filter to apply to the group members")
  @RequestParam(value = "filter", required = false) String filter, @ApiParam(name = "sortDirection",
      value = "The optional sort direction to apply to the group members")
  @RequestParam(value = "sortDirection", required = false) SortDirection sortDirection, @ApiParam(
      name = "pageIndex",
      value = "The optional page index", example = "0")
  @RequestParam(value = "pageIndex", required = false) Integer pageIndex, @ApiParam(
      name = "pageSize",
      value = "The optional page size", example = "0")
  @RequestParam(value = "pageSize", required = false) Integer pageSize)
    throws InvalidArgumentException, UserDirectoryNotFoundException, GroupNotFoundException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    var groupMembers = securityService.getMembersForGroup(userDirectoryId, groupName, filter,
        sortDirection, pageIndex, pageSize);

    var numberOfGroupMembers = securityService.getNumberOfMembersForGroup(userDirectoryId,
        groupName, filter);

    var httpHeaders = new HttpHeaders();
    httpHeaders.add("x-total-count", String.valueOf(numberOfGroupMembers));

    return new ResponseEntity<>(groupMembers, httpHeaders, HttpStatus.OK);
  }

  /**
   * Retrieve the organization.
   *
   * @param organizationId the ID used to uniquely identify the organization
   *
   * @return the organization
   */
  @ApiOperation(value = "Retrieve the organization", notes = "Retrieve the organization")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The organization could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/organizations/{organizationId}", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration')")
  public Organization getOrganization(@ApiParam(name = "organizationId",
      value = "The ID used to uniquely identify the organization", required = true)
  @PathVariable UUID organizationId)
    throws InvalidArgumentException, OrganizationNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (StringUtils.isEmpty(organizationId))
    {
      throw new InvalidArgumentException("organizationId");
    }

    return securityService.getOrganization(organizationId);
  }

  /**
   * Retrieve the name of the organization.
   *
   * @param organizationId the ID used to uniquely identify the organization
   *
   * @return the name of the organization
   */
  @ApiOperation(value = "Retrieve the name of organization",
      notes = "Retrieve the name of the organization")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The organization could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/organizations/{organizationId}/name", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration')")
  public String getOrganizationName(@ApiParam(name = "organizationId",
      value = "The ID used to uniquely identify the organization", required = true)
  @PathVariable UUID organizationId)
    throws InvalidArgumentException, OrganizationNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (StringUtils.isEmpty(organizationId))
    {
      throw new InvalidArgumentException("organizationId");
    }

    return RestUtil.quote(securityService.getOrganizationName(organizationId));
  }

  /**
   * Retrieve the organizations.
   *
   * @param filter        the optional filter to apply to the organizations
   * @param sortDirection the optional sort direction to apply to the organizations
   * @param pageIndex     the optional page index
   * @param pageSize      the optional page size
   *
   * @return the organizations
   */
  @ApiOperation(value = "Retrieve the organizations", notes = "Retrieve the organizations")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/organizations", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration')")
  public ResponseEntity<List<Organization>> getOrganizations(@ApiParam(name = "filter",
      value = "The optional filter to apply to the organizations")
  @RequestParam(value = "filter", required = false) String filter, @ApiParam(name = "sortDirection",
      value = "The optional sort direction to apply to the organizations")
  @RequestParam(value = "sortDirection", required = false) SortDirection sortDirection, @ApiParam(
      name = "pageIndex",
      value = "The optional page index", example = "0")
  @RequestParam(value = "pageIndex", required = false) Integer pageIndex, @ApiParam(
      name = "pageSize",
      value = "The optional page size", example = "0")
  @RequestParam(value = "pageSize", required = false) Integer pageSize)
    throws SecurityServiceException
  {
    var httpHeaders = new HttpHeaders();
    httpHeaders.add("x-total-count", String.valueOf(securityService.getNumberOfOrganizations(
        filter)));

    return new ResponseEntity<>(securityService.getOrganizations(filter, sortDirection, pageIndex,
        pageSize), httpHeaders, HttpStatus.OK);
  }

  /**
   * Retrieve the organizations the user directory is associated with.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the organizations the user directory is associated with
   */
  @ApiOperation(value = "Retrieve the organizations the user directory is associated with",
      notes = "Retrieve the organizations the user directory is associated with")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/organizations",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<Organization>> getOrganizationsForUserDirectory(@ApiParam(
      name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId)
    throws InvalidArgumentException, UserDirectoryNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (!authentication.isAuthenticated())
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    List<Organization> organizations = securityService.getOrganizationsForUserDirectory(
        userDirectoryId);

    return new ResponseEntity<>(organizations, HttpStatus.OK);
  }

  /**
   * Retrieve the codes for the roles that have been assigned to the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   *
   * @return the codes for the roles that have been assigned to the group
   */
  @ApiOperation(value = "Retrieve the codes for the roles that have been assigned to the group",
      notes = "Retrieve the codes for the roles that have been assigned to the group")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or group could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups/{groupName}/role-codes",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public List<String> getRoleCodesForGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "groupName",
      value = "The name identifying the group", required = true)
  @PathVariable String groupName)
    throws InvalidArgumentException, UserDirectoryNotFoundException, GroupNotFoundException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    return securityService.getRoleCodesForGroup(userDirectoryId, groupName);
  }

  /**
   * Retrieve all the roles.
   *
   * @return the roles
   */
  @ApiOperation(value = "Retrieve all the roles", notes = "Retrieve all the roles")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public List<Role> getRoles()
    throws SecurityServiceException
  {
    return securityService.getRoles();
  }

  /**
   * Retrieve the roles that have been assigned to the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   *
   * @return the roles that have been assigned to the group
   */
  @ApiOperation(value = "Retrieve the roles that have been assigned to the group",
      notes = "Retrieve the roles that have been assigned to the group")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or group could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups/{groupName}/roles",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public List<GroupRole> getRolesForGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "groupName",
      value = "The name identifying the group", required = true)
  @PathVariable String groupName)
    throws InvalidArgumentException, UserDirectoryNotFoundException, GroupNotFoundException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    return securityService.getRolesForGroup(userDirectoryId, groupName);
  }

  /**
   * Retrieve the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   *
   * @return the user
   */
  @ApiOperation(value = "Retrieve the user", notes = "Retrieve the user")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or user could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/users/{username}",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.UserAdministration') or hasAuthority('FUNCTION_Security.ResetUserPassword')")
  public User getUser(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "username",
      value = "The username identifying the user", required = true)
  @PathVariable String username)
    throws InvalidArgumentException, UserDirectoryNotFoundException, UserNotFoundException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(username))
    {
      throw new InvalidArgumentException("username");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    User user = securityService.getUser(userDirectoryId, username);

    // Remove the password information
    user.setPassword(null);
    user.setPasswordAttempts(null);
    user.setPasswordExpiry(null);

    return user;
  }

  /**
   * Retrieve the user directories.
   *
   * @param filter        the optional filter to apply to the user directories
   * @param sortDirection the optional sort direction to apply to the user directories
   * @param pageIndex     the optional page index
   * @param pageSize      the optional page size
   *
   * @return the user directories
   */
  @ApiOperation(value = "Retrieve the user directories", notes = "Retrieve the user directories")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.UserDirectoryAdministration')")
  public ResponseEntity<List<UserDirectory>> getUserDirectories(@ApiParam(name = "filter",
      value = "The optional filter to apply to the user directories")
  @RequestParam(value = "filter", required = false) String filter, @ApiParam(name = "sortDirection",
      value = "The optional sort direction to apply to the user directories")
  @RequestParam(value = "sortDirection", required = false) SortDirection sortDirection, @ApiParam(
      name = "pageIndex",
      value = "The optional page index", example = "0")
  @RequestParam(value = "pageIndex", required = false) Integer pageIndex, @ApiParam(
      name = "pageSize",
      value = "The optional page size", example = "0")
  @RequestParam(value = "pageSize", required = false) Integer pageSize)
    throws SecurityServiceException
  {
    var httpHeaders = new HttpHeaders();
    httpHeaders.add("x-total-count", String.valueOf(securityService.getNumberOfUserDirectories(
        filter)));

    return new ResponseEntity<>(securityService.getUserDirectories(filter, sortDirection,
        pageIndex, pageSize), httpHeaders, HttpStatus.OK);
  }

  /**
   * Retrieve the user directories the organization is associated with.
   *
   * @param organizationId the ID used to uniquely identify the organization
   *
   * @return the user directories the organization is associated with
   */
  @ApiOperation(value = "Retrieve the user directories the organization is associated with",
      notes = "Retrieve the user directories the organization is associated with")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The organization could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/organizations/{organizationId}/user-directories",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.UserAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public ResponseEntity<List<UserDirectory>> getUserDirectoriesForOrganization(@ApiParam(
      name = "organizationId",
      value = "The ID used to uniquely identify the organization", required = true)
  @PathVariable UUID organizationId)
    throws InvalidArgumentException, OrganizationNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (StringUtils.isEmpty(organizationId))
    {
      throw new InvalidArgumentException("organizationId");
    }

    List<UserDirectory> userDirectories = securityService.getUserDirectoriesForOrganization(
        organizationId);

    List<UserDirectory> filteredUserDirectories = new ArrayList<>();

    for (UserDirectory userDirectory : userDirectories)
    {
      if (hasAccessToUserDirectory(authentication, userDirectory.getId()))
      {
        filteredUserDirectories.add(userDirectory);
      }
    }

    return new ResponseEntity<>(filteredUserDirectories, HttpStatus.OK);
  }

  /**
   * Retrieve the user directory.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the user directory
   */
  @ApiOperation(value = "Retrieve the user directory", notes = "Retrieve the user directory")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.UserDirectoryAdministration')")
  public UserDirectory getUserDirectory(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId)
    throws InvalidArgumentException, UserDirectoryNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    return securityService.getUserDirectory(userDirectoryId);
  }

  /**
   * Retrieve the capabilities the user directory supports.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the capabilities the user directory supports
   */
  @ApiOperation(value = "Retrieve the capabilities the user directory supports",
      notes = "Retrieve the capabilities the user directory supports")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/capabilities",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.UserDirectoryAdministration') or hasAuthority('FUNCTION_Security.UserAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration') or hasAuthority('FUNCTION_Security.ResetUserPassword')")
  public UserDirectoryCapabilities getUserDirectoryCapabilities(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId)
    throws InvalidArgumentException, UserDirectoryNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    return securityService.getUserDirectoryCapabilities(userDirectoryId);
  }

  /**
   * Retrieve the name of the user directory.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the name of user directory
   */
  @ApiOperation(value = "Retrieve the name of the user directory",
      notes = "Retrieve the name of the user directory")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/name", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.UserDirectoryAdministration')")
  public String getUserDirectoryName(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId)
    throws InvalidArgumentException, UserDirectoryNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    return RestUtil.quote(securityService.getUserDirectoryName(userDirectoryId));
  }

  /**
   * Retrieve the summaries for the user directories.
   *
   * @param filter        the optional filter to apply to the user directories
   * @param sortDirection the optional sort direction to apply to the user directories
   * @param pageIndex     the optional page index
   * @param pageSize      the optional page size
   *
   * @return the summaries for the user directories
   */
  @ApiOperation(value = "Retrieve the summaries for the user directories",
      notes = "Retrieve the summaries for the user directories")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directory-summaries", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.UserDirectoryAdministration')")
  public ResponseEntity<List<UserDirectorySummary>> getUserDirectorySummaries(@ApiParam(
      name = "filter",
      value = "The optional filter to apply to the user directories")
  @RequestParam(value = "filter", required = false) String filter, @ApiParam(name = "sortDirection",
      value = "The optional sort direction to apply to the user directories")
  @RequestParam(value = "sortDirection", required = false) SortDirection sortDirection, @ApiParam(
      name = "pageIndex",
      value = "The optional page index", example = "0")
  @RequestParam(value = "pageIndex", required = false) Integer pageIndex, @ApiParam(
      name = "pageSize",
      value = "The optional page size", example = "0")
  @RequestParam(value = "pageSize", required = false) Integer pageSize)
    throws SecurityServiceException
  {
    var httpHeaders = new HttpHeaders();
    httpHeaders.add("x-total-count", String.valueOf(securityService.getNumberOfUserDirectories(
        filter)));

    return new ResponseEntity<>(securityService.getUserDirectorySummaries(filter, sortDirection,
        pageIndex, pageSize), httpHeaders, HttpStatus.OK);
  }

  /**
   * Retrieve the summaries for the user directories the organization is associated with.
   *
   * @param organizationId the ID used to uniquely identify the organization
   *
   * @return the summaries for the user directories the organization is associated with
   */
  @ApiOperation(
      value = "Retrieve the summaries for the user directories the organization is associated with",
      notes = "Retrieve the summaries for the user directories the organization is associated with")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The organization could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/organizations/{organizationId}/user-directory-summaries",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.ResetUserPassword') or hasAuthority('FUNCTION_Security.UserAdministration') or hasAuthority('FUNCTION_Security.UserGroups')")
  public ResponseEntity<List<UserDirectorySummary>> getUserDirectorySummariesForOrganization(
      @ApiParam(name = "organizationId",
          value = "The ID used to uniquely identify the organization", required = true)
  @PathVariable UUID organizationId)
    throws InvalidArgumentException, OrganizationNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (StringUtils.isEmpty(organizationId))
    {
      throw new InvalidArgumentException("organizationId");
    }

    List<UserDirectorySummary> userDirectorySummaries =
        securityService.getUserDirectorySummariesForOrganization(organizationId);

    List<UserDirectorySummary> filteredUserDirectorySummaries = new ArrayList<>();

    for (UserDirectorySummary userDirectorySummary : userDirectorySummaries)
    {
      if (hasAccessToUserDirectory(authentication, userDirectorySummary.getId()))
      {
        filteredUserDirectorySummaries.add(userDirectorySummary);
      }
    }

    return new ResponseEntity<>(filteredUserDirectorySummaries, HttpStatus.OK);
  }

  /**
   * Retrieve the user directory type for the user directory.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the user directory type for the user directory
   */
  @ApiOperation(value = "Retrieve the user directory type for the user directory",
      notes = "Retrieve the user directory type for the user directory")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404,
          message = "The user directory or user directory type could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/user-directory-type",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.UserDirectoryAdministration') or hasAuthority('FUNCTION_Security.UserAdministration')")
  public UserDirectoryType getUserDirectoryTypeForUserDirectory(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId)
    throws InvalidArgumentException, UserDirectoryNotFoundException,
        UserDirectoryTypeNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    return securityService.getUserDirectoryTypeForUserDirectory(userDirectoryId);
  }

  /**
   * Retrieve the user directory types.
   *
   * @return the user directory types
   */
  @ApiOperation(value = "Retrieve the user directory types",
      notes = "Retrieve the user directory types")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directory-types", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.UserDirectoryAdministration')")
  public List<UserDirectoryType> getUserDirectoryTypes()
    throws SecurityServiceException
  {
    return securityService.getUserDirectoryTypes();
  }

  /**
   * Retrieve the full name for the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   *
   * @return the full name for the user
   */
  @ApiOperation(value = "Retrieve the full name for the user",
      notes = "Retrieve the full name for the user")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or user could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/users/{username}/full-name",
      method = RequestMethod.GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.UserAdministration') or hasAuthority('FUNCTION_Security.ResetUserPassword')")
  public String getUserFullName(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "username",
      value = "The username identifying the user", required = true)
  @PathVariable String username)
    throws InvalidArgumentException, UserDirectoryNotFoundException, UserNotFoundException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(username))
    {
      throw new InvalidArgumentException("username");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    return RestUtil.quote(securityService.getUserFullName(userDirectoryId, username));
  }

  /**
   * Retrieve the users.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param filter          the optional filter to apply to the users
   * @param sortBy          The optional method used to sort the users e.g. by last name.
   * @param sortDirection   the optional sort direction to apply to the users
   * @param pageIndex       the optional page index
   * @param pageSize        the optional page size
   *
   * @return the users
   */
  @ApiOperation(value = "Retrieve the users", notes = "Retrieve the users")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/users", method = RequestMethod.GET,
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.UserAdministration') or hasAuthority('FUNCTION_Security.ResetUserPassword')")
  public ResponseEntity<List<User>> getUsers(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "filter",
      value = "The optional filter to apply to the users")
  @RequestParam(value = "filter", required = false) String filter, @ApiParam(name = "sortBy",
      value = "The optional method used to sort the users e.g. by last name")
  @RequestParam(value = "sortBy", required = false) UserSortBy sortBy, @ApiParam(
      name = "sortDirection",
      value = "The optional sort direction to apply to the users")
  @RequestParam(value = "sortDirection", required = false) SortDirection sortDirection, @ApiParam(
      name = "pageIndex",
      value = "The optional page index", example = "0")
  @RequestParam(value = "pageIndex", required = false) Integer pageIndex, @ApiParam(
      name = "pageSize",
      value = "The optional page size", example = "0")
  @RequestParam(value = "pageSize", required = false) Integer pageSize)
    throws InvalidArgumentException, UserDirectoryNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    var users = securityService.getUsers(userDirectoryId, filter, sortBy, sortDirection, pageIndex,
        pageSize);

    var numberOfUsers = securityService.getNumberOfUsers(userDirectoryId, filter);

    var httpHeaders = new HttpHeaders();
    httpHeaders.add("x-total-count", String.valueOf(numberOfUsers));

    return new ResponseEntity<>(users, httpHeaders, HttpStatus.OK);
  }

  /**
   * Remove the group member from the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   * @param memberType      the group member type
   * @param memberName      the name identifying the group member
   */
  @ApiOperation(value = "Remove the group member from the group",
      notes = "Remove the group member from the group")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The group member was successfully removed from the group") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404,
          message = "The user directory or group or group member could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(
      value = "/user-directories/{userDirectoryId}/groups/{groupName}/members/{memberType}/{memberName}",
      method = RequestMethod.DELETE, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public void removeMemberFromGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "groupName",
      value = "The name identifying the group", required = true)
  @PathVariable String groupName, @ApiParam(name = "memberType", value = "The group member type",
      required = true)
  @PathVariable GroupMemberType memberType, @ApiParam(name = "memberName",
      value = "The name identifying the group member", required = true)
  @PathVariable String memberName)
    throws InvalidArgumentException, UserDirectoryNotFoundException, GroupNotFoundException,
        GroupMemberNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (memberType == null)
    {
      throw new InvalidArgumentException("memberType");
    }

    if (StringUtils.isEmpty(memberName))
    {
      throw new InvalidArgumentException("memberName");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    securityService.removeMemberFromGroup(userDirectoryId, groupName, memberType, memberName);
  }

  /**
   * Remove the role from the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   * @param roleCode        the code used to uniquely identify the role
   */
  @ApiOperation(value = "Remove the role from the group", notes = "Remove the role from the group")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The role was successfully removed from the group") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404,
          message = "The user directory or group or group role could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups/{groupName}/roles/{roleCode}",
      method = RequestMethod.DELETE, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public void removeRoleFromGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "groupName",
      value = "The name identifying the group", required = true)
  @PathVariable String groupName, @ApiParam(name = "roleCode",
      value = "The code used to uniquely identify the role", required = true)
  @PathVariable String roleCode)
    throws InvalidArgumentException, UserDirectoryNotFoundException, GroupNotFoundException,
        GroupRoleNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (StringUtils.isEmpty(roleCode))
    {
      throw new InvalidArgumentException("roleCode");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    if (roleCode.equalsIgnoreCase(SecurityService.ADMINISTRATOR_ROLE_CODE)
        && (!hasRole(authentication, SecurityService.ADMINISTRATOR_ROLE_CODE)))
    {
      throw new AccessDeniedException("Insufficient authority to remove the " + SecurityService
          .ADMINISTRATOR_ROLE_CODE + " role from the group (" + groupName
          + ") for the user directory (" + userDirectoryId + ")");
    }

    securityService.removeRoleFromGroup(userDirectoryId, groupName, roleCode);
  }

  /**
   * Update the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName      the name identifying the group
   */
  @ApiOperation(value = "Update the group", notes = "Update the group")
  @ApiResponses(value = { @ApiResponse(code = 204, message = "The group was updated successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or group could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/groups/{groupName}",
      method = RequestMethod.PUT, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.GroupAdministration')")
  public void updateGroup(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "groupName",
      value = "The name identifying the group", required = true)
  @PathVariable String groupName, @ApiParam(name = "group", value = "The group", required = true)
  @RequestBody Group group)
    throws InvalidArgumentException, UserDirectoryNotFoundException, GroupNotFoundException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (group == null)
    {
      throw new InvalidArgumentException("group");
    }

    if (!group.getName().equals(groupName))
    {
      throw new InvalidArgumentException("groupName");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    Set<ConstraintViolation<Group>> constraintViolations = validator.validate(group);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("group", ValidationError.toValidationErrors(
          constraintViolations));
    }

    securityService.updateGroup(userDirectoryId, group);
  }

  /**
   * Update the organization.
   *
   * @param organizationId the ID used to uniquely identify the organization
   * @param organization   the organization
   */
  @ApiOperation(value = "Update the organization", notes = "Update the organization")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The organization was updated successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The organization could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/organizations/{organizationId}", method = RequestMethod.PUT,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration')")
  public void updateOrganization(@ApiParam(name = "organizationId",
      value = "The ID used to uniquely identify the organization", required = true)
  @PathVariable UUID organizationId, @ApiParam(name = "organization", value = "The organization",
      required = true)
  @RequestBody Organization organization)
    throws InvalidArgumentException, OrganizationNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (StringUtils.isEmpty(organizationId))
    {
      throw new InvalidArgumentException("organizationId");
    }

    if (organization == null)
    {
      throw new InvalidArgumentException("organization");
    }

    if (!organization.getId().equals(organizationId))
    {
      throw new InvalidArgumentException("organizationId");
    }

    Set<ConstraintViolation<Organization>> constraintViolations = validator.validate(organization);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("organization", ValidationError.toValidationErrors(
          constraintViolations));
    }

    securityService.updateOrganization(organization);
  }

  /**
   * Update the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   * @param user            the user
   * @param expirePassword  expire the user's password
   * @param lockUser        lock the user
   */
  @ApiOperation(value = "Update the user", notes = "Update the user")
  @ApiResponses(value = { @ApiResponse(code = 204, message = "The user was updated successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory or user could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}/users/{username}",
      method = RequestMethod.PUT, produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.OrganizationAdministration') or hasAuthority('FUNCTION_Security.UserAdministration')")
  public void updateUser(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "username",
      value = "The username identifying the user", required = true)
  @PathVariable String username, @ApiParam(name = "user", value = "The user", required = true)
  @RequestBody User user, @ApiParam(name = "expirePassword", value = "Expire the user's password")
  @RequestParam(value = "expirePassword", required = false) Boolean expirePassword, @ApiParam(
      name = "lockUser",
      value = "Lock the user")
  @RequestParam(value = "lockUser", required = false) Boolean lockUser)
    throws InvalidArgumentException, UserDirectoryNotFoundException, UserNotFoundException,
        SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (StringUtils.isEmpty(username))
    {
      throw new InvalidArgumentException("username");
    }

    if (user == null)
    {
      throw new InvalidArgumentException("user");
    }

    if (!user.getUsername().equals(username))
    {
      throw new InvalidArgumentException("username");
    }

    if (!hasAccessToUserDirectory(authentication, userDirectoryId))
    {
      throw new AccessDeniedException("Access denied to the user directory (" + userDirectoryId
          + ")");
    }

    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("user", ValidationError.toValidationErrors(
          constraintViolations));
    }

    securityService.updateUser(userDirectoryId, user, (expirePassword != null)
        && expirePassword, (lockUser != null) && lockUser);
  }

  /**
   * Update the user directory.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param userDirectory   the user directory
   */
  @ApiOperation(value = "Update the user directory", notes = "Update the user directory")
  @ApiResponses(value = { @ApiResponse(code = 204,
      message = "The user directory was updated successfully") ,
      @ApiResponse(code = 400, message = "Invalid argument", response = RestControllerError.class) ,
      @ApiResponse(code = 404, message = "The user directory could not be found",
          response = RestControllerError.class) ,
      @ApiResponse(code = 500,
          message = "An error has occurred and the service is unable to process the request at this time",
          response = RestControllerError.class) })
  @RequestMapping(value = "/user-directories/{userDirectoryId}", method = RequestMethod.PUT,
      produces = "application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(
      "hasRole('Administrator') or hasAuthority('FUNCTION_Security.UserDirectoryAdministration')")
  public void updateUserDirectory(@ApiParam(name = "userDirectoryId",
      value = "The ID used to uniquely identify the user directory", required = true)
  @PathVariable UUID userDirectoryId, @ApiParam(name = "userDirectory",
      value = "The user directory", required = true)
  @RequestBody UserDirectory userDirectory)
    throws InvalidArgumentException, UserDirectoryNotFoundException, SecurityServiceException
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (userDirectoryId == null)
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    if (userDirectory == null)
    {
      throw new InvalidArgumentException("userDirectory");
    }

    if (!userDirectory.getId().equals(userDirectoryId))
    {
      throw new InvalidArgumentException("userDirectoryId");
    }

    Set<ConstraintViolation<UserDirectory>> constraintViolations = validator.validate(
        userDirectory);

    if (!constraintViolations.isEmpty())
    {
      throw new InvalidArgumentException("userDirectory", ValidationError.toValidationErrors(
          constraintViolations));
    }

    securityService.updateUserDirectory(userDirectory);
  }

  /**
   * Confirm that the user associated with the authenticated request has access to the user
   * directory.
   *
   * @param authentication  the authenticated principal
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return <code>true</code> if the user associated with the authenticated request has access to
   *         the user directory or <code>false</code> otherwise
   */
  protected boolean hasAccessToUserDirectory(Authentication authentication, UUID userDirectoryId)
    throws AccessDeniedException
  {
    // If the user is not authenticated then they cannot have access
    if (!authentication.isAuthenticated())
    {
      return false;
    }

    // If the user has the "Administrator" role they always have access
    if (hasRole(authentication, SecurityService.ADMINISTRATOR_ROLE_CODE))
    {
      return true;
    }

    // If the user is associated with the user directory then they have access
    List<UUID> userDirectoryAuthorityValues = getUUIDValuesForAuthoritiesWithPrefix(authentication,
        "USER_DIRECTORY_");

    return userDirectoryAuthorityValues.stream().anyMatch(userDirectoryId::equals);
  }
}
