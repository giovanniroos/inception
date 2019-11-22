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

import {AfterViewInit, Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogService} from '../../services/dialog/dialog.service';
import {SpinnerService} from '../../services/layout/spinner.service';
import {I18n} from '@ngx-translate/i18n-polyfill';
import {Error} from '../../errors/error';
import {finalize, first} from 'rxjs/operators';
import {SystemUnavailableError} from '../../errors/system-unavailable-error';
import {AccessDeniedError} from '../../errors/access-denied-error';
import {AdminContainerView} from '../../components/layout/admin-container-view';
import {BackNavigation} from '../../components/layout/back-navigation';
import {Job} from '../../services/scheduler/job';
import {SchedulerService} from '../../services/scheduler/scheduler.service';
import {SchedulerServiceError} from '../../services/scheduler/scheduler.service.errors';
import {JobParameter} from '../../services/scheduler/job-parameter';
import {JobStatus} from '../../services/scheduler/job-status';
import {JobParameterDialogComponent, JobParameterDialogData} from './job-parameter-dialog.component';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';

/**
 * The EditJobComponent class implements the edit job component.
 *
 * @author Marcus Portmann
 */
@Component({
  templateUrl: 'edit-job.component.html',
  styleUrls: ['edit-job.component.css'],
})
export class EditJobComponent extends AdminContainerView implements AfterViewInit {

  JobStatus = JobStatus;

  editJobForm: FormGroup;

  enabledFormControl: FormControl;

  idFormControl: FormControl;

  jobClassFormControl: FormControl;

  jobParameters: JobParameter[] = [];

  jobStatuses: JobStatus[] = [JobStatus.Unscheduled, JobStatus.Scheduled, JobStatus.Executing, JobStatus.Executed, JobStatus.Aborted,
    JobStatus.Failed, JobStatus.OnceOff, JobStatus.Unknown
  ];

  job?: Job;

  nameFormControl: FormControl;

  schedulingPatternFormControl: FormControl;

