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
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {DialogService} from '../../services/dialog/dialog.service';
import {SpinnerService} from '../../services/layout/spinner.service';
import {I18n} from '@ngx-translate/i18n-polyfill';
import {CodesService} from '../../services/codes/codes.service';
import {Error} from '../../errors/error';
import {Code} from '../../services/codes/code';
import {finalize, first} from 'rxjs/operators';
import {CodesServiceError} from '../../services/codes/codes.service.errors';
import {SystemUnavailableError} from '../../errors/system-unavailable-error';
import {AccessDeniedError} from '../../errors/access-denied-error';
import {AdminContainerView} from '../../components/layout/admin-container-view';
import {BackNavigation} from '../../components/layout/back-navigation';

/**
 * The EditCodeComponent class implements the edit code category component.
 *
 * @author Marcus Portmann
 */
@Component({
  templateUrl: 'edit-code.component.html',
  styleUrls: ['edit-code.component.css'],
})
export class EditCodeComponent extends AdminContainerView implements AfterViewInit {

  codeCategoryId: string;

  codeCategoryIdFormControl: FormControl;

  codeId: string;

  editCodeForm: FormGroup;

  idFormControl: FormControl;

  nameFormControl: FormControl;

  valueFormControl: FormControl;

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
              private formBuilder: FormBuilder, private i18n: I18n,
              private codesService: CodesService, private dialogService: DialogService,
              private spinnerService: SpinnerService) {
    super();

    // Retrieve parameters
    this.codeCategoryId = this.activatedRoute.snapshot.paramMap.get('codeCategoryId')!;
    this.codeId = this.activatedRoute.snapshot.paramMap.get('codeId')!;

    // Initialise form controls
    this.codeCategoryIdFormControl = new FormControl({value: '', disabled: true},
      [Validators.required, Validators.maxLength(100)]);

    this.idFormControl = new FormControl({value: '', disabled: true},
      [Validators.required, Validators.maxLength(100)]);

    this.nameFormControl = new FormControl('', [Validators.required, Validators.maxLength(100)]);

    this.valueFormControl = new FormControl('', Validators.required);

    // Initialise form
    this.editCodeForm = new FormGroup({
      codeCategoryId: this.codeCategoryIdFormControl,
      id: this.idFormControl,
      name: this.nameFormControl,
      value: this.valueFormControl
    });
  }

  get backNavigation(): BackNavigation {
    return new BackNavigation(this.i18n({
      id: '@@edit_code_component_back_title',
      value: 'Codes'
    }), ['../..'], {relativeTo: this.activatedRoute});
  }

  get title(): string {
    return this.i18n({
      id: '@@edit_code_component_title',
      value: 'Edit Code'
    })
  }

  ngAfterViewInit(): void {
    this.spinnerService.showSpinner();

    this.codesService.getCode(this.codeCategoryId, this.codeId)
      .pipe(first(), finalize(() => this.spinnerService.hideSpinner()))
      .subscribe((code: Code) => {
        this.idFormControl.setValue(code.id);
        this.codeCategoryIdFormControl.setValue(code.codeCategoryId);
        this.nameFormControl.setValue(code.name);
        this.valueFormControl.setValue(code.value);
      }, (error: Error) => {
        // noinspection SuspiciousTypeOfGuard
        if ((error instanceof CodesServiceError) || (error instanceof AccessDeniedError) ||
          (error instanceof SystemUnavailableError)) {
          // noinspection JSIgnoredPromiseFromCall
          this.router.navigateByUrl('/error/send-error-report', {state: {error}});
        } else {
          this.dialogService.showErrorDialog(error);
        }
      });
  }

  onCancel(): void {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['../..'], {relativeTo: this.activatedRoute});
  }

  onOK(): void {
    if (this.editCodeForm.valid) {
      const code: Code = new Code(this.codeId, this.codeCategoryIdFormControl.value,
        this.nameFormControl.value, this.valueFormControl.value);

      this.spinnerService.showSpinner();

      this.codesService.updateCode(code)
        .pipe(first(), finalize(() => this.spinnerService.hideSpinner()))
        .subscribe(() => {
          // noinspection JSIgnoredPromiseFromCall
          this.router.navigate(['../..'], {relativeTo: this.activatedRoute});
        }, (error: Error) => {
          // noinspection SuspiciousTypeOfGuard
          if ((error instanceof CodesServiceError) || (error instanceof AccessDeniedError) ||
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
