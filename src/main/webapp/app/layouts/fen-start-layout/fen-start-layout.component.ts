import { Component, OnInit, Renderer2, RendererFactory2 } from '@angular/core';
import { AccountService } from '../../core/auth/account.service';
import { Title } from '@angular/platform-browser';
import { FindLanguageFromKeyPipe } from '../../shared/language/find-language-from-key.pipe';
import { ActivatedRouteSnapshot, NavigationEnd, Router } from '@angular/router';
import { LangChangeEvent, TranslateService } from '@ngx-translate/core';
import dayjs from 'dayjs/esm';

@Component({
  selector: 'jhi-fen-start-layout',
  templateUrl: './fen-start-layout.component.html',
  styleUrls: ['./fen-start-layout.component.scss'],
})
export class FenStartLayoutComponent implements OnInit {
  private renderer: Renderer2;

  constructor(
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private findLanguageFromKeyPipe: FindLanguageFromKeyPipe,
    private translateService: TranslateService,
    rootRenderer: RendererFactory2
  ) {
    this.renderer = rootRenderer.createRenderer(document.querySelector('html'), null);
  }

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
      }
    });

    this.translateService.onLangChange.subscribe((langChangeEvent: LangChangeEvent) => {
      this.updateTitle();
      dayjs.locale(langChangeEvent.lang);
      this.renderer.setAttribute(document.querySelector('html'), 'lang', langChangeEvent.lang);

      this.updatePageDirection();
    });
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    const title: string = routeSnapshot.data['pageTitle'] ?? '';
    if (routeSnapshot.firstChild) {
      return this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => this.titleService.setTitle(title));
  }

  private updatePageDirection(): void {
    this.renderer.setAttribute(
      document.querySelector('html'),
      'dir',
      this.findLanguageFromKeyPipe.isRTL(this.translateService.currentLang) ? 'rtl' : 'ltr'
    );
  }
}
