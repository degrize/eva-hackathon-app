import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ANNONCE_ROUTE } from './annonce.route';
import { AnnonceComponent } from './annonce.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [AnnonceComponent],
  imports: [CommonModule, RouterModule.forChild([ANNONCE_ROUTE]), SharedModule],
})
export class AnnonceModule {}
