import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISouscription } from '../souscription.model';
import { SouscriptionService } from '../service/souscription.service';

@Injectable({ providedIn: 'root' })
export class SouscriptionRoutingResolveService implements Resolve<ISouscription | null> {
  constructor(protected service: SouscriptionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISouscription | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((souscription: HttpResponse<ISouscription>) => {
          if (souscription.body) {
            return of(souscription.body);
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
