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

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { path: '/annonce/new', title: 'Créer une annonce', icon: 'ni-planet text-blue', class: '' },
  { path: '/echanges', title: 'Mes echanges', icon: 'ni-pin-3 text-orange', class: '' },
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
  component_dash_id = '';

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
    private element: ElementRef
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
    });
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
