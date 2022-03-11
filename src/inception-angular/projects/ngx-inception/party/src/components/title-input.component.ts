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
 * See the License for the specific title governing permissions and
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
import {debounceTime, first, map, startWith} from 'rxjs/operators';
import {PartyReferenceService} from '../services/party-reference.service';
import {Title} from '../services/title';

/**
 * The TitleInputComponent class implements the title input component.
 *
 * @author Marcus Portmann
 */
@Component({
  // eslint-disable-next-line @angular-eslint/component-selector
  selector: 'title-input',
  template: `
    <div matAutocompleteOrigin #origin="matAutocompleteOrigin">
      <input
        #titleInput
        type="text"
        matInput
        autocompleteSelectionRequired
        required="required"
        [matAutocomplete]="titleAutocomplete"
        [matAutocompleteConnectedTo]="origin"
        (input)="titleInputChanged($event)"
        (focusin)="onFocusIn($event)"
        (focusout)="onFocusOut($event)">
      <mat-autocomplete
        #titleAutocomplete="matAutocomplete"
        [displayWith]="displayTitle"

        (optionSelected)="selectTitle($event)">
        <mat-option *ngFor="let title of filteredTitles$ | async" [value]="title">
          {{title.name}}
        </mat-option>
      </mat-autocomplete>
    </div>
  `,
  providers: [
    {
      provide: MatFormFieldControl,
      useExisting: TitleInputComponent
    }
  ]
})
export class TitleInputComponent implements MatFormFieldControl<string>,
  ControlValueAccessor, OnInit, OnDestroy {

  private static _nextId: number = 0;

  /**
   * The name for the control type.
   */
  controlType = 'title-input';

  /**
   * The filtered titles for the autocomplete.
   */
  filteredTitles$: Subject<Title[]> = new ReplaySubject<Title[]>();

  /**
   * Whether the control is focused.
   */
  focused = false;

  /**
   * The ID for the control.
   */
  @HostBinding() id = `title-input-${TitleInputComponent._nextId++}`;

  /**
   * The observable indicating that the state of the control has changed.
   */
  stateChanges = new Subject<void>();

  /**
   * The title input.
   */
  @ViewChild(MatInput, {static: true}) titleInput!: MatInput;

  /**
   * The reference to the element for the title input.
   */
  @ViewChild('titleInput') titleInputElementRef!: ElementRef;

  /**
   * The observable providing access to the value for the title input as it changes.
   */
  titleInputValue$: Subject<string> = new ReplaySubject<string>();

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
      this.titleInput.disabled = true;
    }

    this.stateChanges.next();
  }

  /**
   * The placeholder for the title input.
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
   * The code for the selected title.
   */
  private _value: string | null = null;

  /**
   * Returns the code for the selected title.
   *
   * @return the code for the selected title
   */
  public get value(): string | null {
    return this._value;
  }

  /**
   * Set the code for the selected title.
   *
   * @param value the code for the selected title
   */
  @Input()
  public set value(value: string | null) {
    if (value == undefined) {
      value = null;
    }

    if (this._value !== value) {
      this.partyReferenceService.getTitles().pipe(first()).subscribe((titles: Map<string, Title>) => {
        this._value = null;
        this.titleInput.value = '';

        if (!!value) {
          for (const title of titles.values()) {
            if (title.code === value) {
              this._value = value;
              this.titleInput.value = title.name;
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
    return this.focused || !this.empty || this.titleInput.focused;
  }

  displayTitle(title: Title): string {
    if (!!title) {
      return title.name;
    } else {
      return '';
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
    this.stateChanges.complete();
  }

  ngOnInit(): void {
    this.titleInput.placeholder = this._placeholder;

    this.partyReferenceService.getTitles().pipe(first()).subscribe((titles: Map<string, Title>) => {
      this.subscriptions.add(this.titleInputValue$.pipe(
        startWith(''),
        debounceTime(500),
        map((value: string | Title) => {
          if (typeof (value) === 'string') {
            value = value.toLowerCase();
          } else {
            value = value.name.toLowerCase();
          }

          let filteredTitles: Title[] = [];

          for (const title of titles.values()) {
            if (title.name.toLowerCase().indexOf(value) === 0) {
              filteredTitles.push(title);
            }
          }

          this.filteredTitles$.next(filteredTitles);
        })).subscribe());
    });
  }

  onChange: any = (_: any) => {
  };

  onContainerClick(event: MouseEvent) {
    if ((event.target as Element).tagName.toLowerCase() != 'input') {
      this.titleInput.focus();
    }
  }

  onFocusIn(event: FocusEvent) {
    if (!this.focused) {
      this.focused = true;
      this.stateChanges.next();
    }
  }

  onFocusOut(event: FocusEvent) {
    // If we have cleared the title input then clear the value when losing focus
    if ((!!this._value) && (!this.titleInput.value)) {
      this._value = null;
      this.onChange(this._value);
      this.changeDetectorRef.detectChanges();
      this.stateChanges.next();
    }

    this.touched = true;
    this.onTouched();
    this.focused = this.titleInput.focused;
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

  selectTitle(event: MatAutocompleteSelectedEvent): void {
    this.value = event.option.value.code;
  }

  setDescribedByIds(ids: string[]) {
    // TODO: IMPLEMENT THIS IF NECESSARY -- MARCUS
    // https://material.angular.io/guide/creating-a-custom-form-field-control

    // const controlElement = this._elementRef.nativeElement
    // .querySelector('.example-tel-input-container')!;

    // const controlElement = this._elementRef.nativeElement
    // .querySelector('.example-tel-input-container')!;
    // controlElement.setAttribute('aria-describedby', ids.join(' '));
  }

  titleInputChanged(event: Event) {
    if (((event.target as HTMLInputElement).value) !== undefined) {
      this.titleInputValue$.next((event.target as HTMLInputElement).value);
    }
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
