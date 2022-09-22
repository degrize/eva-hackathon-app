import { Route } from '@angular/router';
import { AnnoncesDemandesComponent } from './annonces-demandes.component';

export const ANNONCES_DEMANDES_ROUTE: Route = {
  path: '',
  component: AnnoncesDemandesComponent,
  data: {
    pageTitle: 'annoncesDemandes.title',
  },
};
