import { Route } from '@angular/router';
import { MessageComponent } from './message.component';
import { AnnonceRoutingResolveService } from '../entities/annonce/route/annonce-routing-resolve.service';

export const MESSAGE_ROUTE: Route = {
  path: ':id',
  component: MessageComponent,
  data: {
    pageTitle: 'Message.title',
  },
  resolve: {
    annonce: AnnonceRoutingResolveService,
  },
};
