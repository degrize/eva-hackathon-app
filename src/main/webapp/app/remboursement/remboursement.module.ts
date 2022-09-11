import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { REMBOURSEMENT_ROUTE } from './remboursement.route';
import { RemboursementComponent } from './remboursement.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [RemboursementComponent],
  imports: [CommonModule, RouterModule.forChild([REMBOURSEMENT_ROUTE]), SharedModule],
})
export class RemboursementModule {}
