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

package digital.inception.codes;

//~--- non-JDK imports --------------------------------------------------------

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//~--- JDK imports ------------------------------------------------------------

import java.time.LocalDateTime;

import java.util.Optional;

/**
 * The <code>CodeCategoryRepository</code> interface declares the repository for the
 * <code>CodeCategory</code> domain type.
 *
 * @author Marcus Portmann
 */
public interface CodeCategoryRepository extends JpaRepository<CodeCategory, String>
{
  @Modifying
  @Query("delete from CodeCategory cc where cc.id = :codeCategoryId")
  void deleteById(@Param("codeCategoryId") String codeCategoryId);

  @Query("select cc.data from CodeCategory cc where cc.id = :codeCategoryId")
  Optional<String> getDataById(@Param("codeCategoryId") String codeCategoryId);

  @Query("select cc.name from CodeCategory cc where cc.id = :codeCategoryId")
  Optional<String> getNameById(@Param("codeCategoryId") String codeCategoryId);

  @Query("select cc.updated from CodeCategory cc where cc.id = :codeCategoryId")
  Optional<LocalDateTime> getUpdatedById(@Param("codeCategoryId") String codeCategoryId);

  @Modifying
  @Query("update CodeCategory cc set cc.data = :data, cc.updated = :updated "
      + "where cc.id = :codeCategoryId")
  int setDataAndUpdatedById(@Param("codeCategoryId") String codeCategoryId, @Param(
      "data") String data, @Param("updated") LocalDateTime updated);
}
