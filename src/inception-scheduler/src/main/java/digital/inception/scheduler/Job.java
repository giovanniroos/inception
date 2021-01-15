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

// ~--- non-JDK imports --------------------------------------------------------

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import digital.inception.core.xml.LocalDateTimeAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

// ~--- JDK imports ------------------------------------------------------------

/**
 * The <code>Job</code> class holds the information for a job.
 *
 * @author Marcus Portmann
 */
@Schema(description = "A scheduled job")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "id",
  "name",
  "schedulingPattern",
  "jobClass",
  "enabled",
  "status",
  "executionAttempts",
  "lockName",
  "lastExecuted",
  "nextExecution",
  "parameters"
})
@XmlRootElement(name = "Job", namespace = "http://scheduler.inception.digital")
@XmlType(
    name = "Job",
    namespace = "http://scheduler.inception.digital",
    propOrder = {
      "id",
      "name",
      "schedulingPattern",
      "jobClass",
      "enabled",
      "status",
      "executionAttempts",
      "lockName",
      "lastExecuted",
      "nextExecution",
      "parameters"
    })
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(schema = "scheduler", name = "jobs")
@SuppressWarnings({"unused", "WeakerAccess"})
public class Job implements Serializable {

  private static final long serialVersionUID = 1000000;

  /** The parameters for the job. */
  @Schema(description = "The parameters for the job")
  @JsonProperty
  @JsonManagedReference
  @XmlElementWrapper(name = "Parameters")
  @XmlElement(name = "Parameter")
  @Valid
  @OneToMany(
      mappedBy = "job",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  private final Set<JobParameter> parameters = new HashSet<>();

  /** The date and time the job was created. */
  @JsonIgnore
  @XmlTransient
  @CreationTimestamp
  @Column(name = "created", nullable = false, updatable = false)
  private LocalDateTime created;

  /** Is the job enabled for execution? */
  @Schema(description = "Is the job enabled for execution", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Enabled", required = true)
  @NotNull
  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  /** The number of times the current execution of the job has been attempted. */
  @Schema(description = "The number of times the current execution of the job has been attempted")
  @JsonProperty
  @XmlElement(name = "ExecutionAttempts")
  @Column(name = "execution_attempts")
  private Integer executionAttempts;

  /** The ID for the job. */
  @Schema(description = "The ID for the job", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Id", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  /** The fully qualified name of the Java class that implements the job. */
  @Schema(
      description = "The fully qualified name of the Java class that implements the job",
      required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "JobClass", required = true)
  @NotNull
  @Size(min = 1, max = 1000)
  @Column(name = "job_class", nullable = false, length = 1000)
  private String jobClass;

  /** The date and time the job was last executed. */
  @Schema(description = "The date and time the job was last executed")
  @JsonProperty
  @XmlElement(name = "LastExecuted")
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  @XmlSchemaType(name = "dateTime")
  @Column(name = "last_executed")
  private LocalDateTime lastExecuted;

  /** The name of the entity that has locked the job for execution. */
  @Schema(description = "The name of the entity that has locked the job for execution")
  @XmlElement(name = "LockName")
  @Size(min = 1, max = 100)
  @Column(name = "lock_name", length = 100)
  private String lockName;

  /** The name of the job. */
  @Schema(description = "The name of the job", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Name", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  /** The date and time when the job will next be executed. */
  @Schema(description = "The date and time when the job will next be executed")
  @JsonProperty
  @XmlElement(name = "NextExecution")
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  @XmlSchemaType(name = "dateTime")
  @Column(name = "next_execution")
  private LocalDateTime nextExecution;

  /** The cron-style scheduling pattern for the job. */
  @Schema(description = "The cron-style scheduling pattern for the job", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "SchedulingPattern", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "scheduling_pattern", nullable = false, length = 100)
  private String schedulingPattern;

  /** The status of the job. */
  @Schema(description = "The status of the job", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Status", required = true)
  @NotNull
  @Column(name = "status", nullable = false)
  private JobStatus status;

  /** The date and time the job was last updated. */
  @JsonIgnore
  @XmlTransient
  @UpdateTimestamp
  @Column(name = "updated", insertable = false)
  private LocalDateTime updated;

  /** Constructs a new <code>Job</code>. */
  public Job() {}

  /**
   * Constructs a new <code>Job</code>.
   *
   * @param id the ID for the job
   * @param name the name of the job
   * @param schedulingPattern the cron-style scheduling pattern for the job
   * @param jobClass the fully qualified name of the Java class that implements the job
   * @param enabled is the job enabled for execution
   * @param status the status of the job
   * @param executionAttempts the number of times the current execution of the job has been
   *     attempted
   * @param lockName the name of the entity that has locked the job for execution
   * @param lastExecuted the date and time the job was last executed
   * @param nextExecution the date and time when the job will next be executed
   */
  public Job(
      String id,
      String name,
      String schedulingPattern,
      String jobClass,
      boolean enabled,
      JobStatus status,
      int executionAttempts,
      String lockName,
      LocalDateTime lastExecuted,
      LocalDateTime nextExecution) {
    this.id = id;
    this.name = name;
    this.schedulingPattern = schedulingPattern;
    this.jobClass = jobClass;
    this.enabled = enabled;
    this.status = status;
    this.executionAttempts = executionAttempts;
    this.lockName = lockName;
    this.lastExecuted = lastExecuted;
    this.nextExecution = nextExecution;
  }

  /**
   * Add the parameter for the job.
   *
   * @param parameter the parameter
   */
  public void addParameter(JobParameter parameter) {
    parameter.setJob(this);

    this.parameters.add(parameter);
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param object the reference object with which to compare
   * @return <code>true</code> if this object is the same as the object argument otherwise <code>
   * false</code>
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

    Job other = (Job) object;

    return Objects.equals(id, other.id);
  }

  /**
   * Returns the date and time the job was created.
   *
   * @return the date and time the job was created
   */
  public LocalDateTime getCreated() {
    return created;
  }

  /**
   * Returns the number of times the current execution of the job has been attempted.
   *
   * @return the number of times the current execution of the job has been attempted
   */
  public Integer getExecutionAttempts() {
    return executionAttempts;
  }

  /**
   * Returns the ID for the job.
   *
   * @return the ID for the job
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the fully qualified name of the Java class that implements the job.
   *
   * @return the fully qualified name of the Java class that implements the job
   */
  public String getJobClass() {
    return jobClass;
  }

  /**
   * Returns date and time the job was last executed.
   *
   * @return the date and time the job was last executed
   */
  public LocalDateTime getLastExecuted() {
    return lastExecuted;
  }

  /**
   * Returns the name of the entity that has locked the job for execution.
   *
   * @return the name of the entity that has locked the job for execution
   */
  public String getLockName() {
    return lockName;
  }

  /**
   * Returns the name of the job.
   *
   * @return the name of the job
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the date and time when the job will next be executed.
   *
   * @return the date and time when the job will next be executed
   */
  public LocalDateTime getNextExecution() {
    return nextExecution;
  }

  /**
   * Returns the parameters for the job.
   *
   * @return the parameters for the job
   */
  public Set<JobParameter> getParameters() {
    return parameters;
  }

  /**
   * Returns the cron-style scheduling pattern for the job.
   *
   * @return the cron-style scheduling pattern for the job
   */
  public String getSchedulingPattern() {
    return schedulingPattern;
  }

  /**
   * Returns the status of the job.
   *
   * @return the status of the job
   */
  public JobStatus getStatus() {
    return status;
  }

  /**
   * Returns the date and time the job was last updated.
   *
   * @return the date and time the job was last updated
   */
  public LocalDateTime getUpdated() {
    return updated;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for the object
   */
  @Override
  public int hashCode() {
    return (id == null) ? 0 : id.hashCode();
  }

  /** Increment the number of execution attempts for the job. */
  public void incrementExecutionAttempts() {
    if (executionAttempts == null) {
      executionAttempts = 1;
    } else {
      executionAttempts++;
    }
  }

  /**
   * Returns whether the job is enabled for execution.
   *
   * @return <code>true</code> if the job is enabled for execution or <code>false</code> otherwise
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Remove the parameter for the job.
   *
   * @param parameterName the name of the parameter
   */
  public void removeParameter(String parameterName) {
    for (JobParameter parameter : parameters) {
      if (parameterName.equalsIgnoreCase(parameter.getName())) {
        this.parameters.remove(parameter);

        return;
      }
    }
  }

  /**
   * Set whether the job is enabled for execution.
   *
   * @param enabled <code>true</code> if the job is enabled for execution or <code>false</code>
   *     otherwise
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Set the number of times the current execution of the job has been attempted.
   *
   * @param executionAttempts the number of times the current execution of the job has been
   *     attempted
   */
  public void setExecutionAttempts(Integer executionAttempts) {
    this.executionAttempts = executionAttempts;
  }

  /**
   * Set the ID for the job.
   *
   * @param id the ID for the scheduled job
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Set the fully qualified name of the Java class that implements the job.
   *
   * @param jobClass the fully qualified name of the Java class that implements the job
   */
  public void setJobClass(String jobClass) {
    this.jobClass = jobClass;
  }

  /**
   * Set the date and time the job was last executed.
   *
   * @param lastExecuted the date and time the job was last executed
   */
  public void setLastExecuted(LocalDateTime lastExecuted) {
    this.lastExecuted = lastExecuted;
  }

  /**
   * Set the name of the entity that has locked the job for execution.
   *
   * @param lockName the name of the entity that has locked the job for execution
   */
  public void setLockName(String lockName) {
    this.lockName = lockName;
  }

  /**
   * Set the name of the job.
   *
   * @param name the name of the job
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set the date and time when the job will next be executed.
   *
   * @param nextExecution the date and time when the job will next be executed
   */
  public void setNextExecution(LocalDateTime nextExecution) {
    this.nextExecution = nextExecution;
  }

  /**
   * Set the parameters for the job.
   *
   * @param parameters the parameters for the job
   */
  public void setParameters(Set<JobParameter> parameters) {
    this.parameters.clear();
    this.parameters.addAll(parameters);
  }

  /**
   * Set the cron-style scheduling pattern for the job.
   *
   * @param schedulingPattern the cron-style scheduling pattern for the job
   */
  public void setSchedulingPattern(String schedulingPattern) {
    this.schedulingPattern = schedulingPattern;
  }

  /**
   * Set the status of the job.
   *
   * @param status the status of the job
   */
  public void setStatus(JobStatus status) {
    this.status = status;
  }
}
