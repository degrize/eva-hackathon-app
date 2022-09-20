import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MandatairePortfolioRoutingModule } from './mandataire-portfolio-routing.module';
import { MandatairePortfolioComponent } from './mandataire-portfolio.component';

@NgModule({
  declarations: [MandatairePortfolioComponent],
  imports: [CommonModule, MandatairePortfolioRoutingModule],
})
export class MandatairePortfolioModule {}
