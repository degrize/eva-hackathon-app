import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FenStartLayoutComponent } from './fen-start-layout.component';
import { AppModule } from '../../app.module';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ClipboardModule } from 'ngx-clipboard';
import { FenStartLayoutRoutes } from './fen-start-layout.routing';
import { FooterComponent } from '../footer/footer.component';
import { HomeModule } from '../../home/home.module';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [FenStartLayoutComponent, FooterComponent],
  imports: [
    SharedModule,
    CommonModule,
    RouterModule.forChild(FenStartLayoutRoutes),
    FormsModule,
    HttpClientModule,
    NgbModule,
    ClipboardModule,
    HomeModule,
  ],
})
export class FenStartLayoutModule {}
