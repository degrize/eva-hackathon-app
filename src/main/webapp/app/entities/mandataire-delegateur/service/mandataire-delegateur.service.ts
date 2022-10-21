import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMandataireDelegateur, NewMandataireDelegateur } from '../mandataire-delegateur.model';

export type PartialUpdateMandataireDelegateur = Partial<IMandataireDelegateur> & Pick<IMandataireDelegateur, 'id'>;

export type EntityResponseType = HttpResponse<IMandataireDelegateur>;
export type EntityArrayResponseType = HttpResponse<IMandataireDelegateur[]>;

@Injectable({ providedIn: 'root' })
export class MandataireDelegateurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mandataire-delegateurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mandataireDelegateur: NewMandataireDelegateur): Observable<EntityResponseType> {
    return this.http.post<IMandataireDelegateur>(this.resourceUrl, mandataireDelegateur, { observe: 'response' });
  }

  update(mandataireDelegateur: IMandataireDelegateur): Observable<EntityResponseType> {
    return this.http.put<IMandataireDelegateur>(
      `${this.resourceUrl}/${this.getMandataireDelegateurIdentifier(mandataireDelegateur)}`,
      mandataireDelegateur,
      { observe: 'response' }
    );
  }

  partialUpdate(mandataireDelegateur: PartialUpdateMandataireDelegateur): Observable<EntityResponseType> {
    return this.http.patch<IMandataireDelegateur>(
      `${this.resourceUrl}/${this.getMandataireDelegateurIdentifier(mandataireDelegateur)}`,
      mandataireDelegateur,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMandataireDelegateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMandataireDelegateur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMandataireDelegateurIdentifier(mandataireDelegateur: Pick<IMandataireDelegateur, 'id'>): number {
    return mandataireDelegateur.id;
  }

  compareMandataireDelegateur(o1: Pick<IMandataireDelegateur, 'id'> | null, o2: Pick<IMandataireDelegateur, 'id'> | null): boolean {
    return o1 && o2 ? this.getMandataireDelegateurIdentifier(o1) === this.getMandataireDelegateurIdentifier(o2) : o1 === o2;
  }

  addMandataireDelegateurToCollectionIfMissing<Type extends Pick<IMandataireDelegateur, 'id'>>(
    mandataireDelegateurCollection: Type[],
    ...mandataireDelegateursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mandataireDelegateurs: Type[] = mandataireDelegateursToCheck.filter(isPresent);
    if (mandataireDelegateurs.length > 0) {
      const mandataireDelegateurCollectionIdentifiers = mandataireDelegateurCollection.map(
        mandataireDelegateurItem => this.getMandataireDelegateurIdentifier(mandataireDelegateurItem)!
      );
      const mandataireDelegateursToAdd = mandataireDelegateurs.filter(mandataireDelegateurItem => {
        const mandataireDelegateurIdentifier = this.getMandataireDelegateurIdentifier(mandataireDelegateurItem);
        if (mandataireDelegateurCollectionIdentifiers.includes(mandataireDelegateurIdentifier)) {
          return false;
        }
        mandataireDelegateurCollectionIdentifiers.push(mandataireDelegateurIdentifier);
        return true;
      });
      return [...mandataireDelegateursToAdd, ...mandataireDelegateurCollection];
    }
    return mandataireDelegateurCollection;
  }

  findByJhiUserId(req?: any): Observable<HttpResponse<IMandataireDelegateur>> {
    const params: HttpParams = createRequestOption(req);
    params.set('login', req.login);
    return this.http.get<IMandataireDelegateur>(this.applicationConfigService.getEndpointFor('api/mandataire-delegateurs/account'), {
      params,
      observe: 'response',
    });
  }

  genarateExcel(id: number): void {
    window.open(`${this.resourceUrl}/excel/${id}`, '_blank');
  }
}
