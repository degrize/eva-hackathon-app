import { Injectable } from '@angular/core';
import { BehaviorSubject, delay, map, Observable, tap } from 'rxjs';
import { SAnnonce } from '../models/s-annonce.model';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../core/config/application-config.service';

@Injectable()
export class AnnonceSearchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/annonces');

  constructor(private http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  private _loading$ = new BehaviorSubject<boolean>(false);
  get loading$(): Observable<boolean> {
    return this._loading$.asObservable();
  }

  private _annonces$ = new BehaviorSubject<SAnnonce[]>([]);
  get annonces$(): Observable<SAnnonce[]> {
    return this._annonces$.asObservable();
  }

  private setLoadingStatus(loading: boolean) {
    this._loading$.next(loading);
  }

  private lastAnnoncesLoad = 0;

  getAnnoncesFromServer() {
    if (Date.now() - this.lastAnnoncesLoad <= 300000) {
      return;
    }
    this.setLoadingStatus(true);
    this.http
      .get<SAnnonce[]>(`${this.resourceUrl}/search-list`)
      .pipe(
        delay(1000),
        tap(annonces => {
          this.lastAnnoncesLoad = Date.now();
          this._annonces$.next(annonces);
          this.setLoadingStatus(false);
        })
      )
      .subscribe();
  }

  getAnnonceById(id: number): Observable<SAnnonce> {
    if (!this.lastAnnoncesLoad) {
      this.getAnnoncesFromServer();
    }
    return this.annonces$.pipe(map(annonces => annonces.filter(annonce => annonce.id === id)[0]));
  }
}
