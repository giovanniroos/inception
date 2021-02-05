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

import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {finalize, first, map} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
import {SecurityService} from '../services/security.service';
import {SpinnerService} from '../../layout/services/spinner.service';
import {DialogService} from '../../dialog/services/dialog.service';
import {AccessDeniedError} from '../../core/errors/access-denied-error';
import {ServiceUnavailableError} from '../../core/errors/service-unavailable-error';
import {Error} from '../../core/errors/error';
import {INCEPTION_CONFIG, InceptionConfig} from '../../inception-config';
import {Session} from '../services/session';
import {Tenants} from '../services/tenants';
import {PasswordExpiredError} from '../services/security.service.errors';
import {Tenant} from '../services/tenant';

/**
 * The LoginComponent class implements the login component.
 *
 * @author Marcus Portmann
 */
@Component({
  templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  passwordFormControl: FormControl;

  usernameFormControl: FormControl;

  /**
   * Constructs a new LoginComponent.
   *
   * @param config          The Inception configuration.
   * @param router          The router.
   * @param activatedRoute  The activated route.
   * @param dialogService   The dialog service.
   * @param securityService The security service.
   * @param spinnerService  The spinner service.
   */
  constructor(@Inject(INCEPTION_CONFIG) private config: InceptionConfig, private router: Router,
              private activatedRoute: ActivatedRoute, private dialogService: DialogService,
              private securityService: SecurityService, private spinnerService: SpinnerService) {

    // Initialise the form controls
    this.passwordFormControl = new FormControl(
      !!this.config.prepopulatedLoginPassword ? this.config.prepopulatedLoginPassword : '',
      [Validators.required, Validators.maxLength(100)]);
    this.usernameFormControl = new FormControl(
      !!this.config.prepopulatedLoginUsername ? this.config.prepopulatedLoginUsername : '',
      [Validators.required, Validators.maxLength(100)]);

    // Initialise the form
    this.loginForm = new FormGroup({
      password: this.passwordFormControl,
      username: this.usernameFormControl
    });
  }

  isForgottenPasswordEnabled(): boolean {
    return this.config.forgottenPasswordEnabled;
  }

  forgotPassword(): void {
    this.router.navigate(['forgotten-password'], {
      relativeTo: this.activatedRoute
    });
  }

  login(): void {
    if (this.loginForm.valid) {
      const username = this.usernameFormControl.value;
      const password = this.passwordFormControl.value;

      this.spinnerService.showSpinner();

      this.securityService.login(username, password)
      .pipe(first())
      .subscribe((session: Session | null) => {
        if (session) {
          if (session.hasRole('Administrator')) {
            this.securityService.getTenants()
            .pipe(first(), finalize(() => this.spinnerService.hideSpinner()))
            .subscribe((tenants: Tenants) => {
              if (tenants.total === 1) {
                session.tenant = tenants.tenants[0];
                // noinspection JSIgnoredPromiseFromCall
                this.router.navigate(['/']);
              } else {
                // noinspection JSIgnoredPromiseFromCall
                this.router.navigate(['select-tenant'], {
                  relativeTo: this.activatedRoute,
                  state: {tenants: tenants.tenants}
                });
              }
            }, (error: Error) => {
              // noinspection SuspiciousTypeOfGuard
              if ((error instanceof AccessDeniedError) || (error instanceof ServiceUnavailableError)) {
                // noinspection JSIgnoredPromiseFromCall
                this.router.navigateByUrl('/error/send-error-report', {state: {error}});
              } else {
                this.dialogService.showErrorDialog(error);
              }
            });
          } else {
            this.securityService.getTenantsForUserDirectory(session.userDirectoryId)
            .pipe(first(), finalize(() => this.spinnerService.hideSpinner()))
            .subscribe((tenants: Tenant[]) => {
              if (tenants.length === 1) {
                session.tenant = tenants[0];
                // noinspection JSIgnoredPromiseFromCall
                this.router.navigate(['/']);
              } else {
                // noinspection JSIgnoredPromiseFromCall
                this.router.navigate(['select-tenant'], {
                  relativeTo: this.activatedRoute,
                  state: {tenants}
                });
              }
            }, (error: Error) => {
              // noinspection SuspiciousTypeOfGuard
              if ((error instanceof AccessDeniedError) || (error instanceof ServiceUnavailableError)) {
                // noinspection JSIgnoredPromiseFromCall
                this.router.navigateByUrl('/error/send-error-report', {state: {error}});
              } else {
                this.dialogService.showErrorDialog(error);
              }
            });
          }
        } else {
          this.spinnerService.hideSpinner();

          // noinspection JSIgnoredPromiseFromCall
          this.router.navigate(['/']);
        }
      }, (error: Error) => {
        this.spinnerService.hideSpinner();

        // noinspection SuspiciousTypeOfGuard
        if ((error instanceof AccessDeniedError) || (error instanceof ServiceUnavailableError)) {
          // noinspection JSIgnoredPromiseFromCall
          this.router.navigateByUrl('/error/send-error-report', {state: {error}});
        } else if (error instanceof PasswordExpiredError) {
          // noinspection JSIgnoredPromiseFromCall
          this.router.navigate(['expired-password'], {
            relativeTo: this.activatedRoute,
            state: {username}
          });
        } else {
          this.dialogService.showErrorDialog(error);
        }
      });
    }
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
    .pipe(first(), map(() => window.history.state))
    .subscribe((state) => {
      if (state.username) {
        this.usernameFormControl.setValue(state.username);
      }
    });
  }
}
