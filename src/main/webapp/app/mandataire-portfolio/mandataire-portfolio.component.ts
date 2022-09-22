import { ChangeDetectionStrategy, Component, EventEmitter, OnInit, Output } from '@angular/core';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';
import { ActivatedRoute } from '@angular/router';
import { combineLatest, map, Observable, startWith } from 'rxjs';
import { SAnnonce } from '../liste-annonces/models/s-annonce.model';
import { AnnonceSearchService } from '../liste-annonces/services/annonce-search.service';
import { AnnonceSearchType } from '../liste-annonces/enums/annonce-search-type.enum';
import { HttpResponse } from '@angular/common/http';
import { IAnnonce } from '../entities/annonce/annonce.model';
import { ICommentaire } from '../shared/models/commentaire.model';
import { CommentairesService } from '../shared/commentaires/services/commentaires.service';
import { CommentaireFormService } from '../shared/commentaires/commentaires-form.service';

declare var jquery: any;
declare var $: any;

@Component({
  selector: 'jhi-mandataire-portfolio',
  templateUrl: './mandataire-portfolio.component.html',
  styleUrls: ['./mandataire-portfolio.component.scss'],
})
export class MandatairePortfolioComponent implements OnInit {
  mandataireDelegateur: IMandataireDelegateur | null = null;
  @Output() postCommented = new EventEmitter<ICommentaire>();

  loading$!: Observable<boolean>;
  annonces$!: Observable<SAnnonce[]>;
  commentaires?: ICommentaire[];

  constructor(
    protected activatedRoute: ActivatedRoute,
    private annonceSearchService: AnnonceSearchService,
    private commentaireService: CommentairesService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mandataireDelegateur }) => {
      this.mandataireDelegateur = mandataireDelegateur;
      console.log(mandataireDelegateur);
    });

    this.start_javascript();

    this.initObservables();
    this.annonceSearchService.getAnnoncesFromServer();

    this.loadCommentiareList();
  }

  onNewComment(comment: ICommentaire) {
    console.log('OK OK OK');
  }

  previousState(): void {
    window.history.back();
  }

  loadCommentiareList(): void {
    this.commentaireService.getCommentaireList().subscribe(
      (res: HttpResponse<ICommentaire[]>) => {
        this.commentaires = res.body ?? [];
        this.onSuccess();
      },
      () => {
        this.onError();
      }
    );
  }

  protected onSuccess(): void {
    //
  }

  protected onError(): void {
    //
  }

  start_javascript(): void {
    $(document).ready(function () {
      // slide-up script
      $('.scroll-up-btn').click(function () {
        $('html').animate({ scrollTop: 0 });
        // removing smooth scroll on slide-up button click
        $('html').css('scrollBehavior', 'auto');
      });

      $('.navbar .menu li a').click(function () {
        // applying again smooth scroll on menu items click
        $('html').css('scrollBehavior', 'smooth');
      });

      // toggle menu/navbar script
      $('.menu-btn').click(function () {
        $('.navbar .menu').toggleClass('active');
        $('.menu-btn i').toggleClass('active');
      });

      // typing text animation script
    });
  }

  private initObservables() {
    this.loading$ = this.annonceSearchService.loading$;
    this.annonces$ = this.annonceSearchService.annonces$;
  }
}
