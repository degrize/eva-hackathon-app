import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { REGLECONDITION_ROUTE } from './regle-condition.route';
import { RegleConditionComponent } from './regle-condition.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [RegleConditionComponent],
  imports: [CommonModule, RouterModule.forChild([REGLECONDITION_ROUTE]), SharedModule],
})
export class RegleConditionModule {}
