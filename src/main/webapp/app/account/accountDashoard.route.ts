import { Routes } from '@angular/router';

import { passwordRoute } from './password/password.route';
import { settingsRoute } from './settings/settings.route';

const ACCOUNT_ROUTES = [passwordRoute, settingsRoute];

export const accountDashoardState: Routes = [
  {
    path: '',
    children: ACCOUNT_ROUTES,
  },
];
