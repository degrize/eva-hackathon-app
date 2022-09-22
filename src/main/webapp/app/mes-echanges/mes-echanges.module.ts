import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MesEchangesComponent } from './mes-echanges.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { MES_ECHANGES_ROUTE } from './mes-echanges.route';
import { AccordionModule } from 'ngx-bootstrap/accordion';

@NgModule({
  declarations: [MesEchangesComponent],
  imports: [CommonModule, RouterModule.forChild([MES_ECHANGES_ROUTE]), SharedModule, AccordionModule],
})
export class MesEchangesModule {}
