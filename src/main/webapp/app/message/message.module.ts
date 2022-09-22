import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageComponent } from './message.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { MESSAGE_ROUTE } from './message.route';

@NgModule({
  declarations: [MessageComponent],
  imports: [CommonModule, RouterModule.forChild([MESSAGE_ROUTE]), SharedModule],
})
export class MessageModule {}
