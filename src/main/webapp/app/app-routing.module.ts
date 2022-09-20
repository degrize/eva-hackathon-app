import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { errorRoute } from './layouts/error/error.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

import { FenStartLayoutComponent } from './layouts/fen-start-layout/fen-start-layout.component';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: '',
          component: FenStartLayoutComponent,
          children: [
            {
              path: '',
              loadChildren: () => import('./layouts/fen-start-layout/fen-start-layout.module').then(m => m.FenStartLayoutModule),
            },
          ],
        },
        {
          path: '',
          component: AdminLayoutComponent,
          children: [
            {
              path: '',
              loadChildren: () => import('./layouts/admin-layout/admin-layout.module').then(m => m.AdminLayoutModule),
            },
          ],
        },

        ...errorRoute,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
