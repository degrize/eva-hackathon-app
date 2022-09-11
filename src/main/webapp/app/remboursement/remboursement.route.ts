import { Route } from '@angular/router';

import { RemboursementComponent } from './remboursement.component';

export const REMBOURSEMENT_ROUTE: Route = {
  path: '',
  component: RemboursementComponent,
  data: {
    pageTitle: 'remboursement.title',
  },
};
