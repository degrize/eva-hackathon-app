import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PostulantComponent } from './list/postulant.component';
import { PostulantDetailComponent } from './detail/postulant-detail.component';
import { PostulantUpdateComponent } from './update/postulant-update.component';
import { PostulantDeleteDialogComponent } from './delete/postulant-delete-dialog.component';
import { PostulantRoutingModule } from './route/postulant-routing.module';

@NgModule({
  imports: [SharedModule, PostulantRoutingModule],
  declarations: [PostulantComponent, PostulantDetailComponent, PostulantUpdateComponent, PostulantDeleteDialogComponent],
})
export class PostulantModule {}
