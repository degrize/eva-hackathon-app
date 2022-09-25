import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Observable, Subject, switchMap, tap } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

import Swal from 'sweetalert2';
import { MandataireDelegateurService } from '../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { HttpResponse } from '@angular/common/http';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';
import { MandataireDelegateurComponent } from '../entities/mandataire-delegateur/list/mandataire-delegateur.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnnonceService, EntityArrayResponseType } from '../entities/annonce/service/annonce.service';
import { IAnnonce } from '../entities/annonce/annonce.model';
import { ICategorie } from '../entities/categorie/categorie.model';
import { CategorieService } from '../entities/categorie/service/categorie.service';
import List from 'list.js';
import { EtatCompte } from '../entities/enumerations/etat-compte.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;

  photoAnnonce = '';
  annonces?: IAnnonce[];
  categories?: ICategorie[];
  comptePremium = EtatCompte.PREMIUM;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService,
    private router: Router,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute,
    protected annonceService: AnnonceService,
    protected categorieService: CategorieService
  ) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => {
        this.account = account;
        if (account !== null) {
          this.mandataireDelegateurService.findByJhiUserId({ login: this.account?.login }).subscribe(
            (res: HttpResponse<IMandataireDelegateur>) => this.onSucessUser(res.body),
            (res: HttpResponse<any>) => this.onError()
          );
        }
      });

    this.loadAnnonceList();
    this.loadCategorieList();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  compteEvaSwal() {
    Swal.fire({
      showCancelButton: false,
      showConfirmButton: false,
      html:
        ' <div id="card-pricing-component" class="tab-pane tab-example-result fade show active" role="tabpanel" aria-labelledby="card-pricing-component-tab">\n' +
        '    <div class="card card-pricing bg-gradient-success border-0 text-center mb-4">\n' +
        '      <div class="card-header bg-transparent">\n' +
        '        <h4 class="text-uppercase ls-1 text-white py-3 mb-0"> ' +
        '' +
        this.account?.login +
        ', finalisez votre profil EVA </h4>\n' +
        '      </div>\n' +
        '      <div class="card-body px-lg-7">\n' +
        '        <div class="display-4 text-white">PROFIL EVA</div>\n' +
        '        <span class=" text-white">Veuillez suivre ces étapes </span>\n' +
        '        <ul class="list-unstyled my-4">\n' +
        '          <li>\n' +
        '            <div class="d-flex align-items-center mb-1">\n' +
        '              <div>\n' +
        '                <div class="icon icon-xs icon-shape bg-white shadow rounded-circle">\n' +
        '                  <i class="fas fa-terminal"></i>\n' +
        '                </div>\n' +
        '              </div>\n' +
        '              <div>\n' +
        '                <span class="pl-2 text-sm text-white">Completez vos informations EVA</span>\n' +
        '              </div>\n' +
        '            </div>\n' +
        '          </li>\n' +
        '          <li>\n' +
        '            <div class="d-flex align-items-center mb-1">\n' +
        '              <div>\n' +
        '                <div class="icon icon-xs icon-shape bg-white shadow rounded-circle">\n' +
        '                  <i class="fas fa-pen-fancy"></i>\n' +
        '                </div>\n' +
        '              </div>\n' +
        '              <div>\n' +
        '                <span class="pl-2 text-sm text-white">(Poster - Postuler) une annonce - </span>\n' +
        '              </div>\n' +
        '            </div>\n' +
        '          </li>\n' +
        '          <li>\n' +
        '            <div class="d-flex align-items-center mb-1">\n' +
        '              <div>\n' +
        '                <div class="icon icon-xs icon-shape bg-white shadow rounded-circle">\n' +
        '                  <i class="fas fa-hdd"></i>\n' +
        '                </div>\n' +
        '              </div>\n' +
        '              <div>\n' +
        '                <span class="pl-2 text-sm text-white">(Envoyer - Reçevoir) de l\'argent </span>\n' +
        '              </div>\n' +
        '            </div>\n' +
        '          </li>\n' +
        '        </ul>\n' +
        '        <a href="/create-eva-profile" class="btn btn-primary mb-3">Demarrer maintenant</a>\n' +
        '      </div>\n' +
        '    </div>\n' +
        '  </div>',
    });
  }

  loadAnnonceList(): void {
    this.annonceService.getAnnonceList().subscribe(
      (res: HttpResponse<IAnnonce[]>) => {
        this.annonces = res.body ?? [];
        new List('users', {
          valueNames: ['name', 'budget', 'status', 'completion'],
          listClass: 'list',
        });

        this.onSuccess();
      },
      () => {
        this.onErrorAnnonce();
      }
    );
  }

  loadCategorieList(): void {
    this.categorieService.getCategorieList().subscribe(
      (res: HttpResponse<ICategorie[]>) => {
        this.categories = res.body ?? [];
        this.onSuccess();
      },
      () => {
        this.onError();
      }
    );
  }

  public loadImages(): void {
    this.annonces?.forEach(annonce => {
      if (annonce?.imageVideo && annonce?.imageVideoContentType) {
        this.photoAnnonce = `data:${annonce.imageVideoContentType};base64,${annonce.imageVideo}`;
      }
    });
  }

  protected onSuccess(): void {
    if (this.annonces) {
      console.log(this.annonces);
      /*this.temp = this.annonces.map((prop: any, key: any) => {
        return {
          ...prop,
          id: key,
        };
      });*/
    }

    if (this.categories) {
      console.log(this.categories);
    }
  }

  protected onErrorAnnonce(): void {
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: toast => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });

    Toast.fire({
      icon: 'warning',
      title: 'Aucune annonce trouvée',
    });
  }

  protected onError(): void {
    console.log('Erreur find user all informations');
  }

  protected onSucessUser(data: IMandataireDelegateur | null): void {
    if (data?.id) {
      this.mandataireDelegateur = data;
      console.log('DATA USER MANDATAIRE DELEGATEUR');
    } else {
      this.compteEvaSwal();
    }
  }
}
