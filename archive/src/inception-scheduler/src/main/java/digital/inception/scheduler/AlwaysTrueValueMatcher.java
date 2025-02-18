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

package digital.inception.scheduler;

/**
 * The <b>AlwaysTrueValueMatcher</b> class implements a <b>ValueMatcher</b> that always returns
 * <b>true</b>.
 *
 * @author Carlo Pelliccia
 * @author Marcus Portmann
 */
public class AlwaysTrueValueMatcher implements ValueMatcher {

  /**
   * Validate the given integer value against a set of rules.
   *
   * @param value the value
   * @return <b>true</b> if the given value matches the rules of the <b>ValueMatcher</b> ,
   *     <b>false</b> otherwise
   */
  public boolean match(int value) {
    return true;
  }
}
