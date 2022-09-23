import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageComponent } from './message.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { MESSAGE_ROUTE } from './message.route';
import { MessageListComponent } from './message-list/message-list.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [MessageComponent, MessageListComponent],
  imports: [CommonModule, RouterModule.forChild([MESSAGE_ROUTE]), SharedModule],
  providers: [NgbActiveModal],
})
export class MessageModule {}
