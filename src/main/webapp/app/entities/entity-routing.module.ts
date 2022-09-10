import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'annonce',
        data: { pageTitle: 'evaHackathonApp.annonce.home.title' },
        loadChildren: () => import('./annonce/annonce.module').then(m => m.AnnonceModule),
      },
      {
        path: 'message',
        data: { pageTitle: 'evaHackathonApp.message.home.title' },
        loadChildren: () => import('./message/message.module').then(m => m.MessageModule),
      },
      {
        path: 'categorie',
        data: { pageTitle: 'evaHackathonApp.categorie.home.title' },
        loadChildren: () => import('./categorie/categorie.module').then(m => m.CategorieModule),
      },
      {
        path: 'mandataire-delegateur',
        data: { pageTitle: 'evaHackathonApp.mandataireDelegateur.home.title' },
        loadChildren: () => import('./mandataire-delegateur/mandataire-delegateur.module').then(m => m.MandataireDelegateurModule),
      },
      {
        path: 'postulant',
        data: { pageTitle: 'evaHackathonApp.postulant.home.title' },
        loadChildren: () => import('./postulant/postulant.module').then(m => m.PostulantModule),
      },
      {
        path: 'transaction',
        data: { pageTitle: 'evaHackathonApp.transaction.home.title' },
        loadChildren: () => import('./transaction/transaction.module').then(m => m.TransactionModule),
      },
      {
        path: 'souscription',
        data: { pageTitle: 'evaHackathonApp.souscription.home.title' },
        loadChildren: () => import('./souscription/souscription.module').then(m => m.SouscriptionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
