import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { BehaviorSubject, delay, map, Observable, tap } from 'rxjs';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';

@Injectable({
  providedIn: 'root',
})
export class GlobalSearchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mandataireDelegateurs');

  constructor(private http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  private _loading$ = new BehaviorSubject<boolean>(false);
  get loading$(): Observable<boolean> {
    return this._loading$.asObservable();
  }

  private _mandataireDelegateurs$ = new BehaviorSubject<IMandataireDelegateur[]>([]);
  get mandataireDelegateurs$(): Observable<IMandataireDelegateur[]> {
    return this._mandataireDelegateurs$.asObservable();
  }

  private setLoadingStatus(loading: boolean) {
    this._loading$.next(loading);
  }

  private lastMandataireDelegateursLoad = 0;

  getMandataireDelegateursFromServer(nomprenom: string) {
    if (Date.now() - this.lastMandataireDelegateursLoad <= 300000) {
      return;
    }
    this.setLoadingStatus(true);
    this.http
      .get<IMandataireDelegateur[]>(`${this.resourceUrl}/search/${nomprenom}`)
      .pipe(
        delay(1000),
        tap(mandataireDelegateurs => {
          this.lastMandataireDelegateursLoad = Date.now();
          this._mandataireDelegateurs$.next(mandataireDelegateurs);
          this.setLoadingStatus(false);
        })
      )
      .subscribe();
  }
}
