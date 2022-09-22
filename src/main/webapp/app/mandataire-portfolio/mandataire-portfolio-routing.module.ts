import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MesEchangesComponent } from '../mes-echanges/mes-echanges.component';
import { MandatairePortfolioComponent } from './mandataire-portfolio.component';
import { MandataireDelegateurRoutingResolveService } from '../entities/mandataire-delegateur/route/mandataire-delegateur-routing-resolve.service';
import { AnnonceSearchService } from '../liste-annonces/services/annonce-search.service';
import { SharedModule } from '../shared/shared.module';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { NgSelectModule } from '@ng-select/ng-select';
import { ListeAnnoncesComponent } from '../liste-annonces/liste-annonces.component';
import { SingleAnnonceComponent } from '../liste-annonces/single-annonce/single-annonce.component';

const routes: Routes = [
  {
    path: ':id/view',
    component: MandatairePortfolioComponent,
    resolve: {
      mandataireDelegateur: MandataireDelegateurRoutingResolveService,
    },
    data: {
      pageTitle: 'mandatairePortfolioComponent.title',
    },
  },
];

@NgModule({
  declarations: [MandatairePortfolioComponent],
  imports: [
    RouterModule.forChild(routes),
    SharedModule,
    NgxDatatableModule,
    ProgressbarModule,
    BsDropdownModule,
    TooltipModule,
    NgSelectModule,
  ],
  providers: [AnnonceSearchService],
})
export class MandatairePortfolioRoutingModule {}
