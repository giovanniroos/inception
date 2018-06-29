/*
 * Copyright 2018 Marcus Portmann
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

// Import Angular modules
import {CommonModule} from '@angular/common';
import {FormsModule} from "@angular/forms";
import { NgModule } from '@angular/core';

// Import Angular classes
import {RouterModule, Routes} from "@angular/router";

// Import Inception components
import {CardsComponent} from './cards.component';

// Collapse Component
import {CollapseModule} from 'ngx-bootstrap/collapse';
import {CollapsesComponent} from './collapses.component';

// Pagination Component
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {PaginationsComponent} from './paginations.component';

// Popovers Component
import {PopoverModule} from 'ngx-bootstrap/popover';
import {PopoversComponent} from './popovers.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'cards',
    pathMatch: 'full',
  },
  {
    path: 'cards',
    component: CardsComponent,
    data: {
      title: 'Cards',
    }
  },
  {
    path: 'collapses',
    component: CollapsesComponent,
    data: {
      title: 'Collapses',
    }
  },
  {
    path: 'paginations',
    component: PaginationsComponent,
    data: {
      title: 'Paginations',
    }
  },
  {
    path: 'popovers',
    component: PopoversComponent,
    data: {
      title: 'Popovers',
    }
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,

    CollapseModule.forRoot(),
    PaginationModule.forRoot(),
    PopoverModule.forRoot(),

    RouterModule.forChild(routes)
  ],
  declarations: [ CardsComponent, CollapsesComponent, PaginationsComponent, PopoversComponent ]
})
export class BaseModule { }
