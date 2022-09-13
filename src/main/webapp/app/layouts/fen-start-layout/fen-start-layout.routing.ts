import { Routes } from '@angular/router';
import { navbarRoute } from '../navbar/navbar.route';

export const FenStartLayoutRoutes: Routes = [
  {
    path: 'account',
    loadChildren: () => import('../../account/account.module').then(m => m.AccountModule),
  },
  {
    path: 'login',
    loadChildren: () => import('../../login/login.module').then(m => m.LoginModule),
  },

  {
    path: 'a-propos',
    loadChildren: () => import(`../../a-propos/a-propos.module`).then(m => m.AProposModule),
  },
  {
    path: 'vie-privee',
    loadChildren: () => import(`../../vie-prive/vie-prive.module`).then(m => m.ViePriveModule),
  },
  {
    path: 'regle-conditions',
    loadChildren: () => import(`../../regle-condition/regle-condition.module`).then(m => m.RegleConditionModule),
  },

  {
    path: 'aide',
    loadChildren: () => import(`../../aide/aide.module`).then(m => m.AideModule),
  },

  navbarRoute,
];
