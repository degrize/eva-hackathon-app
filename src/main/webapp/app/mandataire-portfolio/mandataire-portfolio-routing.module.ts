import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MesEchangesComponent } from '../mes-echanges/mes-echanges.component';
import { MandatairePortfolioComponent } from './mandataire-portfolio.component';

const routes: Routes = [
  {
    path: '',
    component: MandatairePortfolioComponent,
    data: {
      pageTitle: 'mandatairePortfolioComponent.title',
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MandatairePortfolioRoutingModule {}
