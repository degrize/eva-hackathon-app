import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IAdminStatistics } from '../../entities/enumerations/admin-statistics';

export type EntityArrayResponseType = HttpResponse<IAdminStatistics[]>;

@Injectable({ providedIn: 'root' })
export class DashboardService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/statistques');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getAdminStatistique(): Observable<HttpResponse<any>> {
    return this.http.get<IAdminStatistics>(`${this.resourceUrl}`, { observe: 'response' });
  }
}
