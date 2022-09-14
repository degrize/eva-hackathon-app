import { Route } from '@angular/router';

import { MandataireDelegateurCreateProfileComponent } from './mandataire-delegateur-create-profile.component';

export const MANDATAIRE_DELEGATAIRE_CREATE_PROFILE_ROUTE: Route = {
  path: '',
  component: MandataireDelegateurCreateProfileComponent,
  data: {
    pageTitle: 'mandataireDelegateurCreateProfileComponent.title',
  },
};
