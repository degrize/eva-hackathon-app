import { Route } from '@angular/router';

import { AnnonceComponent } from './annonce.component';

export const ANNONCE_ROUTE: Route = {
  path: '',
  component: AnnonceComponent,
  data: {
    pageTitle: 'annonce.title',
  },
};
