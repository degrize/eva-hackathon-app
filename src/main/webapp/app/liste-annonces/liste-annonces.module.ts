import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AideComponent } from '../aide/aide.component';
import { RouterModule } from '@angular/router';
import { AIDE_ROUTE } from '../aide/aide.route';
import { SharedModule } from '../shared/shared.module';
import { LISTE_ANNONCES_ROUTE } from './liste-annonces.route';
import { ListeAnnoncesComponent } from './liste-annonces.component';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

@NgModule({
  declarations: [ListeAnnoncesComponent],
  imports: [CommonModule, RouterModule.forChild([LISTE_ANNONCES_ROUTE]), SharedModule, NgxDatatableModule],
})
export class ListeAnnoncesModule {}
