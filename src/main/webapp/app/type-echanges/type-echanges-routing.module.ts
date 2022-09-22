import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MesEchangesComponent } from '../mes-echanges/mes-echanges.component';
import { TypeEchangesComponent } from './type-echanges.component';
import { Authority } from '../config/authority.constants';
import { UserRouteAccessService } from '../core/auth/user-route-access.service';

const routes: Routes = [
  {
    path: '',
    component: TypeEchangesComponent,
    data: {
      pageTitle: 'typeEchanges.title',
    },
  },

  {
    path: 'postes',
    data: {
      authorities: [Authority.USER],
    },
    canActivate: [UserRouteAccessService],
    loadChildren: () => import(`../mes-echanges/mes-echanges.module`).then(m => m.MesEchangesModule),
  },

  {
    path: 'demandes',
    data: {
      authorities: [Authority.USER],
    },
    canActivate: [UserRouteAccessService],
    loadChildren: () => import(`../annonces-demandes/annonces-demandes.module`).then(m => m.AnnoncesDemandesModule),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TypeEchangesRoutingModule {}
