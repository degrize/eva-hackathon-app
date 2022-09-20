import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, map, Observable, startWith, switchMap } from 'rxjs';
import { GlobalSearchService } from './global-search.service';
import { SAnnonce } from '../liste-annonces/models/s-annonce.model';
import { AnnonceSearchType } from '../liste-annonces/enums/annonce-search-type.enum';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';

@Component({
  selector: 'jhi-global-search',
  templateUrl: './global-search.component.html',
  styleUrls: ['./global-search.component.scss'],
})
export class GlobalSearchComponent implements OnInit {
  loading$!: Observable<boolean>;
  mandataireDelegateurs$!: Observable<IMandataireDelegateur[]>;

  params: any;
  searchInput: any;

  constructor(private route: ActivatedRoute, private router: Router, private globalSearchService: GlobalSearchService) {}

  ngOnInit(): void {
    this.initObservables();
    this.route.queryParams.subscribe(params => {
      this.searchInput = params['nomprenom'];
      // on recuperes les données depuis la base
      this.globalSearchService.getMandataireDelegateursFromServer(this.searchInput);
    });
  }

  private initObservables() {
    this.loading$ = this.globalSearchService.loading$;
    this.mandataireDelegateurs$ = this.globalSearchService.mandataireDelegateurs$;
  }
}
