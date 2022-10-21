import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AnnonceSearchService } from '../services/annonce-search.service';
import { SAnnonce } from '../models/s-annonce.model';
import { Observable, Subject, switchMap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { IMandataireDelegateur } from '../../entities/mandataire-delegateur/mandataire-delegateur.model';
import { AccountService } from '../../core/auth/account.service';
import { MandataireDelegateurService } from '../../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { Account } from '../../core/auth/account.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-single-annonce',
  templateUrl: './single-annonce.component.html',
  styleUrls: ['./single-annonce.component.scss'],
})
export class SingleAnnonceComponent implements OnInit {
  loading$!: Observable<boolean>;
  annonce$!: Observable<SAnnonce>;

  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;
  private readonly destroy$ = new Subject<void>();

  constructor(
    private annoncesService: AnnonceSearchService,
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService
  ) {}

  ngOnInit(): void {
    this.initObservables();
    this.loadProfileMandataire();
  }

  loadProfileMandataire(): void {
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

  previousState(): void {
    window.history.back();
  }

  protected onError(): void {
    this.notification('Aucune annonce trouvée', 'warning');
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSucessUser(data: IMandataireDelegateur | null): void {
    if (data) {
      this.mandataireDelegateur = data;
      console.log('DATA USERIO MANDATAIRE DELEGATEUR');
      console.log(this.mandataireDelegateur);
    }
  }

  private initObservables() {
    this.loading$ = this.annoncesService.loading$;

    this.annonce$ = this.route.params.pipe(switchMap(params => this.annoncesService.getAnnonceById(+params['id'])));
  }

  onHire() {}

  onRefuse() {}

  onGoBack() {
    this.router.navigateByUrl('/liste-annonces');
  }

  private start_javaScript(): void {}

  protected notification(message: string, type: string): void {
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: toast => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });

    if (type === 'success') {
      Toast.fire({
        icon: 'success',
        title: message,
      });
    }
    if (type === 'warning') {
      Toast.fire({
        icon: 'warning',
        title: message,
      });
    }
  }
}
