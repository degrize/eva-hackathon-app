import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { ICommentaire, NewCommentaire } from '../../models/commentaire.model';
import { DATE_FORMAT } from '../../../config/input.constants';
import dayjs from 'dayjs/esm';

export type PartialUpdateCommentaire = Partial<ICommentaire> & Pick<ICommentaire, 'id'>;

type RestOf<T extends ICommentaire | NewCommentaire> = Omit<T, ''> & {};

export type RestCommentaire = RestOf<ICommentaire>;

export type NewRestCommentaire = RestOf<NewCommentaire>;

export type PartialUpdateRestCommentaire = RestOf<PartialUpdateCommentaire>;

export type EntityResponseType = HttpResponse<ICommentaire>;
export type EntityArrayResponseType = HttpResponse<ICommentaire[]>;

@Injectable({
  providedIn: 'root',
})
export class CommentairesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/commentaires');
  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getCommentaireList(): Observable<HttpResponse<any>> {
    return this.http.get<ICommentaire[]>(`${this.resourceUrl}/liste`, { observe: 'response' });
  }

  addNewComment(postCommented: NewCommentaire) {
    this.create(postCommented);
  }

  create(commentaire: NewCommentaire): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commentaire);
    console.log('nnkk');
    return this.http
      .post<RestCommentaire>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  protected convertDateFromClient<T extends ICommentaire | NewCommentaire | PartialUpdateCommentaire>(commentaire: T): RestOf<T> {
    return {
      ...commentaire,
    };
  }

  protected convertDateFromServer(restCommentaire: RestCommentaire): ICommentaire {
    return {
      ...restCommentaire,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCommentaire>): HttpResponse<ICommentaire> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }
}