  statusFormControl: FormControl;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private i18n: I18n,
              private schedulerService: SchedulerService, private dialogService: DialogService, private spinnerService: SpinnerService,
              private matDialog: MatDialog) {
    super();

    // Initialise the form controls
    this.enabledFormControl = new FormControl(true, [Validators.required]);
    this.idFormControl = new FormControl({
      value: '',
      disabled: true
    }, [Validators.required]);
    this.jobClassFormControl = new FormControl('', [Validators.required, Validators.maxLength(1000)]);
    this.nameFormControl = new FormControl('', [Validators.required, Validators.maxLength(100)]);
    this.schedulingPatternFormControl = new FormControl('0 * * * *', [Validators.required, Validators.maxLength(100), Validators.pattern(
      '(((([*])|(((([0-5])?[0-9])((-(([0-5])?[0-9])))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?))(,(((([*])|(((([0-5])?[0-9])((-(([0-5])?[0-9])))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?)))* (((([*])|(((((([0-1])?[0-9]))|(([2][0-3])))((-(((([0-1])?[0-9]))|(([2][0-3])))))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?))(,(((([*])|(((((([0-1])?[0-9]))|(([2][0-3])))((-(((([0-1])?[0-9]))|(([2][0-3])))))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?)))* (((((((([*])|(((((([1-2])?[0-9]))|(([3][0-1]))|(([1-9])))((-(((([1-2])?[0-9]))|(([3][0-1]))|(([1-9])))))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?))|(L)|(((((([1-2])?[0-9]))|(([3][0-1]))|(([1-9])))W))))(,(((((([*])|(((((([1-2])?[0-9]))|(([3][0-1]))|(([1-9])))((-(((([1-2])?[0-9]))|(([3][0-1]))|(([1-9])))))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?))|(L)|(((((([1-2])?[0-9]))|(([3][0-1]))|(([1-9])))W)))))*)|([?])) (((([*])|((((([1-9]))|(([1][0-2])))((-((([1-9]))|(([1][0-2])))))?))|((((JAN)|(FEB)|(MAR)|(APR)|(MAY)|(JUN)|(JUL)|(AUG)|(SEP)|(OKT)|(NOV)|(DEC))((-((JAN)|(FEB)|(MAR)|(APR)|(MAY)|(JUN)|(JUL)|(AUG)|(SEP)|(OKT)|(NOV)|(DEC))))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?))(,(((([*])|((((([1-9]))|(([1][0-2])))((-((([1-9]))|(([1][0-2])))))?))|((((JAN)|(FEB)|(MAR)|(APR)|(MAY)|(JUN)|(JUL)|(AUG)|(SEP)|(OKT)|(NOV)|(DEC))((-((JAN)|(FEB)|(MAR)|(APR)|(MAY)|(JUN)|(JUL)|(AUG)|(SEP)|(OKT)|(NOV)|(DEC))))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?)))* (((((((([*])|((([0-6])((-([0-6])))?))|((((SUN)|(MON)|(TUE)|(WED)|(THU)|(FRI)|(SAT))((-((SUN)|(MON)|(TUE)|(WED)|(THU)|(FRI)|(SAT))))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?))|((([0-6])L))|(W)|(([#][1-5]))))(,(((((([*])|((([0-6])((-([0-6])))?))|((((SUN)|(MON)|(TUE)|(WED)|(THU)|(FRI)|(SAT))((-((SUN)|(MON)|(TUE)|(WED)|(THU)|(FRI)|(SAT))))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?))|((([0-6])L))|(W)|(([#][1-5])))))*)|([?]))((( (((([*])|((([1-2][0-9][0-9][0-9])((-([1-2][0-9][0-9][0-9])))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?))(,(((([*])|((([1-2][0-9][0-9][0-9])((-([1-2][0-9][0-9][0-9])))?)))((/(([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?([0-9])?[0-9])))?)))*))?)')
    ]);
    this.statusFormControl = new FormControl(JobStatus.Unscheduled, [Validators.required]);

    // Initialise the form
    this.editJobForm = new FormGroup({
      enabled: this.enabledFormControl,
      id: this.idFormControl,
      jobClass: this.jobClassFormControl,
      name: this.nameFormControl,
      schedulingPattern: this.schedulingPatternFormControl,
      status: this.statusFormControl
    });
  }

  get backNavigation(): BackNavigation {
    return new BackNavigation(this.i18n({
      id: '@@scheduler_edit_job_component_back_title',
      value: 'Jobs'
    }), ['../..'], {relativeTo: this.activatedRoute});
  }

  get title(): string {
    return this.i18n({
      id: '@@scheduler_edit_job_component_title',
      value: 'Edit Job'
    });
  }

  cancel(): void {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['../..'], {relativeTo: this.activatedRoute});
  }

  deleteJobParameter(existingJobParameter: JobParameter): void {
    this.jobParameters.forEach((jobParameter, index) => {
      if (jobParameter.name === existingJobParameter.name) {
        this.jobParameters.splice(index, 1);
      }
    });
  }

  editJobParameter(existingJobParameter: JobParameter): void {
    const data: JobParameterDialogData = {
      name: existingJobParameter.name,
      readonlyName: true,
      value: existingJobParameter.value
    };

    const dialogRef: MatDialogRef<JobParameterDialogComponent, JobParameter> = this.matDialog.open(JobParameterDialogComponent, {
      restoreFocus: false,
      data
    });

    dialogRef.afterClosed()
      .pipe(first())
      .subscribe((jobParameter: JobParameter | undefined) => {
        if (jobParameter) {
          for (const aJobParameter of this.jobParameters) {
            if (aJobParameter.name === jobParameter.name) {
              aJobParameter.value = jobParameter.value;
              return;
            }
          }
        }
      });
  }

  newJobParameter(): void {
    const data: JobParameterDialogData = {
      name: '',
      value: ''
    };

    const dialogRef: MatDialogRef<JobParameterDialogComponent, JobParameter> = this.matDialog.open(JobParameterDialogComponent, {
      restoreFocus: false,
      data
    });

    dialogRef.afterClosed()
      .pipe(first())
      .subscribe((jobParameter: JobParameter | undefined) => {
        if (jobParameter) {
          for (const aJobParameter of this.jobParameters) {
            if (aJobParameter.name === jobParameter.name) {
              this.dialogService.showErrorDialog(new Error(this.i18n({
                id: '@@scheduler_new_job_component_the_job_parameter_already_exists',
                value: 'The job parameter already exists.'
              })));

              return;
            }
          }

          this.jobParameters.push(jobParameter);

          this.jobParameters.sort((a: JobParameter, b: JobParameter) => {
            if ((a.name ? a.name.toLowerCase() : '') < (b.name ? b.name.toLowerCase() : '')) {
              return -1;
            }
            if ((a.name ? a.name.toLowerCase() : '') > (b.name ? b.name.toLowerCase() : '')) {
              return 1;
            }
            return 0;
          });
        }
      });
  }

  ngAfterViewInit(): void {
    // Retrieve the route parameters
    let jobId = this.activatedRoute.snapshot.paramMap.get('jobId');

    if (!jobId) {
      throw(new Error('No jobId route parameter found'));
    }

    jobId = decodeURIComponent(jobId);

    // Retrieve the existing job and initialise the form controls
    this.spinnerService.showSpinner();

    this.schedulerService.getJob(jobId)
      .pipe(first(), finalize(() => this.spinnerService.hideSpinner()))
      .subscribe((job: Job) => {
        this.job = job;
        this.enabledFormControl.setValue(job.enabled);
        this.idFormControl.setValue(job.id);
        this.jobClassFormControl.setValue(job.jobClass);
        this.nameFormControl.setValue(job.name);
        this.schedulingPatternFormControl.setValue(job.schedulingPattern);
        this.statusFormControl.setValue(job.status);
        this.jobParameters = job.parameters;
      }, (error: Error) => {
        // noinspection SuspiciousTypeOfGuard
        if ((error instanceof SchedulerServiceError) || (error instanceof AccessDeniedError) || (error instanceof SystemUnavailableError)) {
          // noinspection JSIgnoredPromiseFromCall
          this.router.navigateByUrl('/error/send-error-report', {state: {error}});
        } else {
          this.dialogService.showErrorDialog(error);
        }
      });
  }

  ok(): void {
    if (this.job && this.editJobForm.valid) {
      this.job.enabled = this.enabledFormControl.value;
      this.job.id = this.idFormControl.value;
      this.job.jobClass = this.jobClassFormControl.value;
      this.job.name = this.nameFormControl.value;
      this.job.parameters = this.jobParameters;
      this.job.schedulingPattern = this.schedulingPatternFormControl.value;
      this.job.status = this.statusFormControl.value;

      this.spinnerService.showSpinner();

      this.schedulerService.updateJob(this.job)
        .pipe(first(), finalize(() => this.spinnerService.hideSpinner()))
        .subscribe(() => {
          // noinspection JSIgnoredPromiseFromCall
          this.router.navigate(['../..'], {relativeTo: this.activatedRoute});
        }, (error: Error) => {
          // noinspection SuspiciousTypeOfGuard
          if ((error instanceof SchedulerServiceError) || (error instanceof AccessDeniedError) ||
            (error instanceof SystemUnavailableError)) {
            // noinspection JSIgnoredPromiseFromCall
            this.router.navigateByUrl('/error/send-error-report', {state: {error}});
          } else {
            this.dialogService.showErrorDialog(error);
          }
        });
    }
  }
}
