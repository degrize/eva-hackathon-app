import { Route } from '@angular/router';

import { AProposComponent } from './a-propos.component';

export const APROPOS_ROUTE: Route = {
  path: '',
  component: AProposComponent,
  data: {
    pageTitle: 'a-propos.title',
  },
};
