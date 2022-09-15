import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPostulant, NewPostulant } from '../postulant.model';
import { IAnnonce } from '../../annonce/annonce.model';

export type PartialUpdatePostulant = Partial<IPostulant> & Pick<IPostulant, 'id'>;

export type EntityResponseType = HttpResponse<IPostulant>;
export type EntityArrayResponseType = HttpResponse<IPostulant[]>;

@Injectable({ providedIn: 'root' })
export class PostulantService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/postulants');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(postulant: NewPostulant): Observable<EntityResponseType> {
    return this.http.post<IPostulant>(this.resourceUrl, postulant, { observe: 'response' });
  }

  update(postulant: IPostulant): Observable<EntityResponseType> {
    return this.http.put<IPostulant>(`${this.resourceUrl}/${this.getPostulantIdentifier(postulant)}`, postulant, { observe: 'response' });
  }

  partialUpdate(postulant: PartialUpdatePostulant): Observable<EntityResponseType> {
    return this.http.patch<IPostulant>(`${this.resourceUrl}/${this.getPostulantIdentifier(postulant)}`, postulant, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPostulant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPostulant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getPostulantList(): Observable<HttpResponse<any>> {
    return this.http.get<IPostulant[]>(`${this.resourceUrl}/liste`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPostulantIdentifier(postulant: Pick<IPostulant, 'id'>): number {
    return postulant.id;
  }

  comparePostulant(o1: Pick<IPostulant, 'id'> | null, o2: Pick<IPostulant, 'id'> | null): boolean {
    return o1 && o2 ? this.getPostulantIdentifier(o1) === this.getPostulantIdentifier(o2) : o1 === o2;
  }

  addPostulantToCollectionIfMissing<Type extends Pick<IPostulant, 'id'>>(
    postulantCollection: Type[],
    ...postulantsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const postulants: Type[] = postulantsToCheck.filter(isPresent);
    if (postulants.length > 0) {
      const postulantCollectionIdentifiers = postulantCollection.map(postulantItem => this.getPostulantIdentifier(postulantItem)!);
      const postulantsToAdd = postulants.filter(postulantItem => {
        const postulantIdentifier = this.getPostulantIdentifier(postulantItem);
        if (postulantCollectionIdentifiers.includes(postulantIdentifier)) {
          return false;
        }
        postulantCollectionIdentifiers.push(postulantIdentifier);
        return true;
      });
      return [...postulantsToAdd, ...postulantCollection];
    }
    return postulantCollection;
  }
}
