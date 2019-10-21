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

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;
import java.util.Optional;

/**
 * The <code>GroupRepository</code> interface declares the repository for the
 * <code>Group</code> domain type.
 *
 * @author Marcus Portmann
 */
public interface GroupRepository extends JpaRepository<Group, Long>
{
  @Modifying
  @Query(value = "insert into security.user_to_group_map(user_id, group_id) "
      + "values (:userId, :groupId)",
      nativeQuery = true)
  void addUserToGroup(@Param("groupId") Long groupId, @Param("userId") Long userId);

  long countByUserDirectoryId(Long userDirectoryId);

  @Query("select count(g.id) from Group g where (upper(g.name) like upper(:filter)) and "
      + "g.userDirectoryId = :userDirectoryId")
  long countFiltered(@Param("userDirectoryId") Long userDirectoryId, @Param(
      "filter") String filter);

  @Query("select count(u.id) from Group g join g.users as u where g.userDirectoryId = "
      + ":userDirectoryId and g.id = :groupId and (upper(u.username) like upper(:filter))")
  long countFilteredUsernamesForGroup(@Param("userDirectoryId") Long userDirectoryId, @Param(
      "groupId") Long groupId, @Param("filter") String filter);

  @Query("select count(u.id) from Group g join g.users as u "
      + "where g.userDirectoryId = :userDirectoryId and g.id = :groupId")
  Long countUsernamesForGroup(@Param("userDirectoryId") Long userDirectoryId, @Param(
      "groupId") Long groupId);

  @Query("select count(u.id) from Group g join g.users as u where g.id = :groupId")
  long countUsersById(@Param("groupId") Long groupId);

  @Modifying
  @Query("delete from Group g where g.id = :groupId")
  void deleteById(@Param("groupId") Long groupId);

  @Transactional
  boolean existsByUserDirectoryIdAndNameIgnoreCase(Long userDirectoryId, String name);

  List<Group> findByUserDirectoryId(Long userDirectoryId);

  List<Group> findByUserDirectoryId(Long userDirectoryId, Pageable pageable);

  Optional<Group> findByUserDirectoryIdAndNameIgnoreCase(Long userDirectoryId, String name);

  @Query("select g from Group g where (upper(g.name) like upper(:filter)) and "
      + "g.userDirectoryId = :userDirectoryId")
  List<Group> findFiltered(@Param("userDirectoryId") Long userDirectoryId, @Param(
      "filter") String filter, Pageable pageable);

  @Query("select u.username from Group g join g.users as u where g.userDirectoryId = "
      + ":userDirectoryId and g.id = :groupId and (upper(u.username) like upper(:filter))")
  List<String> getFilteredUsernamesForGroup(@Param("userDirectoryId") Long userDirectoryId, @Param(
      "groupId") Long groupId, @Param("filter") String filter, Pageable pageable);

  @Query("select g.id from Group g where g.userDirectoryId = :userDirectoryId and "
      + "upper(g.name) like upper(:name)")
  Optional<Long> getIdByUserDirectoryIdAndNameIgnoreCase(@Param(
      "userDirectoryId") Long userDirectoryId, @Param("name") String name);

  @Query("select g.name from Group g where g.userDirectoryId = :userDirectoryId")
  List<String> getNamesByUserDirectoryId(@Param("userDirectoryId") Long userDirectoryId);

  @Query("select u.username from Group g join g.users as u "
      + "where g.userDirectoryId = :userDirectoryId and g.id = :groupId")
  List<String> getUsernamesForGroup(@Param("userDirectoryId") Long userDirectoryId, @Param(
      "groupId") Long groupId);

  @Query("select u.username from Group g join g.users as u "
      + "where g.userDirectoryId = :userDirectoryId and g.id = :groupId")
  List<String> getUsernamesForGroup(@Param("userDirectoryId") Long userDirectoryId, @Param(
      "groupId") Long groupId, Pageable pageable);

  @Modifying
  @Query(value = "delete from security.user_to_group_map "
      + "where user_id = :userId and group_id=:groupId",
      nativeQuery = true)
  void removeUserFromGroup(@Param("groupId") Long groupId, @Param("userId") Long userId);
}
