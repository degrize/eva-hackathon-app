import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AIDE_ROUTE } from './aide.route';
import { AideComponent } from './aide.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [AideComponent],
  imports: [CommonModule, RouterModule.forChild([AIDE_ROUTE]), SharedModule],
})
export class AideModule {}
