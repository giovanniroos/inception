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

package digital.inception.party;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * The <b>SegmentRepository</b> interface declares the repository for the <b>Segment</b> domain
 * type.
 *
 * @author Marcus Portmann
 */
public interface SegmentRepository extends JpaRepository<Segment, SegmentId> {

  /**
   * Retrieve all the segments sorted by locale ID, sort index, and name.
   *
   * @return all the segments sorted by locale ID, sort index, and name.
   */
  @Query("select s from Segment s order by s.localeId, -s.sortIndex DESC, s.name")
  List<Segment> findAll();

  /**
   * Retrieve the segments for the specified locale sorted by locale ID, sort index, and name.
   *
   * @param localeId the Unicode locale identifier for the locale to retrieve the segments for
   * @return the segments for the specified locale sorted by locale ID, sort index, and name
   */
  @Query(
      "select s from Segment s where upper(s.localeId) = upper(:localeId) order by s.localeId, -s.sortIndex DESC, s.name")
  List<Segment> findByLocaleIdIgnoreCase(String localeId);
}
