import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { COMMANDE_ROUTE } from './commande.route';
import { CommandeComponent } from './commande.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [CommandeComponent],
  imports: [CommonModule, RouterModule.forChild([COMMANDE_ROUTE]), SharedModule],
})
export class CommandeModule {}
