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

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

/**
 * The <code>ISecurityService</code> interface defines the functionality provided by a Security
 * Service implementation, which manages the security related information for an application.
 *
 * @author Marcus Portmann
 */
public interface ISecurityService
{
  /**
   * Add the user to the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   * @param groupName       the name identifying the group
   */
  void addUserToGroup(Long userDirectoryId, String username, String groupName)
    throws UserDirectoryNotFoundException, UserNotFoundException, GroupNotFoundException,
        SecurityServiceException;

  /**
   * Administratively change the password for the user.
   *
   * @param userDirectoryId      the ID used to uniquely identify the user directory
   * @param username             the username identifying the user
   * @param newPassword          the new password
   * @param expirePassword       expire the user's password
   * @param lockUser             lock the user
   * @param resetPasswordHistory reset the user's password history
   * @param reason               the reason for changing the password
   */
  void adminChangePassword(Long userDirectoryId, String username, String newPassword,
      boolean expirePassword, boolean lockUser, boolean resetPasswordHistory,
      PasswordChangeReason reason)
    throws UserDirectoryNotFoundException, UserNotFoundException, SecurityServiceException;

  /**
   * Authenticate the user.
   *
   * @param username the username identifying the user
   * @param password the password being used to authenticate
   *
   * @return the ID used to uniquely identify the user directory
   */
  Long authenticate(String username, String password)
    throws AuthenticationFailedException, UserLockedException, ExpiredPasswordException,
        UserNotFoundException, SecurityServiceException;

  /**
   * Change the password for the user.
   *
   * @param username    the username identifying the user
   * @param password    the password for the user that is used to authorise the operation
   * @param newPassword the new password
   *
   * @return the ID used to uniquely identify the user directory
   */
  Long changePassword(String username, String password, String newPassword)
    throws AuthenticationFailedException, UserLockedException, UserNotFoundException,
        ExistingPasswordException, SecurityServiceException;

  /**
   * Create the new authorised function.
   *
   * @param function the function
   */
  void createFunction(Function function)
    throws DuplicateFunctionException, SecurityServiceException;

  /**
   * Create the new group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param group           the group
   */
  void createGroup(Long userDirectoryId, Group group)
    throws UserDirectoryNotFoundException, DuplicateGroupException, SecurityServiceException;

  /**
   * Create the new organization.
   *
   * @param organization        the organization
   * @param createUserDirectory should a new internal user directory be created for the organization
   *
   * @return the new internal user directory that was created for the organization or
   *         <code>null</code> if no user directory was created
   */
  UserDirectory createOrganization(Organization organization, boolean createUserDirectory)
    throws DuplicateOrganizationException, SecurityServiceException;

  /**
   * Create the new user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param user            the user
   * @param expiredPassword create the user with its password expired
   * @param userLocked      create the user locked
   */
  void createUser(Long userDirectoryId, User user, boolean expiredPassword, boolean userLocked)
    throws UserDirectoryNotFoundException, DuplicateUserException, SecurityServiceException;

  /**
   * Create the new user directory.
   *
   * @param userDirectory the user directory
   */
  void createUserDirectory(UserDirectory userDirectory)
    throws DuplicateUserDirectoryException, SecurityServiceException;

  /**
   * Delete the authorised function.
   *
   * @param functionCode the code used to uniquely identify the function
   */
  void deleteFunction(String functionCode)
    throws FunctionNotFoundException, SecurityServiceException;

  /**
   * Delete the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   */
  void deleteGroup(Long userDirectoryId, String groupName)
    throws UserDirectoryNotFoundException, GroupNotFoundException, ExistingGroupMembersException,
        SecurityServiceException;

  /**
   * Delete the organization.
   *
   * @param organizationId the ID used to uniquely identify the organization
   */
  void deleteOrganization(Long organizationId)
    throws OrganizationNotFoundException, SecurityServiceException;

  /**
   * Delete the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   */
  void deleteUser(Long userDirectoryId, String username)
    throws UserDirectoryNotFoundException, UserNotFoundException, SecurityServiceException;

  /**
   * Delete the user directory.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   */
  void deleteUserDirectory(Long userDirectoryId)
    throws UserDirectoryNotFoundException, SecurityServiceException;

  /**
   * Retrieve the users matching the attribute criteria.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param attributes      the attribute criteria used to select the users
   *
   * @return the users whose attributes match the attribute criteria
   */
  List<User> findUsers(Long userDirectoryId, List<Attribute> attributes)
    throws UserDirectoryNotFoundException, InvalidAttributeException, SecurityServiceException;

  /**
   * Retrieve the authorised function.
   *
   * @param functionCode the code used to uniquely identify the function
   *
   * @return the authorised function
   */
  Function getFunction(String functionCode)
    throws FunctionNotFoundException, SecurityServiceException;

  /**
   * Retrieve the authorised function codes for the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   *
   * @return the authorised function codes for the user
   */
  List<String> getFunctionCodesForUser(Long userDirectoryId, String username)
    throws UserDirectoryNotFoundException, UserNotFoundException, SecurityServiceException;

