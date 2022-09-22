import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { ListeAnnoncesComponent } from './liste-annonces.component';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { AnnonceSearchService } from './services/annonce-search.service';
import { SingleAnnonceComponent } from './single-annonce/single-annonce.component';
import { NgSelectModule } from '@ng-select/ng-select';

const routes: Routes = [
  { path: ':id', component: SingleAnnonceComponent },
  { path: '', pathMatch: 'full', component: ListeAnnoncesComponent },
];

@NgModule({
  declarations: [ListeAnnoncesComponent, SingleAnnonceComponent],
  imports: [
    RouterModule.forChild(routes),
    SharedModule,
    NgxDatatableModule,
    ProgressbarModule,
    BsDropdownModule,
    TooltipModule,
    NgSelectModule,
  ],
  providers: [AnnonceSearchService],
})
export class ListeAnnoncesModule {}
