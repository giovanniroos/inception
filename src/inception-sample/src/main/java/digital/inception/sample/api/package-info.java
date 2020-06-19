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

@javax.xml.bind.annotation.XmlSchema(namespace = "http://sample.inception.digital",
    elementFormDefault = javax.xml.bind.annotation.XmlNsForm.UNQUALIFIED,
    xmlns = {@javax.xml.bind.annotation.XmlNs(prefix = "inception",
        namespaceURI = "http://inception.digital"),
        @javax.xml.bind.annotation.XmlNs(prefix = "validation",
            namespaceURI = "http://validation.inception.digital"),
        @javax.xml.bind.annotation.XmlNs(prefix = "sample",
            namespaceURI = "http://sample.inception.digital"),})
@javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters({@javax.xml.bind.annotation.adapters
    .XmlJavaTypeAdapter(type = java.time.LocalDateTime.class,
    value = digital.inception.core.xml.LocalDateTimeAdapter.class),
    @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(type = java.time.ZonedDateTime.class,
        value = digital.inception.core.xml.ZonedDateTimeAdapter.class)})
package digital.inception.sample.api;
