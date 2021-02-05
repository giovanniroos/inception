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

import {AfterViewInit, Component, HostBinding, OnDestroy, ViewChild} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {finalize, first, tap} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
import {merge, Subscription} from 'rxjs';
import {AdminContainerView} from '../../layout/components/admin-container-view';
import {TenantDatasource} from '../services/tenant.datasource';
import {TableFilterComponent} from '../../core/components/table-filter.component';
import {SecurityService} from '../services/security.service';
import {SpinnerService} from '../../layout/services/spinner.service';
import {DialogService} from '../../dialog/services/dialog.service';
import {ConfirmationDialogComponent} from '../../dialog/components/confirmation-dialog.component';
import {AccessDeniedError} from '../../core/errors/access-denied-error';
import {ServiceUnavailableError} from '../../core/errors/service-unavailable-error';
import {SortDirection} from '../../core/sorting/sort-direction';
import {Error} from '../../core/errors/error';

/**
 * The TenantsComponent class implements the tenants component.
 *
 * @author Marcus Portmann
 */
@Component({
  templateUrl: 'tenants.component.html',
  styleUrls: ['tenants.component.css']
})
export class TenantsComponent extends AdminContainerView implements AfterViewInit, OnDestroy {

  @HostBinding('class') hostClass = 'flex flex-column flex-fill';
  dataSource: TenantDatasource;
  displayedColumns = ['name', 'actions'];
  @ViewChild(MatPaginator, {static: true}) paginator!: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort!: MatSort;
  @ViewChild(TableFilterComponent, {static: true}) tableFilter!: TableFilterComponent;
  private subscriptions: Subscription = new Subscription();

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private securityService: SecurityService,
              private dialogService: DialogService, private spinnerService: SpinnerService) {
    super();

    this.dataSource = new TenantDatasource(this.securityService);
  }

  get title(): string {
    return $localize`:@@security_tenants_title:Tenants`
  }

  deleteTenant(tenantId: string): void {
    const dialogRef: MatDialogRef<ConfirmationDialogComponent, boolean> = this.dialogService.showConfirmationDialog({
      message: $localize`:@@security_tenants_confirm_delete_tenant:Are you sure you want to delete the tenant?`
    });

    dialogRef.afterClosed()
    .pipe(first())
    .subscribe((confirmation: boolean | undefined) => {
      if (confirmation === true) {
        this.spinnerService.showSpinner();

        this.securityService.deleteTenant(tenantId)
        .pipe(first(), finalize(() => this.spinnerService.hideSpinner()))
        .subscribe(() => {
          this.loadTenants();
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
    });
  }

  editTenant(tenantId: string): void {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate([encodeURIComponent(tenantId) + '/edit'], {relativeTo: this.activatedRoute});
  }

  loadTenants(): void {
    let filter = '';

    if (!!this.tableFilter.filter) {
      filter = this.tableFilter.filter;
      filter = filter.trim();
      filter = filter.toLowerCase();
    }

    const sortDirection = this.sort.direction === 'asc' ? SortDirection.Ascending : SortDirection.Descending;

    this.dataSource.load(filter, sortDirection, this.paginator.pageIndex, this.paginator.pageSize);
  }

  newTenant(): void {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate(['new'], {relativeTo: this.activatedRoute});
  }

  ngAfterViewInit(): void {
    this.subscriptions.add(this.dataSource.loading$.subscribe((next: boolean) => {
      if (next) {
        this.spinnerService.showSpinner();
      } else {
        this.spinnerService.hideSpinner();
      }
    }, (error: Error) => {
      // noinspection SuspiciousTypeOfGuard
      if ((error instanceof AccessDeniedError) || (error instanceof ServiceUnavailableError)) {
        // noinspection JSIgnoredPromiseFromCall
        this.router.navigateByUrl('/error/send-error-report', {state: {error}});
      } else {
        this.dialogService.showErrorDialog(error);
      }
    }));

    this.subscriptions.add(this.sort.sortChange.subscribe(() => {
      if (this.paginator) {
        this.paginator.pageIndex = 0;
      }
    }));

    this.subscriptions.add(this.tableFilter.changed.subscribe(() => {
      if (this.paginator) {
        this.paginator.pageIndex = 0;
      }
    }));

    this.subscriptions.add(merge(this.sort.sortChange, this.tableFilter.changed, this.paginator.page)
    .pipe(tap(() => {
      this.loadTenants();
    })).subscribe());

    this.loadTenants();
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  tenantUserDirectories(tenantId: string) {
    // noinspection JSIgnoredPromiseFromCall
    this.router.navigate([encodeURIComponent(tenantId) + '/user-directories'], {relativeTo: this.activatedRoute});
  }
}

