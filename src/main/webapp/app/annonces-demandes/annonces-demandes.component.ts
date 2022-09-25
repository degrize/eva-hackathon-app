import { Component, OnInit } from '@angular/core';
import { IAnnonce } from '../entities/annonce/annonce.model';
import { Account } from '../core/auth/account.model';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';
import { Observable, Subject } from 'rxjs';
import { AnnonceService } from '../entities/annonce/service/annonce.service';
import { DataUtils } from '../core/util/data-util.service';
import { AccountService } from '../core/auth/account.service';
import { MandataireDelegateurService } from '../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { finalize, takeUntil } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { AnnonceFormGroup, AnnonceFormService } from '../entities/annonce/update/annonce-form.service';
import dayjs, { Dayjs } from 'dayjs';

@Component({
  selector: 'jhi-annonces-demandes',
  templateUrl: './annonces-demandes.component.html',
  styleUrls: ['./annonces-demandes.component.scss'],
})
export class AnnoncesDemandesComponent implements OnInit {
  annonces?: IAnnonce[];
  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;

  editForm: AnnonceFormGroup = this.annonceFormService.createAnnonceFormGroup();

  private readonly destroy$ = new Subject<void>();

  constructor(
    protected annonceService: AnnonceService,
    protected annonceFormService: AnnonceFormService,
    protected dataUtils: DataUtils,
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService
  ) {}

  ngOnInit(): void {
    this.loadAnnonceList();
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

  loadAnnonceList(): void {
    this.annonceService.getAnnonceList().subscribe(
      (res: HttpResponse<IAnnonce[]>) => {
        this.annonces = res.body ?? [];
        /* new List('users', {
          valueNames: ['name', 'budget', 'status', 'completion'],
          listClass: 'list',
        }); */

        this.onSuccess();
      },
      () => {
        this.onError();
      }
    );
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }

  save(varAnnonce: IAnnonce): void {
    let annonce = this.annonceFormService.getAnnonce(this.editForm);
    annonce = varAnnonce;
    annonce.dateDeDelais = dayjs().format('YYYY-MM-DD');
    annonce.dateDeDelegation = null;
    annonce.estTerminee = true;
    annonce.id = varAnnonce.id;

    console.log(annonce);
    if (annonce.id !== null) {
      this.subscribeToSaveResponse(this.annonceService.update(annonce));
    } else {
      this.subscribeToSaveResponse(this.annonceService.create(annonce));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnnonce>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.TachefiniFirstNotification();

    setTimeout(() => {
      this.transfertEffecue();
    }, 2000);
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    //
  }

  protected onSuccess(): void {
    if (this.annonces) {
      console.log(this.annonces);
    }
  }

  protected onError(): void {
    this.notification('Aucune annonce trouvée', 'warning');
  }

  protected afterClickAbonner() {
    this.notification("Vous etes abonné à l'annonce", 'success');
  }

  protected onSucessUser(data: IMandataireDelegateur | null): void {
    if (data) {
      this.mandataireDelegateur = data;
      console.log('DATA USERIO MANDATAIRE DELEGATEUR');
      console.log(this.mandataireDelegateur);
    }
  }

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

  private TachefiniFirstNotification(): void {
    let timerInterval: any;
    Swal.fire({
      title: 'Nous sommes entraint de preparer le paiement la transaction...',
      html: "Patientez jusqu'a <b></b> millisecondes.",
      timer: 2000,
      timerProgressBar: true,
      didOpen: () => {
        Swal.showLoading();
        const b = Swal.getHtmlContainer()?.querySelector('b');
        timerInterval = setInterval(() => {
          if (b?.textContent != undefined) {
            b.textContent = Swal.getTimerLeft() + '';
          }
        }, 100);
      },
      willClose: () => {
        clearInterval(timerInterval);
      },
    }).then(result => {
      /* Read more about handling dismissals below */
      if (result.dismiss === Swal.DismissReason.timer) {
        console.log('I was closed by the timer');
      }
    });
  }

  private transfertEffecue(): void {
    Swal.fire({
      title: 'Transaction MoMo!',
      text: "l'argent a bien été transmit sur le de l'annonceur.",
      imageUrl: '../../../content/images/sendMoneyToAnnonceur.jpg',
      imageWidth: 400,
      imageHeight: 200,
      imageAlt: 'Eva App',
    });
  }
}
