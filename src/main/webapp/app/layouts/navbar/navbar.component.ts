import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { HttpResponse } from '@angular/common/http';
import { IMandataireDelegateur } from '../../entities/mandataire-delegateur/mandataire-delegateur.model';
import { MandataireDelegateurService } from '../../entities/mandataire-delegateur/service/mandataire-delegateur.service';

import Selectr from 'mobius1-selectr';
import { AnnonceFormGroup } from '../../entities/annonce/update/annonce-form.service';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  searchInput = '';
  public isCollapsed = true;

  public focus: any;
  lang = 'fr';
  mandataireDelegateur?: IMandataireDelegateur | null;

  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router,
    private mandataireDelegateurService: MandataireDelegateurService
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    if (this.sessionStorageService.retrieve('locale')) {
      this.lang = this.sessionStorageService.retrieve('locale');
    }

    this.entitiesNavbarItems = EntityNavbarItems;
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      // On recupère les infos sur le compte
      this.mandataireDelegateurService.findByJhiUserId({ login: this.account?.login }).subscribe(
        (res: HttpResponse<IMandataireDelegateur>) => this.onSucessUser(res.body),
        (res: HttpResponse<any>) => this.onError()
      );
    });

    var selectr: any = document.getElementById('selectr1');
    var options = {};
    // var optionsMultiple = {multiple: true};
    var selectorDefault = new Selectr(selectr, options);
  }

  changeLanguage(languageKey: string): void {
    this.lang = languageKey;
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  verifCompteEva(): void {
    if (this.mandataireDelegateur) {
      this.router.navigate(['/user-profile']);
    } else {
      this.router.navigate(['/create-eva-profile']);
    }
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  search(): void {
    let inputSearch = document.getElementsByTagName('input');
    this.searchInput = inputSearch[0].value;
    if (this.searchInput !== '') {
      this.router.navigateByUrl('/search?nomprenom=' + this.searchInput);
    }
  }

  protected onError(): void {
    console.log('erreur de recuperation des informations sur le compte');
  }

  protected onSucessUser(data: IMandataireDelegateur | null): void {
    this.mandataireDelegateur = data;
    console.log('DATA USER MANDATAIRE DELEGATEUR');
    console.log(data?.email);
  }
}
