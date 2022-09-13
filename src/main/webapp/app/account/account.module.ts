import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { PasswordStrengthBarComponent } from './password/password-strength-bar/password-strength-bar.component';
import { RegisterComponent } from './register/register.component';
import { ActivateComponent } from './activate/activate.component';
import { PasswordResetInitComponent } from './password-reset/init/password-reset-init.component';
import { PasswordResetFinishComponent } from './password-reset/finish/password-reset-finish.component';
import { accountState } from './account.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(accountState)],
  declarations: [
    ActivateComponent,
    RegisterComponent,
    PasswordStrengthBarComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
  ],
  exports: [PasswordStrengthBarComponent],
})
export class AccountModule {}
