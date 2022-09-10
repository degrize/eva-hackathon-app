import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISouscription } from '../souscription.model';
import { SouscriptionService } from '../service/souscription.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './souscription-delete-dialog.component.html',
})
export class SouscriptionDeleteDialogComponent {
  souscription?: ISouscription;

  constructor(protected souscriptionService: SouscriptionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.souscriptionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
