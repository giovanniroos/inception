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

import {AfterViewInit, Component, HostBinding, ViewChild} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {Code} from '../../services/codes/code';
import {CodesService} from '../../services/codes/codes.service';
import {DialogService} from '../../services/dialog/dialog.service';
import {SpinnerService} from '../../services/layout/spinner.service';
import {I18n} from '@ngx-translate/i18n-polyfill';
import {Error} from '../../errors/error';
import {ActivatedRoute, Router} from '@angular/router';
import {finalize, first} from 'rxjs/operators';
import {CodesServiceError} from '../../services/codes/codes.service.errors';
import {SystemUnavailableError} from '../../errors/system-unavailable-error';
import {ConfirmationDialogComponent} from '../../components/dialogs';
import {AccessDeniedError} from '../../errors/access-denied-error';
import {AdminContainerView} from '../../components/layout/admin-container-view';
import {BackNavigation} from '../../components/layout/back-navigation';

/**
 * The CodesComponent class implements the codes component.
 *
 * @author Marcus Portmann
 */
@Component({
  templateUrl: 'codes.component.html',
  styleUrls: ['codes.component.css']
})
export class CodesComponent extends AdminContainerView implements AfterViewInit {

  @HostBinding('class') hostClass = 'flex flex-column flex-fill';

  codeCategoryId: string;

  dataSource: MatTableDataSource<Code> = new MatTableDataSource<Code>();

  displayedColumns = ['id', 'name', 'actions'];

  @ViewChild(MatPaginator, {static: true}) paginator!: MatPaginator;

  @ViewChild(MatSort, {static: true}) sort!: MatSort;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private i18n: I18n, private codesService: CodesService,
              private dialogService: DialogService, private spinnerService: SpinnerService) {
    super();

    // Retrieve the route parameters
    const codeCategoryId = this.activatedRoute.snapshot.paramMap.get('codeCategoryId');

    if (!codeCategoryId) {
      throw(new Error('No codeCategoryId route parameter found'));
    }

    this.codeCategoryId = decodeURIComponent(codeCategoryId);

    // Set the data source filter
    this.dataSource.filterPredicate =
      (data, filter): boolean => data.id.toLowerCase().includes(filter) || data.name.toLowerCase().includes(filter);
  }

  get backNavigation(): BackNavigation {
    return new BackNavigation(this.i18n({
      id: '@@codes_codes_component_back_title',
      value: 'Code Categories'
    }), ['../../../../../..'], {relativeTo: this.activatedRoute});
  }

  get title(): string {
    return this.i18n({
      id: '@@codes_codes_component_title',
      value: 'Codes'
    });
  }

  applyFilter(filterValue: string): void {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  deleteCode(codeId: string): void {
    const dialogRef: MatDialogRef<ConfirmationDialogComponent, boolean> = this.dialogService.showConfirmationDialog({
      message: this.i18n({
        id: '@@codes_codes_component_confirm_delete_code',
        value: 'Are you sure you want to delete the code?'
      })
    });

    dialogRef.afterClosed()
      .pipe(first())
      .subscribe((confirmation: boolean | undefined) => {
        if (confirmation === true) {
          this.spinnerService.showSpinner();

          this.codesService.deleteCode(this.codeCategoryId, codeId)
            .pipe(first(), finalize(() => this.spinnerService.hideSpinner()))
            .subscribe(() => {
              this.loadCodes();
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
      });
  }

  editCode(codeId: string): void {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate([encodeURIComponent(codeId) + '/edit'], {relativeTo: this.activatedRoute});
  }

  loadCodes(): void {
    this.spinnerService.showSpinner();

    this.codesService.getCodes(this.codeCategoryId)
      .pipe(first(), finalize(() => this.spinnerService.hideSpinner()))
      .subscribe((codes: Code[]) => {
        this.dataSource.data = codes;
      }, (error: Error) => {
        // noinspection SuspiciousTypeOfGuard
        if ((error instanceof CodesServiceError) || (error instanceof AccessDeniedError) || (error instanceof SystemUnavailableError)) {
          // noinspection JSIgnoredPromiseFromCall
          this.router.navigateByUrl('/error/send-error-report', {state: {error}});
        } else {
          this.dialogService.showErrorDialog(error);
        }
      });
  }

  newCode(): void {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['new'], {relativeTo: this.activatedRoute});
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.loadCodes();
  }
}

