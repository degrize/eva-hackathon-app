import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VIEPRIVE_ROUTE } from './vie-prive.route';
import { ViePriveComponent } from './vie-prive.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [ViePriveComponent],
  imports: [CommonModule, RouterModule.forChild([VIEPRIVE_ROUTE]), SharedModule],
})
export class ViePriveModule {}
