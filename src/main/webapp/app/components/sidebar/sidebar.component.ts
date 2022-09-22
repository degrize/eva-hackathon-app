import { Component, ElementRef, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EntityNavbarItems } from '../../entities/entity-navbar-items';
import { LANGUAGES } from '../../config/language.constants';
import { Account } from '../../core/auth/account.model';
import { Location } from '@angular/common';
import { LoginService } from '../../login/login.service';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';
import { AccountService } from '../../core/auth/account.service';
import { ProfileService } from '../../layouts/profiles/profile.service';
import { VERSION } from '../../app.constants';
import { IMandataireDelegateur } from '../../entities/mandataire-delegateur/mandataire-delegateur.model';
import { Subject } from 'rxjs';
import { MandataireDelegateurService } from '../../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { HttpResponse } from '@angular/common/http';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { path: '/mes-annonces', title: 'Annonces', icon: 'ni-planet text-blue', class: '' },
  { path: '/mes-echanges', title: 'Messages', icon: 'ni-planet text-orange', class: '' },
  { path: '/user-profile', title: 'profile', icon: 'ni-single-02 text-yellow', class: '' },
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  public menuItems: any[] | undefined;
  public isCollapsed = true;

  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  lang = 'fr';

  mandataireDelegateur?: IMandataireDelegateur | null;

  photoProfile = '';
  evaLogo = '../../../content/images/eva-logo.png';
  component_dash_id = '';

  private readonly destroy$ = new Subject<void>();

  public focus: any;
  public location: Location;
  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router,
    location: Location,
    private element: ElementRef,
    private mandataireDelegateurService: MandataireDelegateurService
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
    this.location = location;
  }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
    this.router.events.subscribe(event => {
      this.isCollapsed = true;
    });

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
      if (account !== null) {
        this.mandataireDelegateurService.findByJhiUserId({ login: this.account?.login }).subscribe(
          (res: HttpResponse<IMandataireDelegateur>) => this.onSucessUser(res.body),
          (res: HttpResponse<any>) => this.onError()
        );
      }
    });
  }

  public loadImages(): void {
    if (this.mandataireDelegateur?.photo && this.mandataireDelegateur?.photoContentType) {
      this.photoProfile = `data:${this.mandataireDelegateur.photoContentType};base64,${this.mandataireDelegateur.photo}`;
    }
  }

  protected onError(): void {
    console.log('Erreur: Find user informations');
  }

  protected onSucessUser(data: IMandataireDelegateur | null): void {
    this.mandataireDelegateur = data;
    this.loadImages();
    console.log('DATA USER MANDATAIRE DELEGATEUR');
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

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }
}
