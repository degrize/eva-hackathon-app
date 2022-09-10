import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MandataireDelegateurComponent } from '../list/mandataire-delegateur.component';
import { MandataireDelegateurDetailComponent } from '../detail/mandataire-delegateur-detail.component';
import { MandataireDelegateurUpdateComponent } from '../update/mandataire-delegateur-update.component';
import { MandataireDelegateurRoutingResolveService } from './mandataire-delegateur-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const mandataireDelegateurRoute: Routes = [
  {
    path: '',
    component: MandataireDelegateurComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MandataireDelegateurDetailComponent,
    resolve: {
      mandataireDelegateur: MandataireDelegateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MandataireDelegateurUpdateComponent,
    resolve: {
      mandataireDelegateur: MandataireDelegateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MandataireDelegateurUpdateComponent,
    resolve: {
      mandataireDelegateur: MandataireDelegateurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mandataireDelegateurRoute)],
  exports: [RouterModule],
})
export class MandataireDelegateurRoutingModule {}
