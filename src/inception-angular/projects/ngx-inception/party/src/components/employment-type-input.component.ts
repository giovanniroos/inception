/*
 * Copyright 2022 Marcus Portmann
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

import {coerceBooleanProperty} from '@angular/cdk/coercion';
import {
  ChangeDetectorRef, Component, ElementRef, HostBinding, Input, OnDestroy, OnInit, Optional, Self,
  ViewChild
} from '@angular/core';
import {ControlValueAccessor, NgControl} from '@angular/forms';
import {MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {MatFormFieldControl} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {ReplaySubject, Subject, Subscription} from 'rxjs';
import {debounceTime, first, startWith} from 'rxjs/operators';
import {EmploymentType} from '../services/employment-type';
import {PartyReferenceService} from '../services/party-reference.service';

/**
 * The EmploymentTypeInputComponent class implements the employment type input component.
 *
 * @author Marcus Portmann
 */
@Component({
  // eslint-disable-next-line @angular-eslint/component-selector
  selector: 'employment-type-input',
  template: `
    <div matAutocompleteOrigin #origin="matAutocompleteOrigin">
      <input
        #employmentTypeInput
        type="text"
        matInput
        autocompleteSelectionRequired
        required="required"
        [matAutocomplete]="employmentTypeAutocomplete"
        [matAutocompleteConnectedTo]="origin"
        (input)="employmentTypeInputChanged($event)"
        (focusin)="onFocusIn($event)"
        (focusout)="onFocusOut($event)">
      <mat-autocomplete
        #employmentTypeAutocomplete="matAutocomplete"
        [displayWith]="displayEmploymentType"
        (optionSelected)="selectEmploymentType($event)">
        <mat-option
          *ngFor="let employmentType of filteredEmploymentTypes$ | async"
          [value]="employmentType">
          {{employmentType.name}}
        </mat-option>
      </mat-autocomplete>
    </div>
  `,
  providers: [
    {
      provide: MatFormFieldControl,
      useExisting: EmploymentTypeInputComponent
    }
  ]
})
export class EmploymentTypeInputComponent implements MatFormFieldControl<string>,
  ControlValueAccessor, OnInit, OnDestroy {

  private static _nextId: number = 0;

  /**
   * The name for the control type.
   */
  controlType = 'employment-type-input';

  /**
   * The employment type input.
   */
  @ViewChild(MatInput, {static: true}) employmentTypeInput!: MatInput;

  /**
   * The reference to the element for the employment type input.
   */
  @ViewChild('employmentTypeInput') employmentTypeInputElementRef!: ElementRef;

  /**
   * The observable providing access to the value for the employment type input as it changes.
   */
  employmentTypeInputValue$: Subject<string> = new ReplaySubject<string>();

  /**
   * The filtered employment types for the autocomplete.
   */
  filteredEmploymentTypes$: Subject<EmploymentType[]> = new ReplaySubject<EmploymentType[]>();

  /**
   * Whether the control is focused.
   */
  focused = false;

  /**
   * The ID for the control.
   */
  @HostBinding() id = `employment-type-input-${EmploymentTypeInputComponent._nextId++}`;

  /**
   * The observable indicating that the state of the control has changed.
   */
  stateChanges = new Subject<void>();

  /**
   * Has the control received a touch event.
   */
  touched: boolean = false;

  //@Input('aria-describedby') userAriaDescribedBy?: string;

  private subscriptions: Subscription = new Subscription();

  constructor(private partyReferenceService: PartyReferenceService,
              @Optional() @Self() public ngControl: NgControl,
              private changeDetectorRef: ChangeDetectorRef) {
    if (this.ngControl != null) {
      /*
       * Setting the value accessor directly (instead of using the providers) to avoid running into
       * a circular import.
       */
      this.ngControl.valueAccessor = this;
    }
  }

  /**
   * Whether the control is disabled.
   * @private
   */
  private _disabled = false;

  @Input()
  get disabled(): boolean {
    return this._disabled;
  }

  set disabled(value: boolean) {
    this._disabled = coerceBooleanProperty(value);

    if (this._disabled) {
      this.employmentTypeInput.disabled = true;
    }

    this.stateChanges.next();
  }

  /**
   * The placeholder for the employment type input.
   * @private
   */
  private _placeholder: string = '';

  @Input()
  get placeholder() {
    return this._placeholder;
  }

  set placeholder(placeholder) {
    this._placeholder = placeholder;
    this.stateChanges.next();
  }

  /**
   * Whether the control is required.
   * @private
   */
  private _required: boolean = false;

  @Input()
  get required(): boolean {
    return this._required;
  }

  set required(req: any) {
    this._required = coerceBooleanProperty(req);
    this.stateChanges.next();
  }

  /**
   * The code for the selected employment type.
   */
  private _value: string | null = null;

  /**
   * Returns the code for the selected employment type.
   *
   * @return The code for the selected employment type.
   */
  public get value(): string | null {
    return this._value;
  }

  /**
   * Set the code for the selected employment type.
   *
   * @param value the code for the selected employment type
   */
  @Input()
  public set value(value: string | null) {
    if (value == undefined) {
      value = null;
    }

    if (this._value !== value) {
      this.partyReferenceService.getEmploymentTypes().pipe(first()).subscribe((employmentTypes: Map<string, EmploymentType>) => {
        this._value = null;
        this.employmentTypeInput.value = '';

        if (!!value) {
          for (const employmentType of employmentTypes.values()) {
            if (employmentType.code === value) {
              this._value = value;
              this.employmentTypeInput.value = employmentType.name;
              break;
            }
          }
        }

        this.onChange(this._value);
        this.changeDetectorRef.detectChanges();
        this.stateChanges.next();
      });
    }
  }

  get empty(): boolean {
    return ((this._value == null) || (this._value.length == 0));
  }

  get errorState(): boolean {
    return this.required && ((this._value == null) || (this._value.length == 0)) && this.touched;
  }

  @HostBinding('class.floating')
  get shouldLabelFloat() {
    return this.focused || !this.empty || this.employmentTypeInput.focused;
  }

  displayEmploymentType(employmentType: EmploymentType): string {
    if (!!employmentType) {
      return employmentType.name;
    } else {
      return '';
    }
  }

  employmentTypeInputChanged(event: Event) {
    if (((event.target as HTMLInputElement).value) !== undefined) {
      this.employmentTypeInputValue$.next((event.target as HTMLInputElement).value);
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
    this.stateChanges.complete();
  }

  ngOnInit(): void {
    this.employmentTypeInput.placeholder = this._placeholder;

    this.partyReferenceService.getEmploymentTypes().pipe(first()).subscribe((employmentTypes: Map<string, EmploymentType>) => {
      this.subscriptions.add(this.employmentTypeInputValue$.pipe(
        startWith(''),
        debounceTime(500)).subscribe((value: string | EmploymentType) => {
        if (typeof (value) === 'string') {
          value = value.toLowerCase();
        } else {
          value = value.name.toLowerCase();
        }

        let filteredEmploymentTypes: EmploymentType[] = [];

        for (const employmentType of employmentTypes.values()) {
          if (employmentType.name.toLowerCase().indexOf(value) === 0) {
            filteredEmploymentTypes.push(employmentType);
          }
        }

        this.filteredEmploymentTypes$.next(filteredEmploymentTypes);
      }));
    });
  }

  onChange: any = (_: any) => {
  };

  onContainerClick(event: MouseEvent) {
    if ((event.target as Element).tagName.toLowerCase() != 'input') {
      this.employmentTypeInput.focus();
    }
  }

  onFocusIn(event: FocusEvent) {
    if (!this.focused) {
      this.focused = true;
      this.stateChanges.next();
    }
  }

  onFocusOut(event: FocusEvent) {
    // If we have cleared the input then clear the value when losing focus
    if ((!!this._value) && (!this.employmentTypeInput.value)) {
      this._value = null;
      this.onChange(this._value);
      this.changeDetectorRef.detectChanges();
    }

    this.touched = true;
    this.onTouched();
    this.focused = this.employmentTypeInput.focused;
    this.stateChanges.next();
  }

  onTouched: any = () => {
  };

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  selectEmploymentType(event: MatAutocompleteSelectedEvent): void {
    this.value = event.option.value.code;
  }

  setDescribedByIds(ids: string[]) {
    // TODO: IMPLEMENT THIS IF NECESSARY -- MARCUS
    // https://material.angular.io/guide/creating-a-custom-form-field-control

    // const controlElement = this._elementRef.nativeElement
    // .querySelector('.example-tel-input-container')!;

    // const controlElement = this._elementRef.nativeElement
    // .querySelector('.example-tel-input-container')!;
    // controlElement.setEmployment('aria-describedby', ids.join(' '));
  }

  /**
   * Writes a new value to the control.
   *
   * This method is called by the forms API to write to the view when programmatic changes from
   * model to view are requested.
   *
   * @param value The new value for the control.
   */
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  writeValue(value: any): void {
    if (typeof value === 'string') {
      this.value = value as string;
    }
  }
}
