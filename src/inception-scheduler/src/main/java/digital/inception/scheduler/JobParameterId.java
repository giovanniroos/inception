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

package digital.inception.scheduler;



import java.io.Serializable;
import java.util.Objects;

/**
 * The <b>JobParameterId</b> class implements the ID class for the <b>JobParameter</b>
 * class.
 *
 * @author Marcus Portmann
 */
@SuppressWarnings({"unused"})
public class JobParameterId implements Serializable {

  /** The job the job parameter is associated with. */
  private Job job;

  /** The name of the job parameter. */
  private String name;

  /** Constructs a new <b>JobParameterId</b>. */
  public JobParameterId() {}

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param object the reference object with which to compare
   * @return <b>true</b> if this object is the same as the object argument otherwise <b>
   * false</b>
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

    return Objects.equals(job, other.job) && Objects.equals(name, other.name);
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for the object
   */
  @Override
  public int hashCode() {
    return (((job == null) || (job.getId() == null)) ? 0 : job.getId().hashCode())
        + ((name == null) ? 0 : name.hashCode());
  }
}
