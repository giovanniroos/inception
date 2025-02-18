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

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * The <b>PartyRepository</b> interface declares the repository for the <b>Party</b> domain type.
 *
 * @author Marcus Portmann
 */
public interface PartyRepository extends JpaRepository<Party, UUID> {

  /**
   * Delete the party.
   *
   * @param tenantId the ID for the tenant
   * @param id the ID for the party
   */
  void deleteByTenantIdAndId(UUID tenantId, UUID id);

  /**
   * Check whether the party exists.
   *
   * @param tenantId the ID for the tenant
   * @param id the ID for the party
   * @return <b>true</b> if the party exists or <b>false</b> otherwise
   */
  boolean existsByTenantIdAndId(UUID tenantId, UUID id);

  @Query("select p from Party p")
  Page<Party> findAll(Pageable pageable);

  /**
   * Retrieve the parties for the tenant.
   *
   * @param tenantId the ID for the tenant
   * @param pageable the pagination information
   * @return the parties for the tenant
   */
  Page<Party> findByTenantId(UUID tenantId, Pageable pageable);

  /**
   * Retrieve the party.
   *
   * @param tenantId the ID for the tenant
   * @param id the ID for the party
   * @return an Optional containing the party or an empty Optional if the party could not be found
   */
  Optional<Party> findByTenantIdAndId(UUID tenantId, UUID id);

  /**
   * Retrieve the filtered parties for the tenant.
   *
   * @param tenantId the ID for the tenant
   * @param filter the filter to apply to the parties
   * @param pageable the pagination information
   * @return the filtered parties for the tenant
   */
  @Query(
      "select p from Party p where (p.tenantId = :tenantId) and (lower(p.name) like lower(:filter))")
  Page<Party> findFiltered(
      @Param("tenantId") UUID tenantId, @Param("filter") String filter, Pageable pageable);

  /**
   * Retrieve the ID for the tenant the party is associated with.
   *
   * @param partyId the ID for the party
   * @return an Optional containing the ID for the tenant the party is associated with or an empty
   *     Optional if the party could not be found
   */
  @Query("select p.tenantId from Party p where p.id = :partyId")
  Optional<UUID> getTenantIdByPartyId(@Param("partyId") UUID partyId);

  /**
   * Retrieve the party type for the party.
   *
   * @param tenantId the ID for the tenant
   * @param partyId the ID for the party
   * @return an Optional containing the party type for the party or an empty Optional if the party
   *     could not be found
   */
  @Query("select p.type from Party p where p.tenantId = :tenantId and p.id = :partyId")
  Optional<PartyType> getTypeByTenantIdAndPartyId(
      @Param("tenantId") UUID tenantId, @Param("partyId") UUID partyId);
}
