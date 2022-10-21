import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { IvyCarouselModule } from 'angular-responsive-carousel';

import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';
import { RippleModule } from 'primeng/ripple';
import { HeroPageComponent } from './hero-page/hero-page.component';
import { StyleClassModule } from 'primeng/styleclass';
import { HomeAnnoncesComponent } from './home-annonces/home-annonces.component';
import { HomeSolutionsComponent } from './home-solutions/home-solutions.component';
import { HomePartenairesComponent } from './home-partenaires/home-partenaires.component';

@NgModule({
  imports: [
    SharedModule,
    StyleClassModule,
    RouterModule.forChild([HOME_ROUTE]),
    IvyCarouselModule,
    CarouselModule,
    ButtonModule,
    ToastModule,
    RippleModule,
  ],
  declarations: [HomeComponent, HeroPageComponent, HomeAnnoncesComponent, HomeSolutionsComponent, HomePartenairesComponent],
})
export class HomeModule {}
