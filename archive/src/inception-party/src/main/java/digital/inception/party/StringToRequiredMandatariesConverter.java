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

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

/**
 * The <b>StringToRequiredMandatariesConverter</b> class implements the Spring converter that
 * converts a <b>String</b> type into a <b>RequiredMandataries</b> type.
 *
 * @author Marcus Portmann
 */
@Component
@ReadingConverter
public class StringToRequiredMandatariesConverter
    implements Converter<String, RequiredMandataries> {

  /** Constructs a new <b>StringToRequiredMandatariesConverter</b>. */
  public StringToRequiredMandatariesConverter() {}

  @Override
  public RequiredMandataries convert(String source) {
    return RequiredMandataries.fromCode(source);
  }
}
