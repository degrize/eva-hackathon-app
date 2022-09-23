import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ICategorie } from '../../entities/categorie/categorie.model';
import { Subject } from 'rxjs';
import { AccountService } from '../../core/auth/account.service';
import { MandataireDelegateurService } from '../../entities/mandataire-delegateur/service/mandataire-delegateur.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnnonceService } from '../../entities/annonce/service/annonce.service';
import { CategorieService } from '../../entities/categorie/service/categorie.service';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'],
})
export class FooterComponent implements OnInit {
  categories?: ICategorie[];

  private readonly destroy$ = new Subject<void>();
  constructor(protected annonceService: AnnonceService, protected categorieService: CategorieService) {}

  ngOnInit(): void {
    this.loadCategorieList();
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

  protected onSuccess(): void {
    //
  }

  protected onError(): void {
    console.log('Erreur all catgorie du footer');
  }
}
