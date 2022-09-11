import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { APROPOS_ROUTE } from './a-propos.route';
import { AProposComponent } from './a-propos.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [AProposComponent],
  imports: [CommonModule, RouterModule.forChild([APROPOS_ROUTE]), SharedModule],
})
export class AProposModule {}
