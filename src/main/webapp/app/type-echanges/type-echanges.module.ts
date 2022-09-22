import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TypeEchangesRoutingModule } from './type-echanges-routing.module';
import { TypeEchangesComponent } from './type-echanges.component';

@NgModule({
  declarations: [TypeEchangesComponent],
  imports: [CommonModule, TypeEchangesRoutingModule],
})
export class TypeEchangesModule {}
