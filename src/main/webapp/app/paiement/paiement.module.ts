import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PAIEMENT_ROUTE } from './paiement.route';
import { PaiementComponent } from './paiement.component';
import { SharedModule } from '../shared/shared.module';
import { RouterModule } from '@angular/router';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputTextModule } from 'primeng/inputtext';
import { FileUploadModule } from 'primeng/fileupload';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@NgModule({
  declarations: [PaiementComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([PAIEMENT_ROUTE]),
    SharedModule,
    InputTextareaModule,
    InputTextModule,
    FileUploadModule,
    ToastModule,
  ],
  providers: [MessageService],
})
export class PaiementModule {}
