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

import {Component, Inject} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import {DialogData} from "./dialog-data";

/**
 * The WarningDialog class implements the warning dialog component.
 *
 * @author Marcus Portmann
 */
@Component({
  selector: 'warning-dialog',
  template: `

    <div class="header">
      <i class="fas fa-3x fa-exclamation-triangle"></i>
    </div>
    <div class="message-holder">
      <span class="message">
        {{data.message}}
      </span>
    </div>
    <div class="button">
      <button *ngIf="data.buttonText; else defaultButton" mat-flat-button (click)="onButtonClick()" tabindex="-1">{{ data.buttonText }}</button>
      <ng-template #defaultButton>
        <button mat-flat-button (click)="onButtonClick()" tabindex="-1" i18n="@@warning_dialog_button_ok">Ok</button>
      </ng-template>
    </div>
  `,
  host: {
    'class': 'warning-dialog'
  }
})
export class WarningDialog {

  /**
   * Constructs a new WarningDialog.
   *
   * @param {MatDialogRef<WarningDialog>} dialogRef The dialog reference.
   * @param {DialogData} data                       The dialog data.
   */
  constructor(
    private dialogRef: MatDialogRef<WarningDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
  }

  onButtonClick(): void {
    this.dialogRef.close();
  }
}
