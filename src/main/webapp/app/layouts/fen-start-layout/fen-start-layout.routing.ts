import { Routes } from '@angular/router';
import { navbarRoute } from '../navbar/navbar.route';
import { GlobalSearchComponent } from '../../global-search/global-search.component';

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
    path: 'vie-prive',
    loadChildren: () => import(`../../vie-prive/vie-prive.module`).then(m => m.ViePriveModule),
  },
  {
    path: 'regle-condition',
    loadChildren: () => import(`../../regle-condition/regle-condition.module`).then(m => m.RegleConditionModule),
  },
  {
    path: 'remboursement',
    loadChildren: () => import(`../../remboursement/remboursement.module`).then(m => m.RemboursementModule),
  },

  {
    path: 'paiement',
    loadChildren: () => import(`../../paiement/paiement.module`).then(m => m.PaiementModule),
  },

  {
    path: 'aide',
    loadChildren: () => import(`../../aide/aide.module`).then(m => m.AideModule),
  },
  {
    path: 'liste-annonces',
    loadChildren: () => import(`../../liste-annonces/liste-annonces.module`).then(m => m.ListeAnnoncesModule),
  },
  {
    path: 'create-eva-profile',
    loadChildren: () =>
      import(`../../mandataire-delegateur-create-profile/mandataire-delegateur-create-profile.module`).then(
        m => m.MandataireDelegateurCreateProfileModule
      ),
  },
  {
    path: 'search',
    component: GlobalSearchComponent,
  },

  navbarRoute,
];
