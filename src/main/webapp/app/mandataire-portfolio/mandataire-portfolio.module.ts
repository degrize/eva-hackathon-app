import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MandatairePortfolioComponent } from './mandataire-portfolio.component';
import { AnnonceSearchService } from '../liste-annonces/services/annonce-search.service';
import { SharedModule } from '../shared/shared.module';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [CommonModule, SharedModule, NgxDatatableModule, ProgressbarModule, BsDropdownModule, TooltipModule, NgSelectModule],
  providers: [AnnonceSearchService],
})
export class MandatairePortfolioModule {}
