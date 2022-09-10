import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISouscription, NewSouscription } from '../souscription.model';

export type PartialUpdateSouscription = Partial<ISouscription> & Pick<ISouscription, 'id'>;

export type EntityResponseType = HttpResponse<ISouscription>;
export type EntityArrayResponseType = HttpResponse<ISouscription[]>;

@Injectable({ providedIn: 'root' })
export class SouscriptionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/souscriptions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(souscription: NewSouscription): Observable<EntityResponseType> {
    return this.http.post<ISouscription>(this.resourceUrl, souscription, { observe: 'response' });
  }

  update(souscription: ISouscription): Observable<EntityResponseType> {
    return this.http.put<ISouscription>(`${this.resourceUrl}/${this.getSouscriptionIdentifier(souscription)}`, souscription, {
      observe: 'response',
    });
  }

  partialUpdate(souscription: PartialUpdateSouscription): Observable<EntityResponseType> {
    return this.http.patch<ISouscription>(`${this.resourceUrl}/${this.getSouscriptionIdentifier(souscription)}`, souscription, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISouscription>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISouscription[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSouscriptionIdentifier(souscription: Pick<ISouscription, 'id'>): number {
    return souscription.id;
  }

  compareSouscription(o1: Pick<ISouscription, 'id'> | null, o2: Pick<ISouscription, 'id'> | null): boolean {
    return o1 && o2 ? this.getSouscriptionIdentifier(o1) === this.getSouscriptionIdentifier(o2) : o1 === o2;
  }

  addSouscriptionToCollectionIfMissing<Type extends Pick<ISouscription, 'id'>>(
    souscriptionCollection: Type[],
    ...souscriptionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const souscriptions: Type[] = souscriptionsToCheck.filter(isPresent);
    if (souscriptions.length > 0) {
      const souscriptionCollectionIdentifiers = souscriptionCollection.map(
        souscriptionItem => this.getSouscriptionIdentifier(souscriptionItem)!
      );
      const souscriptionsToAdd = souscriptions.filter(souscriptionItem => {
        const souscriptionIdentifier = this.getSouscriptionIdentifier(souscriptionItem);
        if (souscriptionCollectionIdentifiers.includes(souscriptionIdentifier)) {
          return false;
        }
        souscriptionCollectionIdentifiers.push(souscriptionIdentifier);
        return true;
      });
      return [...souscriptionsToAdd, ...souscriptionCollection];
    }
    return souscriptionCollection;
  }
}
