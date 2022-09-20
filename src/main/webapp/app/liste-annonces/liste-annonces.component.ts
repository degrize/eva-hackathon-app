import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { AnnonceService } from '../entities/annonce/service/annonce.service';
import { IAnnonce } from '../entities/annonce/annonce.model';
import { HttpResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { CategorieService } from '../entities/categorie/service/categorie.service';
import { ICategorie } from '../entities/categorie/categorie.model';
import { DataUtils } from '../core/util/data-util.service';
import { combineLatest, Observable, map, startWith, Subject } from 'rxjs';
import { IPostulant } from '../entities/postulant/postulant.model';
import { finalize, takeUntil } from 'rxjs/operators';
import { PostulantService } from '../entities/postulant/service/postulant.service';
import { PostulantFormGroup, PostulantFormService } from '../entities/postulant/update/postulant-form.service';
import { IMandataireDelegateur } from '../entities/mandataire-delegateur/mandataire-delegateur.model';
import { AccountService } from '../core/auth/account.service';
import { MandataireDelegateurService } from '../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { Account } from '../core/auth/account.model';
import { EtatCompte } from '../entities/enumerations/etat-compte.model';
import { ITEM_DELETED_EVENT } from '../config/navigation.constants';
import { FormBuilder, FormControl } from '@angular/forms';
import { AnnonceSearchType } from './enums/annonce-search-type.enum';
import { AnnonceSearchService } from './services/annonce-search.service';
import { SAnnonce } from './models/s-annonce.model';

@Component({
  selector: 'jhi-liste-annonces',
  templateUrl: './liste-annonces.component.html',
  styleUrls: ['./liste-annonces.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListeAnnoncesComponent implements OnInit {
  entries: number = 10;
  selected: any[] = [];
  temp: any[] = [];
  activeRow: any;

  annonces?: IAnnonce[];
  postulants?: IPostulant[];
  categories?: ICategorie[];
  postulant?: IPostulant | null;
  verifDoublonAbonnement = false;
  isCollapsed = false;

  account: Account | null = null;
  mandataireDelegateur?: IMandataireDelegateur | null;

  editForm: PostulantFormGroup = this.postulantFormService.createPostulantFormGroup();

  loading$!: Observable<boolean>;
  annonces$!: Observable<SAnnonce[]>;

  searchCtrl!: FormControl;
  searchTypeCtrl!: FormControl;

  searchTypeOptions!: {
    value: AnnonceSearchType;
    label: string;
  }[];

  private readonly destroy$ = new Subject<void>();

  constructor(
    protected annonceService: AnnonceService,
    protected categorieService: CategorieService,
    protected dataUtils: DataUtils,
    protected postulantService: PostulantService,
    protected postulantFormService: PostulantFormService,
    private accountService: AccountService,
    private mandataireDelegateurService: MandataireDelegateurService,
    private formBuilder: FormBuilder,
    private annonceSearchService: AnnonceSearchService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.initObservables();
    this.annonceSearchService.getAnnoncesFromServer();

    this.loadCategorieList();
    this.loadAnnonceList();
    this.loadProfileMandataire();
    this.loadPostulantList();
  }

  private initForm() {
    this.searchCtrl = this.formBuilder.control('');
    this.searchTypeCtrl = this.formBuilder.control(AnnonceSearchType.TITRE);
    this.searchTypeOptions = [
      { value: AnnonceSearchType.TITRE, label: 'Titre' },
      { value: AnnonceSearchType.TARIF, label: 'Tarif' },
      { value: AnnonceSearchType.CATEGORIES, label: 'Categorie' },
    ];
  }

  printAlert(): void {
    this.verifDoublonAbonnement = true;
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

  startAbonnement(annonce: IAnnonce): void {
    const postulant = this.postulantFormService.getPostulant(this.editForm);
    postulant.mandataireDelegateur = this.mandataireDelegateur;
    postulant.numeroMomo = this.mandataireDelegateur?.numeroMomo;
    postulant.annonces?.push(annonce);

    if (postulant?.id !== null) {
      this.subscribeToSaveResponse(this.postulantService.update(postulant));
    } else {
      this.subscribeToSaveResponse(this.postulantService.create(postulant));
      console.log(" qui vient d'inserer");
      console.log(postulant);
    }
  }

  deleteAbonnement(id: number): void {
    this.postulantService.delete(id).subscribe(() => {
      this.verifDoublonAbonnement = true;
      this.loadPostulantList();
      this.notification('Vous etes désabonné', 'success');
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

  private initObservables() {
    this.loading$ = this.annonceSearchService.loading$;
    this.annonces$ = this.annonceSearchService.annonces$;

    const search$ = this.searchCtrl.valueChanges.pipe(
      startWith(this.searchCtrl.value),
      map(value => value.toLowerCase())
    );
    const searchType$: Observable<AnnonceSearchType> = this.searchTypeCtrl.valueChanges.pipe(startWith(this.searchTypeCtrl.value));

    this.annonces$ = combineLatest([search$, searchType$, this.annonceSearchService.annonces$]).pipe(
      map(([search, searchType, annonces]) => annonces.filter(annonce => annonce[searchType].toLowerCase().includes(search as string)))
    );
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
