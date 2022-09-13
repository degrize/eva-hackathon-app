import { Component, OnInit } from '@angular/core';
import { Account } from '../core/auth/account.model';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';
import { Observable, Subject } from 'rxjs';
import { AccountService } from '../core/auth/account.service';
import { MandataireDelegateurService } from '../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { Router } from '@angular/router';
import { finalize, takeUntil } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { EtatCompte } from '../entities/enumerations/etat-compte.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'jhi-etat-compte',
  templateUrl: './etat-compte.component.html',
  styleUrls: ['./etat-compte.component.scss'],
})
export class EtatCompteComponent implements OnInit {
  isSaving = false;

  CompteNormal = {
    text1: "Ultimate accès à l'application EVA",
    text2: '-',
    text3: '-',
    text4: '-',
    title: 'For beginner use',
    price: '0',
  };
  ComptePremium = {
    text1: "Ultimate accès à l'application EVA",
    text2: 'vos annnonces se trouvent en haut de la liste des annonces',
    text3: "Vous pouvez communiquer en permanence avec l'annonceur et le delegateur",
    text4: 'Vous recevrez un mail pour les annonces de grand prix',
    title: 'For professional use',
    price: '32',
  };

  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;
  premium = false;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService,
    private router: Router
  ) {}

  ngOnInit(): void {
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

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  previousState(): void {
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

    Toast.fire({
      icon: 'success',
      title: 'Ta soucription a été mise à jour',
    });
  }

  changeEtatCompte(): void {
    if (this.mandataireDelegateur) {
      this.mandataireDelegateur.etatCompte = this.premium ? EtatCompte.PREMIUM : EtatCompte.NORMAL;
      this.mandataireDelegateur.etatCompte = this.premium ? EtatCompte.PREMIUM : EtatCompte.NORMAL;
      this.subscribeToSaveResponse(this.mandataireDelegateurService.update(this.mandataireDelegateur));
    }
  }

  startPayementForm(): void {
    Swal.fire({
      title: 'Renseignez votre password',
      input: 'password',
      inputAttributes: {
        autocapitalize: 'off',
      },
      showCancelButton: true,
      confirmButtonText: 'Souscrire',
      showLoaderOnConfirm: true,
      preConfirm: login => {
        return fetch(`//api.github.com/users/${login}`)
          .then(response => {
            if (!response.ok) {
              throw new Error(response.statusText);
            }
            return response.json();
          })
          .catch(error => {
            Swal.showValidationMessage(`Request failed: ${error}`);
          });
      },
      allowOutsideClick: () => !Swal.isLoading(),
    }).then(result => {
      if (result.isConfirmed) {
        Swal.fire({
          title: `${result.value.login}'s avatar`,
          imageUrl: result.value.avatar_url,
        });
        this.changeEtatCompte();
      }
    });
  }

  switch(value: boolean): void {
    this.premium = value;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMandataireDelegateur>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onError(): void {
    console.log('Erreur find user all informations');
  }

  protected onSucessUser(data: IMandataireDelegateur | null): void {
    if (data) {
      if (data.etatCompte === EtatCompte.NORMAL) {
        this.premium = false;
      } else {
        this.premium = true;
      }

      this.mandataireDelegateur = data;
      console.log('DATA USER MANDATAIRE DELEGATEUR');
    } else {
      this.premium = false;
    }
  }
}
