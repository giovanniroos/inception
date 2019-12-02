/*
 * Copyright 2019 Marcus Portmann
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Import Angular modules
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgModule} from '@angular/core';
// Import Inception module
// Import Angular classes
import {RouterModule, Routes} from '@angular/router';
// Import Inception components
import {ConfigurationsComponent} from './configurations.component';
import {EditConfigurationComponent} from './edit-configuration.component';
import {NewConfigurationComponent} from './new-configuration.component';
import {ConfigurationsTitleResolver} from './configurations-title-resolver';
import {NewConfigurationTitleResolver} from './new-configuration-title-resolver';
import {EditConfigurationTitleResolver} from './edit-configuration-title-resolver';
import {ConfigurationTitleResolver} from './configuration-title-resolver';
import {CoreModule} from '../../core/core.module';
import {DialogModule} from '../../dialog/dialog.module';
import {LayoutModule} from '../../layout/layout.module';
import {ConfigurationModule} from '../configuration.module';
import {CanActivateFunctionGuard} from '../../security/routing/can-activate-function-guard';

const routes: Routes = [{
  path: '',
  pathMatch: 'full',
  canActivate: [CanActivateFunctionGuard],
  component: ConfigurationsComponent,
  data: {
    authorities: ['ROLE_Administrator', 'FUNCTION_Configuration.ConfigurationAdministration']
  }
}, {
  path: 'new',
  pathMatch: 'full',
  canActivate: [CanActivateFunctionGuard],
  component: NewConfigurationComponent,
  data: {
    authorities: ['ROLE_Administrator', 'FUNCTION_Configuration.ConfigurationAdministration']
  },
  resolve: {
    title: NewConfigurationTitleResolver
  }
}, {
  path: ':key',
  pathMatch: 'full',
  redirectTo: ':key/edit'
}, {
  path: ':key',
  resolve: {
    title: ConfigurationTitleResolver
  },
  children: [{
    path: 'edit',
    canActivate: [CanActivateFunctionGuard],
    component: EditConfigurationComponent,
    data: {
      authorities: ['ROLE_Administrator', 'FUNCTION_Configuration.ConfigurationAdministration']
    },
    resolve: {
      title: EditConfigurationTitleResolver
    }
  }
  ]
}
];

@NgModule({
  declarations: [

    // Components
    ConfigurationsComponent, EditConfigurationComponent, NewConfigurationComponent
  ],
  imports: [

    // Angular modules
    CommonModule, FormsModule, ReactiveFormsModule, RouterModule.forChild(routes),

    // Inception modules
    ConfigurationModule.forRoot(), CoreModule.forRoot(), DialogModule.forRoot(), LayoutModule.forRoot()
  ],
  providers: [

    // Resolvers
    ConfigurationTitleResolver, ConfigurationsTitleResolver, EditConfigurationTitleResolver,
    NewConfigurationTitleResolver
  ]
})
export class ConfigurationViewsModule {
}
