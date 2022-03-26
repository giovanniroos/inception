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
import {BehaviorSubject, pipe, ReplaySubject, Subject, Subscription, throttleTime} from 'rxjs';
import {debounceTime, first, map, startWith} from 'rxjs/operators';
import {ReferenceService} from '../services/reference.service';
import {Region} from '../services/region';
import {combineLatest} from 'rxjs';

/**
 * The RegionInputComponent class implements the region input component.
 *
 * @author Marcus Portmann
 */
@Component({
  // eslint-disable-next-line @angular-eslint/component-selector
  selector: 'region-input',
  template: `
    <div matAutocompleteOrigin #origin="matAutocompleteOrigin">
      <input
        #regionInput
        type="text"
        matInput
        autocompleteSelectionRequired
        required="required"
        [matAutocomplete]="regionAutocomplete"
        [matAutocompleteConnectedTo]="origin"
        (input)="regionInputChanged($event)"
        (focusin)="onFocusIn($event)"
        (focusout)="onFocusOut($event)">
      <mat-autocomplete
        #regionAutocomplete="matAutocomplete"
        [displayWith]="displayRegion"

        (optionSelected)="selectRegion($event)">
        <mat-option *ngFor="let region of filteredRegions$ | async" [value]="region">
          {{region.name}}
        </mat-option>
      </mat-autocomplete>
    </div>
  `,
  providers: [
    {
      provide: MatFormFieldControl,
      useExisting: RegionInputComponent
    }
  ]
})
export class RegionInputComponent implements MatFormFieldControl<string>,
  ControlValueAccessor, OnInit, OnDestroy {

  private static _nextId: number = 0;

  /**
   * The name for the control type.
   */
  controlType = 'region-input';

  /**
   * The filtered regions for the autocomplete.
   */
  filteredRegions$: Subject<Region[]> = new ReplaySubject<Region[]>();

  /**
   * Whether the control is focused.
   */
  focused = false;

  /**
   * The ID for the control.
   */
  @HostBinding() id = `region-input-${RegionInputComponent._nextId++}`;

  /**
   * The region input.
   */
  @ViewChild(MatInput, {static: true}) regionInput!: MatInput;

  /**
   * The reference to the element for the region input.
   */
  @ViewChild('regionInput') regionInputElementRef!: ElementRef;

  /**
   * The observable providing access to the value for the region input as it changes.
   */
  regionInputValue$: Subject<string> = new ReplaySubject<string>();

  /**
   * The observable indicating that the state of the control has changed.
   */
  stateChanges = new Subject<void>();

  /**
   * Has the control received a touch event.
   */
  touched: boolean = false;

  //@Input('aria-describedby') userAriaDescribedBy?: string;

  /**
   * The regions for the country.
   */
  private _regions: Region[] = [];

  /**
   * The ISO 3166-1 alpha-2 code for the country to retrieve the regions for.
   */
  private country$: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);

  private subscriptions: Subscription = new Subscription();

  constructor(private referenceService: ReferenceService,
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
      this.regionInput.disabled = true;
    }

    this.stateChanges.next();
  }

  /**
   * The placeholder for the region input.
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
   * The ISO 639-1 alpha-2 code for the selected region.
   */
  //private _value: string | null = null;


  private value$: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);

  /**
   * Returns the ISO 639-1 alpha-2 code for the selected region.
   *
   * @return The ISO 639-1 alpha-2 code for the selected region.
   */
  public get value(): string | null {
    return this.value$.value;
  }

  /**
   * Set the ISO 639-1 alpha-2 code for the selected region.
   *
   * @param value the ISO 639-1 alpha-2 code for the selected region
   */
  @Input()
  public set value(value: string | null) {
    console.log('[RegionInputComponent][value] this.value$.value = (' + this.value$.value + '), value = (' + value + ')');

    if (value == undefined) {
      value = null;
    }

    if (this.value$.value !== value) {
      this.value$.next(value);
    }


    /*
    if (this._value !== value) {




      this._value = null;

      if (!!value) {
        for (const region of this._regions.values()) {
          if (region.code === value) {
            this._value = value;
            this.regionInput.value = region.name;
            break;
          }
        }
      }

      this.onChange(this._value);
      this.changeDetectorRef.detectChanges();
      this.stateChanges.next();
    }
    */

  }

  /**
   * The ISO 3166-1 alpha-2 code for the country to retrieve the regions for.
   */
  @Input() get country(): string | null {
    return this.country$.value;
  }

  set country(country: string | null) {
    console.log('[RegionInputComponent][country] this.country$.value = (' + this.country$.value + '), country = (' + country + ')');

    if (country == undefined) {
      country = null;
    }

    if (country !== this.country$.value) {
      this.country$.next(country);
    }
  }

  get empty(): boolean {
    return ((this.value$.value == null) || (this.value$.value.length == 0));
  }

  get errorState(): boolean {
    return this.required && ((this.value$.value == null) || (this.value$.value.length == 0)) && this.touched;
  }

  @HostBinding('class.floating')
  get shouldLabelFloat() {
    return this.focused || !this.empty || this.regionInput.focused;
  }

  displayRegion(region: Region): string {
    if (!!region) {
      return region.name;
    } else {
      return '';
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
    this.stateChanges.complete();
  }

  ngOnInit(): void {
    console.log('[RegionInputComponent][ngOnInit] HERE');

    this.regionInput.placeholder = this._placeholder;

    this.subscriptions.add(combineLatest([this.country$]).pipe(throttleTime(250), map(values => ({
      country: this.country$.value
    }))).subscribe(parameters => {
      console.log('parameters = ', parameters);

      this._regions = [];

      this.referenceService.getRegions().pipe(first()).subscribe((regions: Map<string, Region>) => {
        for (const region of regions.values()) {
          if ((!!parameters.country) && (region.country === parameters.country)) {
            this._regions.push(region);
          } else {
            this._regions.push(region);
          }
        }
      });

      console.log('After loading regions, this.value$.value = ', this.value$.value);

      /*
      if (!!this._value) {
        console.log('Looking for region with code = ', this._value);

        for (const region of this._regions.values()) {
          if (region.code === this._value) {
            this.regionInput.value = region.name;
            return;
          }
        }

        console.log('Resetting value!!!!');

        this.value = null;
      }*/


    }));

    this.subscriptions.add(this.value$.subscribe((value: string | null) => {
      this.onChange(value);
      this.changeDetectorRef.detectChanges();
      this.stateChanges.next();
    }));

/*
    this.subscriptions.add(this.regionInputValue$.pipe(
      startWith(''),
      debounceTime(500),
      map((value: string | Region) => {
        if (typeof (value) === 'string') {
          value = value.toLowerCase();
        } else {
          value = value.name.toLowerCase();
        }

        let filteredRegions: Region[] = [];

        for (const region of this._regions) {
          if (region.name.toLowerCase().indexOf(value) === 0) {
            filteredRegions.push(region);
          }
        }

        this.filteredRegions$.next(filteredRegions);
      })).subscribe());
    */

    this.subscriptions.add(this.regionInputValue$.pipe(
      startWith(''),
      debounceTime(500)).subscribe((value: string | Region) => {
      if (typeof (value) === 'string') {
        value = value.toLowerCase();
      } else {
        value = value.name.toLowerCase();
      }

      let filteredRegions: Region[] = [];

      for (const region of this._regions) {
        if (region.name.toLowerCase().indexOf(value) === 0) {
          filteredRegions.push(region);
        }
      }

      this.filteredRegions$.next(filteredRegions);
    }));

  }

  onChange: any = (_: any) => {
  };

  onContainerClick(event: MouseEvent) {
    if ((event.target as Element).tagName.toLowerCase() != 'input') {
      this.regionInput.focus();
    }
  }

  onFocusIn(event: FocusEvent) {
    if (!this.focused) {
      this.focused = true;
      this.stateChanges.next();
    }
  }

  onFocusOut(event: FocusEvent) {
    // If we have cleared the region input then clear the value when losing focus
    if ((!!this.value$.value) && (!this.regionInput.value)) {
      this.value$.next(null);
    }

    this.touched = true;
    this.onTouched();
    this.focused = this.regionInput.focused;
    this.stateChanges.next();
  }

  onTouched: any = () => {
  };

  regionInputChanged(event: Event) {
    if (((event.target as HTMLInputElement).value) !== undefined) {
      this.regionInputValue$.next((event.target as HTMLInputElement).value);
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  selectRegion(event: MatAutocompleteSelectedEvent): void {
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
