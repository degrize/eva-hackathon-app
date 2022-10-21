import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MultipleSlideComponent } from './multiple-slide.component';

@NgModule({
  declarations: [MultipleSlideComponent],
  exports: [MultipleSlideComponent],
  imports: [CommonModule],
})
export class MultipleSlideModule {}
