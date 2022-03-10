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

import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {CoreModule} from 'ngx-inception/core';
import {ReferenceComponentsModule} from 'ngx-inception/reference';
import {ExampleFormComponent} from './example-form.component';
import {PartyComponentsFormComponent} from './party-components-form.component';
import {PartyReferenceFormComponent} from './party-reference-form-component';
import {PersonFormComponent} from "./person-form.component";
import {PersonComponent} from "./person.component";
import {ReferenceFormComponent} from './reference-form.component';

const routes: Routes = [{
  path: '',
  redirectTo: 'example-form'
}, {
  path: 'example-form',
  component: ExampleFormComponent,
  data: {
    title: 'Example Form',
  }
}, {
  path: 'party-components-form',
  component: PartyComponentsFormComponent,
  data: {
    title: 'Party Components Form',
  }
}, {
  path: 'party-reference-form',
  component: PartyReferenceFormComponent,
  data: {
    title: 'Party Reference Form',
  }
}, {
  path: 'person-form',
  component: PersonFormComponent,
  data: {
    title: 'Person Form',
  }
}, {
  path: 'reference-form',
  component: ReferenceFormComponent,
  data: {
    title: 'Reference Form',
  }
}
];

@NgModule({
  imports: [
    // Angular modules
    CommonModule, FormsModule, ReactiveFormsModule, RouterModule.forChild(routes),

    // Inception modules
    CoreModule, ReferenceComponentsModule,
  ],
  declarations: [ExampleFormComponent, PartyComponentsFormComponent, PartyReferenceFormComponent,
    PersonComponent, PersonFormComponent, ReferenceFormComponent],
  providers: []
})
export class FormModule {
}
