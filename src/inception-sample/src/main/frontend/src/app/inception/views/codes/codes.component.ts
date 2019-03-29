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

import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {Code} from "../../services/codes/code";
import {CodesService} from "../../services/codes/codes.service";
import {DialogService} from "../../services/dialog/dialog.service";
import {SpinnerService} from "../../services/layout/spinner.service";
import {I18n} from "@ngx-translate/i18n-polyfill";
import {Error} from "../../errors/error";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable, of} from "rxjs";
import {first} from "rxjs/operators";
import {TitleService} from "../../services/layout/title.service";
import {CodesServiceError} from "../../services/codes/codes.service.errors";
import {SystemUnavailableError} from "../../errors/system-unavailable-error";
import {ConfirmationDialog} from "../../components/dialogs";

/**
 * The CodesComponent class implements the codes component.
 *
 * @author Marcus Portmann
 */
@Component({
  templateUrl: 'codes.component.html',
  styleUrls: ['codes.component.css'],
  host: {
    'class': 'flex flex-column flex-fill',
  }
})
export class CodesComponent implements AfterViewInit, OnInit {

  codeCategoryId: string;

  dataSource = new MatTableDataSource<Code>();

  displayedColumns: string[] = ['id', 'name', 'actions'];

  @ViewChild(MatPaginator)
  paginator: MatPaginator;

  @ViewChild(MatSort)
  sort: MatSort;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private i18n: I18n,
              private codesService: CodesService, private dialogService: DialogService,
              private spinnerService: SpinnerService) {
  }

  applyFilter(filterValue: string): void {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  deleteCode(codeId: string, codeName: string): void {
    let dialogRef: MatDialogRef<ConfirmationDialog, boolean> = this.dialogService.showConfirmationDialog({message: this.i18n({id: '@@codes_component_confirm_delete_code', value: 'Are you sure you want to delete the code \'{{codeName}}\'?'}, {codeName: codeName})});

    dialogRef.afterClosed().pipe(first()).subscribe((confirmation: boolean) => {
      if (confirmation === true) {
        this.spinnerService.showSpinner();

        this.codesService.deleteCode(this.codeCategoryId, codeId).pipe(first()).subscribe((result: boolean) => {
          this.spinnerService.hideSpinner();

          this.ngAfterViewInit();
        }, (error: Error) => {
          this.spinnerService.hideSpinner();

          if ((error instanceof CodesServiceError) || (error instanceof SystemUnavailableError)) {
            this.router.navigateByUrl('/error/send-error-report', {state: {error: error}});
          }
          else {
            this.dialogService.showErrorDialog(error);
          }
        });
      }
    });  }

  editCode(id: string): void {
    console.log('Editing code: ', id);
  }

  loadCodes(): void {
    this.spinnerService.showSpinner();

    this.codesService.getCodeCategoryCodes(this.codeCategoryId).pipe(first()).subscribe((codes: Code[]) => {
      this.spinnerService.hideSpinner();

      this.dataSource.data = codes;
    }, (error: Error) => {
      this.spinnerService.hideSpinner();

      if ((error instanceof CodesServiceError) || (error instanceof SystemUnavailableError)) {
        this.router.navigateByUrl('/error/send-error-report', {state: {error: error}});
      }
      else {
        this.dialogService.showErrorDialog(error);
      }
    });

    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.dataSource.filterPredicate = function(data, filter): boolean {
      return data.id.toLowerCase().includes(filter) || data.name.toLowerCase().includes(filter);
    };
  }

  newCode(): void {
    this.router.navigate(['../../../../../new-code'], {relativeTo: this.activatedRoute});
  }

  ngAfterViewInit(): void {
    this.loadCodes();
  }

  ngOnInit(): void {
    this.codeCategoryId = this.activatedRoute.snapshot.paramMap.get('codeCategoryId');
  }
}

