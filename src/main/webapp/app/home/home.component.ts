import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

import Swal from 'sweetalert2';
import { MandataireDelegateurService } from '../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { HttpResponse } from '@angular/common/http';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService,
    private router: Router
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
        ', finalisez votre profile EVA </h4>\n' +
        '      </div>\n' +
        '      <div class="card-body px-lg-7">\n' +
        '        <div class="display-4 text-white">PROFILE EVA</div>\n' +
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
        '                <span class="pl-2 text-sm text-white">(Envoyer - Recevvoir) de l\'argent </span>\n' +
        '              </div>\n' +
        '            </div>\n' +
        '          </li>\n' +
        '        </ul>\n' +
        '        <a href="http://localhost:9000/mandataire-delegateur/new" class="btn btn-primary mb-3">Demarrer maintenant</a>\n' +
        '      </div>\n' +
        '    </div>\n' +
        '  </div>',
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
