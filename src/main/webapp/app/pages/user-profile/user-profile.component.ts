import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../core/auth/account.service';
import { MandataireDelegateurService } from '../../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { takeUntil } from 'rxjs/operators';
import { Account } from '../../core/auth/account.model';
import { IMandataireDelegateur } from '../../entities/mandataire-delegateur/mandataire-delegateur.model';
import { Subject } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent implements OnInit {
  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;

  photoProfile = '';

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private mandataireDelegateurService: MandataireDelegateurService) {}

  ngOnInit() {
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

  doReportExcel(id: any): void {
    this.mandataireDelegateurService.genarateExcel(id);
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
}
