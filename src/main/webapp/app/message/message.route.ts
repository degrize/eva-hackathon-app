import { Route } from '@angular/router';
import { MessageComponent } from './message.component';

export const MESSAGE_ROUTE: Route = {
  path: '',
  component: MessageComponent,
  data: {
    pageTitle: 'Message.title',
  },
};
