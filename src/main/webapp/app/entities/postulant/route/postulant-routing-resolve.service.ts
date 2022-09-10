import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPostulant } from '../postulant.model';
import { PostulantService } from '../service/postulant.service';

@Injectable({ providedIn: 'root' })
export class PostulantRoutingResolveService implements Resolve<IPostulant | null> {
  constructor(protected service: PostulantService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPostulant | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((postulant: HttpResponse<IPostulant>) => {
          if (postulant.body) {
            return of(postulant.body);
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
