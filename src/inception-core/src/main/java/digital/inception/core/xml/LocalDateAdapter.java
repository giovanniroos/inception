/*
 * Copyright 2020 Marcus Portmann
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

package digital.inception.core.xml;

// ~--- non-JDK imports --------------------------------------------------------

import digital.inception.core.util.ISO8601Util;
import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

// ~--- JDK imports ------------------------------------------------------------

/**
 * The <code>LocalDateAdapter</code> class implements a JAXB 2.0 adapter used to convert between
 * <code>String</code> and <code>LocalDate</code> types. <br>
 * Can be used when customizing XML Schema to Java Representation Binding (XJC).
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

  /**
   * Marshals the <code>java.time.LocalDate</code> value as an ISO8601Util string.
   *
   * @param value the value to marshal
   *
   * @return the <code>java.time.LocalDate</code> value as an ISO8601Util string
   */
  @Override
  public String marshal(LocalDate value) {
    if (value == null) {
      return null;
    }

    return ISO8601Util.fromLocalDate(value);
  }

  /**
   * Unmarshals the ISO8601Util string value as a <code>java.time.LocalDate</code>.
   *
   * @param value the ISO8601Util string value
   *
   * @return the ISO8601Util string value as a <code>java.time.LocalDate</code>
   */
  @Override
  public LocalDate unmarshal(String value) {
    if (value == null) {
      return null;
    }

    try {
      return ISO8601Util.toLocalDate(value);
    } catch (Throwable e) {
      throw new RuntimeException("Failed to parse the xs:date value (" + value + ")");
    }
  }
}
