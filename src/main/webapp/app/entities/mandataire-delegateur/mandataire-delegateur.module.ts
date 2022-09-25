import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MandataireDelegateurComponent } from './list/mandataire-delegateur.component';
import { MandataireDelegateurDetailComponent } from './detail/mandataire-delegateur-detail.component';
import { MandataireDelegateurUpdateComponent } from './update/mandataire-delegateur-update.component';
import { MandataireDelegateurDeleteDialogComponent } from './delete/mandataire-delegateur-delete-dialog.component';
import { MandataireDelegateurRoutingModule } from './route/mandataire-delegateur-routing.module';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [SharedModule, MandataireDelegateurRoutingModule, NgSelectModule],
  declarations: [
    MandataireDelegateurComponent,
    MandataireDelegateurDetailComponent,
    MandataireDelegateurUpdateComponent,
    MandataireDelegateurDeleteDialogComponent,
  ],
})
export class MandataireDelegateurModule {}
