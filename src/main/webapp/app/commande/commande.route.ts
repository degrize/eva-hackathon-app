import { Route } from '@angular/router';

import { CommandeComponent } from './commande.component';

export const COMMANDE_ROUTE: Route = {
  path: '',
  component: CommandeComponent,
  data: {
    pageTitle: 'commande.title',
  },
};
