import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnoncesDemandesComponent } from './annonces-demandes.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { ANNONCES_DEMANDES_ROUTE } from './annonces-demandes.route';

@NgModule({
  declarations: [AnnoncesDemandesComponent],
  imports: [CommonModule, RouterModule.forChild([ANNONCES_DEMANDES_ROUTE]), SharedModule, AccordionModule],
})
export class AnnoncesDemandesModule {}
