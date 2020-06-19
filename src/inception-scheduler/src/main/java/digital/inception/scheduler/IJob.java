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

/**
 * The <code>IJob</code> interface defines the functionality that must be provided by all jobs.
 *
 * @author Marcus Portmann
 */
public interface IJob {

  /**
   * Execute the job.
   *
   * @param context the job execution context
   */
  void execute(JobExecutionContext context)
      throws JobExecutionFailedException;
}
