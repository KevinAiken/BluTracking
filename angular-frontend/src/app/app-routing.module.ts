import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AssetsComponent } from './assets/asset.component';
import { LogsComponent } from './logs/logs.component';
import {AssetDetailComponent} from './asset-detail/asset-detail.component';
import { AddAssetComponent} from './add-asset/add-asset.component';
import {AboutComponent} from './about/about.component';

const routes: Routes = [
  { path: '', redirectTo: '/asset', pathMatch: 'full' },
  { path: 'asset', component: AssetsComponent },
  { path: 'logs', component: LogsComponent },
  { path: 'asset/:id', component: AssetDetailComponent},
  { path: 'add-asset', component: AddAssetComponent},
  { path: 'about', component: AboutComponent}
];
@NgModule({
  exports: [ RouterModule ],
  imports: [ RouterModule.forRoot(routes)]
})
export class AppRoutingModule { }
