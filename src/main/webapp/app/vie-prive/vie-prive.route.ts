import { Route } from '@angular/router';

import { ViePriveComponent } from './vie-prive.component';

export const VIEPRIVE_ROUTE: Route = {
  path: '',
  component: ViePriveComponent,
  data: {
    pageTitle: 'vie-prive.title',
  },
};
