import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnnonceComponent } from './list/annonce.component';
import { AnnonceDetailComponent } from './detail/annonce-detail.component';
import { AnnonceUpdateComponent } from './update/annonce-update.component';
import { AnnonceDeleteDialogComponent } from './delete/annonce-delete-dialog.component';
import { AnnonceRoutingModule } from './route/annonce-routing.module';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [SharedModule, AnnonceRoutingModule, NgSelectModule],
  declarations: [AnnonceComponent, AnnonceDetailComponent, AnnonceUpdateComponent, AnnonceDeleteDialogComponent],
})
export class AnnonceModule {}
