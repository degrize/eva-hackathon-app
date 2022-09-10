import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMandataireDelegateur } from '../mandataire-delegateur.model';
import { MandataireDelegateurService } from '../service/mandataire-delegateur.service';

@Injectable({ providedIn: 'root' })
export class MandataireDelegateurRoutingResolveService implements Resolve<IMandataireDelegateur | null> {
  constructor(protected service: MandataireDelegateurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMandataireDelegateur | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mandataireDelegateur: HttpResponse<IMandataireDelegateur>) => {
          if (mandataireDelegateur.body) {
            return of(mandataireDelegateur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
