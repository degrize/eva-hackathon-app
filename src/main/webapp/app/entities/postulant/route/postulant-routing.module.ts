import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PostulantComponent } from '../list/postulant.component';
import { PostulantDetailComponent } from '../detail/postulant-detail.component';
import { PostulantUpdateComponent } from '../update/postulant-update.component';
import { PostulantRoutingResolveService } from './postulant-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const postulantRoute: Routes = [
  {
    path: '',
    component: PostulantComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PostulantDetailComponent,
    resolve: {
      postulant: PostulantRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PostulantUpdateComponent,
    resolve: {
      postulant: PostulantRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PostulantUpdateComponent,
    resolve: {
      postulant: PostulantRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(postulantRoute)],
  exports: [RouterModule],
})
export class PostulantRoutingModule {}
