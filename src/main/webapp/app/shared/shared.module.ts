import { NgModule } from '@angular/core';

import { SharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { TranslateDirective } from './language/translate.directive';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { DurationPipe } from './date/duration.pipe';
import { FormatMediumDatetimePipe } from './date/format-medium-datetime.pipe';
import { FormatMediumDatePipe } from './date/format-medium-date.pipe';
import { SortByDirective } from './sort/sort-by.directive';
import { SortDirective } from './sort/sort.directive';
import { ItemCountComponent } from './pagination/item-count.component';
import { FilterComponent } from './filter/filter.component';
import { TimeAgoPipe } from './pipes/time-ago.pipe';
import { UsernamePipe } from './pipes/username.pipe';
import { ShortenPipe } from './pipes/shorten.pipe';
import { MaterialModule } from './material.module';
import { CommentairesComponent } from './commentaires/commentaires.component';
import { NgxDropzoneModule } from 'ngx-dropzone';
import { MultipleSlideModule } from './multiple-slide/multiple-slide.module';

@NgModule({
  imports: [SharedLibsModule, MaterialModule, NgxDropzoneModule, MultipleSlideModule],
  declarations: [
    FindLanguageFromKeyPipe,
    TranslateDirective,
    AlertComponent,
    AlertErrorComponent,
    HasAnyAuthorityDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    SortByDirective,
    SortDirective,
    ItemCountComponent,
    FilterComponent,
    TimeAgoPipe,
    ShortenPipe,
    UsernamePipe,
    CommentairesComponent,
  ],
  exports: [
    SharedLibsModule,
    FindLanguageFromKeyPipe,
    TranslateDirective,
    AlertComponent,
    AlertErrorComponent,
    HasAnyAuthorityDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    SortByDirective,
    SortDirective,
    ItemCountComponent,
    FilterComponent,
    TimeAgoPipe,
    ShortenPipe,
    UsernamePipe,
    MaterialModule,
    CommentairesComponent,
    NgxDropzoneModule,
    MultipleSlideModule,
  ],
})
export class SharedModule {}