  /**
   * Retrieve all the authorised functions.
   *
   * @return the authorised functions
   */
  List<Function> getFunctions()
    throws SecurityServiceException;

  /**
   * Retrieve the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param groupName       the name identifying the group
   *
   * @return the group
   */
  Group getGroup(Long userDirectoryId, String groupName)
    throws UserDirectoryNotFoundException, GroupNotFoundException, SecurityServiceException;

  /**
   * Retrieve the names identifying the groups for the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   *
   * @return the names identifying the groups for the user
   */
  List<String> getGroupNamesForUser(Long userDirectoryId, String username)
    throws UserDirectoryNotFoundException, UserNotFoundException, SecurityServiceException;

  /**
   * Retrieve all the groups.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the groups
   */
  List<Group> getGroups(Long userDirectoryId)
    throws UserDirectoryNotFoundException, SecurityServiceException;

  /**
   * Retrieve the groups for the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   *
   * @return the groups for the user
   */
  List<Group> getGroupsForUser(Long userDirectoryId, String username)
    throws UserDirectoryNotFoundException, UserNotFoundException, SecurityServiceException;

  /**
   * Retrieve the number of groups
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the number of groups
   */
  long getNumberOfGroups(Long userDirectoryId)
    throws UserDirectoryNotFoundException, SecurityServiceException;

  /**
   * Retrieve the number of organizations
   *
   * @return the number of organizations
   */
  long getNumberOfOrganizations()
    throws SecurityServiceException;

  /**
   * Retrieve the number of organizations
   *
   * @param filter the optional filter to apply to the organizations
   *
   * @return the number of organizations
   */
  long getNumberOfOrganizations(String filter)
    throws SecurityServiceException;

  /**
   * Retrieve the number of user directories
   *
   * @return the number of user directories
   */
  long getNumberOfUserDirectories()
    throws SecurityServiceException;

  /**
   * Retrieve the number of user directories
   *
   * @param filter the optional filter to apply to the user directories
   *
   * @return the number of user directories
   */
  long getNumberOfUserDirectories(String filter)
    throws SecurityServiceException;

  /**
   * Retrieve the number of users.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the number of users
   */
  long getNumberOfUsers(Long userDirectoryId)
    throws UserDirectoryNotFoundException, SecurityServiceException;

  /**
   * Retrieve the number of users.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param filter          the optional filter to apply to the users
   *
   * @return the number of users
   */
  long getNumberOfUsers(Long userDirectoryId, String filter)
    throws UserDirectoryNotFoundException, SecurityServiceException;

  /**
   * Retrieve the organization.
   *
   * @param organizationId the ID used to uniquely identify the organization
   *
   * @return the organization
   */
  Organization getOrganization(Long organizationId)
    throws OrganizationNotFoundException, SecurityServiceException;

  /**
   * Retrieve the IDs used to uniquely identify the organizations the user directory is associated
   * with.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the IDs used to uniquely identify the organizations the user directory is associated
   *         with
   */
  List<Long> getOrganizationIdsForUserDirectory(Long userDirectoryId)
    throws UserDirectoryNotFoundException, SecurityServiceException;

  /**
   * Retrieve the organizations.
   *
   * @return the organizations
   */
  List<Organization> getOrganizations()
    throws SecurityServiceException;

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
  List<Organization> getOrganizations(String filter, SortDirection sortDirection,
      Integer pageIndex, Integer pageSize)
    throws SecurityServiceException;

  /**
   * Retrieve the organizations the user directory is associated with.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the organizations the user directory is associated with
   */
  List<Organization> getOrganizationsForUserDirectory(Long userDirectoryId)
    throws UserDirectoryNotFoundException, SecurityServiceException;

  /**
   * Retrieve the codes for the roles that the user has been assigned.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   *
   * @return the codes for the roles that the user has been assigned
   */
  List<String> getRoleCodesForUser(Long userDirectoryId, String username)
    throws UserDirectoryNotFoundException, UserNotFoundException, SecurityServiceException;

  /**
   * Retrieve the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   *
   * @return the user
   */
  User getUser(Long userDirectoryId, String username)
    throws UserDirectoryNotFoundException, UserNotFoundException, SecurityServiceException;

  /**
   * Retrieve the user directories.
   *
   * @return the user directories
   */
  List<UserDirectory> getUserDirectories()
    throws SecurityServiceException;

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
  List<UserDirectory> getUserDirectories(String filter, SortDirection sortDirection,
      Integer pageIndex, Integer pageSize)
    throws SecurityServiceException;

  /**
   * Retrieve the user directories the organization is associated with.
   *
   * @param organizationId the ID used to uniquely identify the organization
   *
   * @return the user directories the organization is associated with
   */
  List<UserDirectory> getUserDirectoriesForOrganization(Long organizationId)
    throws OrganizationNotFoundException, SecurityServiceException;

  /**
   * Retrieve the user directory.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the user directory
   */
  UserDirectory getUserDirectory(Long userDirectoryId)
    throws UserDirectoryNotFoundException, SecurityServiceException;

