import { Component, OnInit } from '@angular/core';
import { IAnnonce } from '../entities/annonce/annonce.model';
import { IPostulant } from '../entities/postulant/postulant.model';
import { ICategorie } from '../entities/categorie/categorie.model';
import { Account } from '../core/auth/account.model';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';
import { PostulantFormGroup, PostulantFormService } from '../entities/postulant/update/postulant-form.service';
import { Observable, Subject } from 'rxjs';
import { AnnonceService } from '../entities/annonce/service/annonce.service';
import { CategorieService } from '../entities/categorie/service/categorie.service';
import { DataUtils } from '../core/util/data-util.service';
import { PostulantService } from '../entities/postulant/service/postulant.service';
import { AccountService } from '../core/auth/account.service';
import { MandataireDelegateurService } from '../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { finalize, takeUntil } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'jhi-mes-echanges',
  templateUrl: './mes-echanges.component.html',
  styleUrls: ['./mes-echanges.component.scss'],
})
export class MesEchangesComponent implements OnInit {
  annonces?: IAnnonce[];
  postulants?: IPostulant[];
  categories?: ICategorie[];
  postulant?: IPostulant | null;
  verifDoublonAbonnement = false;
  isCollapsed = false;

  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;

  editForm: PostulantFormGroup = this.postulantFormService.createPostulantFormGroup();

  private readonly destroy$ = new Subject<void>();

  constructor(
    protected annonceService: AnnonceService,
    protected categorieService: CategorieService,
    protected dataUtils: DataUtils,
    protected postulantService: PostulantService,
    protected postulantFormService: PostulantFormService,
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService
  ) {}

  ngOnInit(): void {
    this.loadAnnonceList();
    this.loadCategorieList();
    this.loadProfileMandataire();
    this.loadPostulantList();
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

  loadPostulantList(): void {
    this.postulantService.getPostulantList().subscribe(
      (res: HttpResponse<IPostulant[]>) => {
        this.postulants = res.body ?? [];
        console.log('Postulants');
        console.log(this.postulants);
      },
      () => {
        this.onError();
      }
    );
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

  loadCategorieList(): void {
    this.categorieService.getCategorieList().subscribe(
      (res: HttpResponse<ICategorie[]>) => {
        this.categories = res.body ?? [];
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

  protected onSuccess(): void {
    if (this.annonces) {
      console.log(this.annonces);
    }

    if (this.categories) {
      console.log(this.categories);
    }
  }

  protected onError(): void {
    this.notification('Aucune annonce trouvée', 'warning');
  }

  protected afterClickAbonner() {
    this.notification("Vous etes abonné à l'annonce", 'success');
  }

  protected onSaveFinalize(): void {
    console.log('success abonnement');
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostulant>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.loadPostulantList();
    this.afterClickAbonner();
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
