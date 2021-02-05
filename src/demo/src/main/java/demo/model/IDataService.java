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

package demo.model;

import digital.inception.core.service.ServiceUnavailableException;
import digital.inception.core.validation.ValidationError;
import java.util.List;

/**
 * The <b>IDemoService</b> interface defines the functionality that must be provided by a Data
 * Service implementation.
 *
 * @author Marcus Portmann
 */
@SuppressWarnings("unused")
public interface IDataService {

  /**
   * Create the data.
   *
   * @param data the data
   */
  void createData(Data data) throws ServiceUnavailableException;

  /** Create the sample data. */
  void createSampleData() throws ServiceUnavailableException;

  /**
   * Returns the data.
   *
   * @return the data
   */
  List<Data> getAllData() throws ServiceUnavailableException;

  /**
   * Returns the data.
   *
   * @param id the ID for the data
   * @return the data
   */
  Data getData(long id) throws ServiceUnavailableException;

  /**
   * Validate the data.
   *
   * @return the validation errors
   */
  List<ValidationError> validateData(Data data) throws ServiceUnavailableException;
}
