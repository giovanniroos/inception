/*
 * Copyright 2021 Marcus Portmann
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

import {AssociationProperty} from "./association-property";

/**
 * The Association class holds the information for an association between two parties.
 *
 * @author Marcus Portmann
 */
export class Association {

  /**
   * The date the association is effective from.
   */
  effectiveFrom?: Date;

  /**
   * The date the association is effective to.
   */
  effectiveTo?: Date;

  /**
   * The Universally Unique Identifier (UUID) for the first party in the association.
   */
  firstPartyId: string;

  /**
   * The Universally Unique Identifier (UUID) for the association.
   */
  id: string;

  /**
   * The properties for the association.
   */
  properties: AssociationProperty[];

  /**
   * The Universally Unique Identifier (UUID) for the second party in the association.
   */
  secondPartyId: string;

  /**
   * The Universally Unique Identifier (UUID) for the tenant the association is associated with.
   */
  tenantId: string;

  /**
   * The code for the association type.
   */
  type: string;

  /**
   * Constructs a new Association.
   *
   * @param id            The Universally Unique Identifier (UUID) for the association.
   * @param tenantId      The Universally Unique Identifier (UUID) for the tenant the association is
   *                      associated with.
   * @param type          The code for the association type.
   * @param firstPartyId  The Universally Unique Identifier (UUID) for the first party in the
   *                      association.
   * @param secondPartyId The Universally Unique Identifier (UUID) for the second party in the
   *                      association.
   * @param properties    The properties for the association.
   * @param effectiveFrom The date the association is effective from.
   * @param effectiveTo   The date the association is effective to.
   */
  constructor(id: string, tenantId: string, type: string, firstPartyId: string,
              secondPartyId: string, properties?: AssociationProperty[], effectiveFrom?: Date,
              effectiveTo?: Date) {
    this.id = id;
    this.tenantId = tenantId;
    this.type = type;
    this.firstPartyId = firstPartyId;
    this.secondPartyId = secondPartyId;
    this.properties = (!properties) ? [] : properties;
    this.effectiveFrom = effectiveFrom;
    this.effectiveTo = effectiveTo;
  }
}
