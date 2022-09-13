import { Route } from '@angular/router';

import { PaiementComponent } from './paiement.component';

export const PAIEMENT_ROUTE: Route = {
  path: '',
  component: PaiementComponent,
  data: {
    pageTitle: 'paiement.title',
  },
};
