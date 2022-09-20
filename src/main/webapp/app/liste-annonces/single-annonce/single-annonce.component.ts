import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AnnonceSearchService } from '../services/annonce-search.service';
import { SAnnonce } from '../models/s-annonce.model';
import { Observable, switchMap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-single-annonce',
  templateUrl: './single-annonce.component.html',
  styleUrls: ['./single-annonce.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SingleAnnonceComponent implements OnInit {
  loading$!: Observable<boolean>;
  annonce$!: Observable<SAnnonce>;

  constructor(private annoncesService: AnnonceSearchService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.initObservables();
  }

  private initObservables() {
    this.loading$ = this.annoncesService.loading$;

    this.annonce$ = this.route.params.pipe(switchMap(params => this.annoncesService.getAnnonceById(+params['id'])));
  }

  onHire() {}

  onRefuse() {}

  onGoBack() {
    this.router.navigateByUrl('/liste-annonces');
  }

  private start_javaScript(): void {}
}
