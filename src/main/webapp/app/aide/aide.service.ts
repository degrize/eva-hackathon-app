import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IAide, NewAide } from './aide.model';
import { NewCategorie } from '../entities/categorie/categorie.model';

export type EntityResponseType = HttpResponse<IAide>;
export type EntityArrayResponseType = HttpResponse<IAide[]>;

@Injectable({ providedIn: 'root' })
export class AideService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/aide');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(aide: IAide | (Omit<IAide, 'id'> & { id: null })): Observable<EntityResponseType> {
    return this.http.post<IAide>(this.resourceUrl, aide, { observe: 'response' });
  }
}
