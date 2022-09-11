import { Route } from '@angular/router';

import { AideComponent } from './aide.component';

export const AIDE_ROUTE: Route = {
  path: '',
  component: AideComponent,
  data: {
    pageTitle: 'aide.title',
  },
};
