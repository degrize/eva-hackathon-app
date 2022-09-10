import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMandataireDelegateur } from '../mandataire-delegateur.model';
import { MandataireDelegateurService } from '../service/mandataire-delegateur.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './mandataire-delegateur-delete-dialog.component.html',
})
export class MandataireDelegateurDeleteDialogComponent {
  mandataireDelegateur?: IMandataireDelegateur;

  constructor(protected mandataireDelegateurService: MandataireDelegateurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mandataireDelegateurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
