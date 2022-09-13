import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PAIEMENT_ROUTE } from './paiement.route';
import { PaiementComponent } from './paiement.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [PaiementComponent],
  imports: [CommonModule, RouterModule.forChild([PAIEMENT_ROUTE]), SharedModule],
})
export class PaiementModule {}
