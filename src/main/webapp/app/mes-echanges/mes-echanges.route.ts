import { Route } from '@angular/router';
import { MesEchangesComponent } from './mes-echanges.component';

export const MES_ECHANGES_ROUTE: Route = {
  path: '',
  component: MesEchangesComponent,
  data: {
    pageTitle: 'mesEchanges.title',
  },
};
