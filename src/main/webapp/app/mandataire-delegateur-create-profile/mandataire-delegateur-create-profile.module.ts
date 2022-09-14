import { NgModule } from '@angular/core';
import { MandataireDelegateurCreateProfileComponent } from './mandataire-delegateur-create-profile.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';

import { MANDATAIRE_DELEGATAIRE_CREATE_PROFILE_ROUTE } from './mandataire-delegateur-create-profile.route';
import { TranslateModule } from '@ngx-translate/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [MandataireDelegateurCreateProfileComponent],
  imports: [RouterModule.forChild([MANDATAIRE_DELEGATAIRE_CREATE_PROFILE_ROUTE]), TranslateModule, SharedModule, NgSelectModule],
  providers: [NgbActiveModal],
})
export class MandataireDelegateurCreateProfileModule {}
