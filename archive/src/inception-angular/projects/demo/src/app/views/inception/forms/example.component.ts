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

import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Country, ReferenceService} from 'ngx-inception/reference';
import {ReplaySubject, Subject, Subscription} from 'rxjs';
import {debounceTime, first, startWith} from 'rxjs/operators';

/**
 * The Title class holds title information for the example form component.
 */
class Title {
  name: string;

  value: string;

  constructor(name: string, value: string) {
    this.name = name;
    this.value = value;
  }
}

/**
 * The ExampleComponent class.
 *
 * @author Marcus Portmann
 */
@Component({
  templateUrl: 'example.component.html'
})
export class ExampleComponent implements OnInit, OnDestroy {

  static readonly MAX_DATE = Date.now();

  static readonly MIN_DATE = new Date(1900, 1, 1);

  exampleForm: FormGroup;

  filteredCountries$: Subject<Country[]> = new ReplaySubject<Country[]>();

  titles: Array<Title> = [new Title('Mr', 'Mr'), new Title('Mrs', 'Mrs'), new Title('Ms', 'Ms')];

  private subscriptions: Subscription = new Subscription();

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
              private formBuilder: FormBuilder, private referenceService: ReferenceService) {

    this.exampleForm = this.formBuilder.group({
      // hideRequired: false,
      // floatLabel: 'auto',
      // eslint-disable-next-line
      name: ['', Validators.required],
      preferredName: ['', Validators.required],
      title: ['', Validators.required], // dateOfBirth: [moment(), Validators.required],
      dateOfBirth: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      favoriteCountry: ['', Validators.required],
      grossIncome: ['', Validators.required],
      favoriteColor: ['', Validators.required],
      favoritePetDog: [''],
      favoritePetCat: [{value: 'true'}],
      favoritePetFish: [''],
      notes: ['']
    });
  }

  displayCountry(country: Country): string {
    if (!!country) {
      return country.shortName;
    } else {
      return '';
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

  ngOnInit(): void {
    this.referenceService.getCountries().pipe(first()).subscribe((countries: Map<string, Country>) => {
      const favoriteCountryControl = this.exampleForm.get('favoriteCountry');

      if (favoriteCountryControl) {
        this.subscriptions.add(favoriteCountryControl.valueChanges.pipe(
          startWith(''),
          debounceTime(500)).subscribe((value: string | Country) => {
          if (typeof (value) === 'string') {
            value = value.toLowerCase();
          } else {
            value = value.shortName.toLowerCase();
          }

          let filteredCountries: Country[] = [];

          for (const country of countries.values()) {
            if (country.shortName.toLowerCase().indexOf(value) === 0) {
              filteredCountries.push(country);
            }
          }

          this.filteredCountries$.next(filteredCountries);
        }));
      }
    });
  }

  onSubmit(): void {
    const favoriteColorControl = this.exampleForm.get('favoriteColor');

    if (favoriteColorControl) {
      console.log('favorite color = ', favoriteColorControl.value);
    }
  }
}
