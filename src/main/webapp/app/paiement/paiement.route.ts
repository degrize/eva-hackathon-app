import { Route } from '@angular/router';

import { PaiementComponent } from './paiement.component';
import { AnnonceRoutingResolveService } from '../entities/annonce/route/annonce-routing-resolve.service';

export const PAIEMENT_ROUTE: Route = {
  path: ':id',
  component: PaiementComponent,
  data: {
    pageTitle: 'paiement.title',
  },
  resolve: {
    annonce: AnnonceRoutingResolveService,
  },
};
