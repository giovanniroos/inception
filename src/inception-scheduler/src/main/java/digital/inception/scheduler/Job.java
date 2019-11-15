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

//~--- non-JDK imports --------------------------------------------------------

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import digital.inception.core.xml.LocalDateTimeAdapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The <code>Job</code> class holds the information for a job.
 *
 * @author Marcus Portmann
 */
@ApiModel(value = "Job")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name", "schedulingPattern", "jobClass", "enabled", "status",
    "executionAttempts", "lockName", "lastExecuted", "nextExecution" })
@XmlRootElement(name = "Job", namespace = "http://scheduler.inception.digital")
@XmlType(name = "Job", namespace = "http://scheduler.inception.digital",
    propOrder = { "id", "name", "schedulingPattern", "jobClass", "enabled", "status",
        "executionAttempts", "lockName", "lastExecuted", "nextExecution" })
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(schema = "scheduler", name = "jobs")
@SuppressWarnings({ "unused", "WeakerAccess" })
public class Job
  implements Serializable
{
  private static final long serialVersionUID = 1000000;

  /**
   * Is the job enabled for execution?
   */
  @ApiModelProperty(value = "Is the job enabled for execution", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Enabled", required = true)
  @NotNull
  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  /**
   * The number of times the current execution of the job has been attempted.
   */
  @ApiModelProperty(
      value = "The number of times the current execution of the job has been attempted")
  @JsonProperty
  @XmlElement(name = "ExecutionAttempts")
  @Column(name = "execution_attempts")
  private Integer executionAttempts;

  /**
   * The Universally Unique Identifier (UUID) used to uniquely identify the job.
   */
  @ApiModelProperty(
      value = "The Universally Unique Identifier (UUID) used to uniquely identify the job",
      required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Id", required = true)
  @NotNull
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  /**
   * The fully qualified name of the Java class that implements the job.
   */
  @ApiModelProperty(value = "The fully qualified name of the Java class that implements the job",
      required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "JobClass", required = true)
  @NotNull
  @Size(min = 1, max = 1000)
  @Column(name = "job_class", nullable = false, length = 1000)
  private String jobClass;

  /**
   * The date and time the job was last executed.
   */
  @ApiModelProperty(value = "The date and time the job was last executed")
  @JsonProperty
  @XmlElement(name = "LastExecuted")
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  @XmlSchemaType(name = "dateTime")
  @Column(name = "last_executed")
  private LocalDateTime lastExecuted;

  /**
   * The name of the entity that has locked the job for execution.
   */
  @ApiModelProperty(value = "The name of the entity that has locked the job for execution")
  @XmlElement(name = "LockName")
  @Size(min = 1, max = 100)
  @Column(name = "lock_name", length = 100)
  private String lockName;

  /**
   * The name of the job.
   */
  @ApiModelProperty(value = "The name of the job", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Name", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  /**
   * The date and time when the job will next be executed.
   */
  @ApiModelProperty(value = "The date and time when the job will next be executed")
  @JsonProperty
  @XmlElement(name = "NextExecution")
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  @XmlSchemaType(name = "dateTime")
  @Column(name = "next_execution")
  private LocalDateTime nextExecution;

  /**
   * The parameters for the job.
   */
  @ApiModelProperty(value = "The parameters for the job")
  @JsonProperty
  @XmlElementWrapper(name = "Parameters")
  @XmlElement(name = "Parameter")
  @Valid
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  @JoinColumn(name = "job_id", referencedColumnName = "id", insertable = false, updatable = false,
      nullable = false)
  private Set<JobParameter> parameters = new HashSet<>();

  /**
   * The cron-style scheduling pattern for the job.
   */
  @ApiModelProperty(value = "The cron-style scheduling pattern for the job", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "SchedulingPattern", required = true)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "scheduling_pattern", nullable = false, length = 100)
  private String schedulingPattern;

  /**
   * The status of the job.
   */
  @ApiModelProperty(value = "The status of the job", required = true)
  @JsonProperty(required = true)
  @XmlElement(name = "Status", required = true)
  @NotNull
  @Column(name = "status", nullable = false)
  private JobStatus status;

  /**
   * Constructs a new <code>Job</code>.
   */
  public Job() {}

  /**
   * Constructs a new <code>Job</code>.
   *
   * @param id                the Universally Unique Identifier (UUID) used to uniquely identify
   *                          the job
   * @param name              the name of the job
   * @param schedulingPattern the cron-style scheduling pattern for the job
   * @param jobClass          the fully qualified name of the Java class that implements the job
   * @param enabled           is the job enabled for execution
   * @param status            the status of the job
   * @param executionAttempts the number of times the current execution of the job has
   *                          been attempted
   * @param lockName          the name of the entity that has locked the job for execution
   * @param lastExecuted      the date and time the job was last executed
   * @param nextExecution     the date and time when the job will next be executed
   */
  public Job(UUID id, String name, String schedulingPattern, String jobClass, boolean enabled,
      JobStatus status, int executionAttempts, String lockName, LocalDateTime lastExecuted,
      LocalDateTime nextExecution)
  {
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
  public void addParameter(JobParameter parameter)
  {
    parameter.setJob(this);

    this.parameters.add(parameter);
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param object the reference object with which to compare
   *
   * @return <code>true</code> if this object is the same as the object argument otherwise
   *         <code>false</code>
   */
  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }

    if (object == null)
    {
      return false;
    }

    if (getClass() != object.getClass())
    {
      return false;
    }

    Job other = (Job) object;

    return (id != null) && id.equals(other.id);
  }

  /**
   * Returns the number of times the current execution of the job has been attempted.
   *
   * @return the number of times the current execution of the job has been attempted
   */
  public Integer getExecutionAttempts()
  {
    return executionAttempts;
  }

  /**
   * Returns the Universally Unique Identifier (UUID) used to uniquely identify the job.
   *
   * @return the Universally Unique Identifier (UUID) used to uniquely identify the job
   */
  public UUID getId()
  {
    return id;
  }

  /**
   * Returns the fully qualified name of the Java class that implements the job.
   *
   * @return the fully qualified name of the Java class that implements the job
   */
  public String getJobClass()
  {
    return jobClass;
  }

  /**
   * Returns date and time the job was last executed.
   *
   * @return the date and time the job was last executed
   */
  public LocalDateTime getLastExecuted()
  {
    return lastExecuted;
  }

  /**
   * Returns the name of the entity that has locked the job for execution.
   *
   * @return the name of the entity that has locked the job for execution
   */
  public String getLockName()
  {
    return lockName;
  }

  /**
   * Returns the name of the job.
   *
   * @return the name of the job
   */
  public String getName()
  {
    return name;
  }

  /**
   * Returns the date and time when the job will next be executed.
   *
   * @return the date and time when the job will next be executed
   */
  public LocalDateTime getNextExecution()
  {
    return nextExecution;
  }

  /**
   * Returns the parameters for the job.
   *
   * @return the parameters for the job
   */
  public Set<JobParameter> getParameters()
  {
    return parameters;
  }

  /**
   * Returns the cron-style scheduling pattern for the job.
   *
   * @return the cron-style scheduling pattern for the job
   */
  public String getSchedulingPattern()
  {
    return schedulingPattern;
  }

  /**
   * Returns the status of the job.
   *
   * @return the status of the job
   */
  public JobStatus getStatus()
  {
    return status;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for the object
   */
  @Override
  public int hashCode()
  {
    return (id == null)
        ? 0
        : id.hashCode();
  }

  /**
   * Increment the number of execution attempts for the job.
   */
  public void incrementExecutionAttempts()
  {
    if (executionAttempts == null)
    {
      executionAttempts = 1;
    }
    else
    {
      executionAttempts++;
    }
  }

  /**
   * Returns whether the job is enabled for execution.
   *
   * @return <code>true</code> if the job is enabled for execution or <code>false</code> otherwise
   */
  public boolean isEnabled()
  {
    return enabled;
  }

  /**
   * Remove the parameter for the job.
   *
   * @param parameterName the name of the parameter
   */
  public void removeParameter(String parameterName)
  {
    for (JobParameter parameter : parameters)
    {
      if (parameterName.equalsIgnoreCase(parameter.getName()))
      {
        this.parameters.remove(parameter);

        return;
      }
    }
  }

  /**
   * Set whether the job is enabled for execution.
   *
   * @param enabled <code>true</code> if the job is enabled for execution or <code>false</code>
   *                  otherwise
   */
  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }

  /**
   * Set the number of times the current execution of the job has been attempted.
   *
   * @param executionAttempts the number of times the current execution of the job has
   *                          been attempted
   */
  public void setExecutionAttempts(Integer executionAttempts)
  {
    this.executionAttempts = executionAttempts;
  }

  /**
   * Set the Universally Unique Identifier (UUID) used to uniquely identify the job.
   *
   * @param id the Universally Unique Identifier (UUID) used to uniquely identify the scheduled job
   */
  public void setId(UUID id)
  {
    this.id = id;
  }

  /**
   * Set the fully qualified name of the Java class that implements the job.
   *
   * @param jobClass the fully qualified name of the Java class that implements the job
   */
  public void setJobClass(String jobClass)
  {
    this.jobClass = jobClass;
  }

  /**
   * Set the date and time the job was last executed.
   *
   * @param lastExecuted the date and time the job was last executed
   */
  public void setLastExecuted(LocalDateTime lastExecuted)
  {
    this.lastExecuted = lastExecuted;
  }

  /**
   * Set the name of the entity that has locked the job for execution.
   *
   * @param lockName the name of the entity that has locked the job for execution
   */
  public void setLockName(String lockName)
  {
    this.lockName = lockName;
  }

  /**
   * Set the name of the job.
   *
   * @param name the name of the job
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Set the date and time when the job will next be executed.
   *
   * @param nextExecution the date and time when the job will next be executed
   */
  public void setNextExecution(LocalDateTime nextExecution)
  {
    this.nextExecution = nextExecution;
  }

  /**
   * Set the parameters for the job.
   *
   * @param parameters the parameters for the job
   */
  public void setParameters(Set<JobParameter> parameters)
  {
    this.parameters = parameters;
  }

  /**
   * Set the cron-style scheduling pattern for the job.
   *
   * @param schedulingPattern the cron-style scheduling pattern for the job
   */
  public void setSchedulingPattern(String schedulingPattern)
  {
    this.schedulingPattern = schedulingPattern;
  }

  /**
   * Set the status of the job.
   *
   * @param status the status of the job
   */
  public void setStatus(JobStatus status)
  {
    this.status = status;
  }
}
