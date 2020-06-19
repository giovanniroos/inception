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

package digital.inception.scheduler;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

/**
 * The <code>JobParameterId</code> class implements the ID class for the <code>JobParameter</code>
 * class.
 *
 * @author Marcus Portmann
 */
@SuppressWarnings({"unused"})
public class JobParameterId
    implements Serializable {

  /**
   * The job the job parameter is associated with.
   */
  private Job job;

  /**
   * The name of the job parameter.
   */
  private String name;

  /**
   * Constructs a new <code>JobParameterId</code>.
   */
  public JobParameterId() {
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param object the reference object with which to compare
   *
   * @return <code>true</code> if this object is the same as the object argument otherwise
   * <code>false</code>
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (object == null) {
      return false;
    }

    if (getClass() != object.getClass()) {
      return false;
    }

    JobParameterId other = (JobParameterId) object;

    return (job != null)
        && (job.getId() != null)
        && (other.job != null)
        && (other.job.getId() != null)
        && job.getId().equals(other.job.getId())
        && name.equals(other.name);
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for the object
   */
  @Override
  public int hashCode() {
    return (((job == null) || (job.getId() == null))
        ? 0
        : job.getId().hashCode()) + ((name == null)
        ? 0
        : name.hashCode());
  }
}
