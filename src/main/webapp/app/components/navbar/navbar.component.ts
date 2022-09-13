import { Component, OnInit, ElementRef } from '@angular/core';
import { ROUTES } from '../sidebar/sidebar.component';
import { Location, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { Router } from '@angular/router';
import { LoginService } from '../../login/login.service';
import { takeUntil } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { IMandataireDelegateur } from '../../entities/mandataire-delegateur/mandataire-delegateur.model';
import { AccountService } from '../../core/auth/account.service';
import { MandataireDelegateurService } from '../../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { Account } from '../../core/auth/account.model';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  public focus: any;
  public listTitles: any[] | undefined;
  public location: Location;
  isNavbarCollapsed = true;

  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;

  photoProfile = '';

  private readonly destroy$ = new Subject<void>();

  constructor(
    location: Location,
    private element: ElementRef,
    private router: Router,
    private loginService: LoginService,
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService
  ) {
    this.location = location;
  }

  ngOnInit() {
    this.listTitles = ROUTES.filter(listTitle => listTitle);

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

  getTitle() {
    let titlee = this.location.prepareExternalUrl(this.location.path());
    if (titlee.charAt(0) === '#') {
      titlee = titlee.slice(1);
    }

    if (this.listTitles) {
      for (let item = 0; item < this.listTitles.length; item++) {
        if (this.listTitles[item].path === titlee) {
          return this.listTitles[item].title;
        }
      }
    }
    return 'Dashboard';
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
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
