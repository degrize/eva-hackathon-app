import { Route } from '@angular/router';

import { ListeAnnoncesComponent } from './liste-annonces.component';

export const LISTE_ANNONCES_ROUTE: Route = {
  path: '',
  component: ListeAnnoncesComponent,
  data: {
    pageTitle: 'listeAnnonces.title',
  },
};