  /**
   * Retrieve the ID used to uniquely identify the user directory that the user with the specified
   * username is associated with.
   *
   * @param username the username identifying the user
   *
   * @return the ID used to uniquely identify the user directory that the user with the specified
   *         username is associated with or <code>null</code> if the user cannot be found
   */
  Long getUserDirectoryIdForUser(String username)
    throws SecurityServiceException;

  /**
   * Retrieve the IDs used to uniquely identify the user directories the organization is associated
   * with.
   *
   * @param organizationId the ID used to uniquely identify the organization
   *
   * @return the IDs used to uniquely identify the user directories the organization is associated
   *         with
   */
  List<Long> getUserDirectoryIdsForOrganization(Long organizationId)
    throws OrganizationNotFoundException, SecurityServiceException;

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
  List<UserDirectorySummary> getUserDirectorySummaries(String filter, SortDirection sortDirection,
      Integer pageIndex, Integer pageSize)
    throws SecurityServiceException;

  /**
   * Retrieve the summaries for the user directories the organization is associated with.
   *
   * @param organizationId the ID used to uniquely identify the organization
   *
   * @return the summaries for the user directories the organization is associated with
   */
  List<UserDirectorySummary> getUserDirectorySummariesForOrganization(Long organizationId)
    throws OrganizationNotFoundException, SecurityServiceException;

  /**
   * Retrieve the user directory type for the user directory.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the user directory type for the user directory
   */
  UserDirectoryType getUserDirectoryTypeForUserDirectory(Long userDirectoryId)
    throws UserDirectoryNotFoundException, UserDirectoryTypeNotFoundException,
        SecurityServiceException;

  /**
   * Retrieve the user directory types.
   *
   * @return the user directory types
   */
  List<UserDirectoryType> getUserDirectoryTypes()
    throws SecurityServiceException;

  /**
   * Retrieve all the users.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return the users
   */
  List<User> getUsers(Long userDirectoryId)
    throws UserDirectoryNotFoundException, SecurityServiceException;

  /**
   * Retrieve the users.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param filter          the optional filter to apply to the users
   * @param sortBy          the optional method used to sort the users e.g. by last name
   * @param sortDirection   the optional sort direction to apply to the users
   * @param pageIndex       the optional page index
   * @param pageSize        the optional page size
   *
   * @return the users
   */
  List<User> getUsers(Long userDirectoryId, String filter, UserSortBy sortBy,
      SortDirection sortDirection, Integer pageIndex, Integer pageSize)
    throws UserDirectoryNotFoundException, SecurityServiceException;

  /**
   * Is the user in the group?
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   * @param groupName       the name identifying the group
   *
   * @return <code>true</code> if the user is a member of the group or <code>false</code>
   *         otherwise
   */
  boolean isUserInGroup(Long userDirectoryId, String username, String groupName)
    throws UserDirectoryNotFoundException, UserNotFoundException, GroupNotFoundException,
        SecurityServiceException;

  /**
   * Reload the user directories.
   */
  void reloadUserDirectories()
    throws SecurityServiceException;

  /**
   * Remove the user from the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param username        the username identifying the user
   * @param groupName       the name identifying the group
   */
  void removeUserFromGroup(Long userDirectoryId, String username, String groupName)
    throws UserDirectoryNotFoundException, UserNotFoundException, GroupNotFoundException,
        SecurityServiceException;

  /**
   * Does the user directory support administering groups.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return <code>true</code> if the user directory supports administering groups or
   *         <code>false</code> otherwise
   */
  boolean supportsGroupAdministration(Long userDirectoryId)
    throws UserDirectoryNotFoundException;

  /**
   * Does the user directory support administering users.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   *
   * @return <code>true</code> if the user directory supports administering users or
   *         <code>false</code> otherwise
   */
  boolean supportsUserAdministration(Long userDirectoryId)
    throws UserDirectoryNotFoundException;

  /**
   * Update the authorised function.
   *
   * @param function the function
   */
  void updateFunction(Function function)
    throws FunctionNotFoundException, SecurityServiceException;

  /**
   * Update the group.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param group           the group
   */
  void updateGroup(Long userDirectoryId, Group group)
    throws UserDirectoryNotFoundException, GroupNotFoundException, SecurityServiceException;

  /**
   * Update the organization.
   *
   * @param organization the organization
   */
  void updateOrganization(Organization organization)
    throws OrganizationNotFoundException, SecurityServiceException;

  /**
   * Update the user.
   *
   * @param userDirectoryId the ID used to uniquely identify the user directory
   * @param user            the user
   * @param expirePassword  expire the user's password as part of the update
   * @param lockUser        lock the user as part of the update
   */
  void updateUser(Long userDirectoryId, User user, boolean expirePassword, boolean lockUser)
    throws UserDirectoryNotFoundException, UserNotFoundException, SecurityServiceException;

  /**
   * Update the user directory.
   *
   * @param userDirectory the user directory
   */
  void updateUserDirectory(UserDirectory userDirectory)
    throws UserDirectoryNotFoundException, SecurityServiceException;
}
