import { Component, Input, OnInit } from '@angular/core';
import { Account } from '../../core/auth/account.model';
import { IAnnonce } from '../../entities/annonce/annonce.model';
import { ICategorie } from '../../entities/categorie/categorie.model';
import { EtatCompte } from '../../entities/enumerations/etat-compte.model';

@Component({
  selector: 'jhi-home-annonces',
  templateUrl: './home-annonces.component.html',
  styleUrls: ['./home-annonces.component.scss'],
})
export class HomeAnnoncesComponent implements OnInit {
  @Input() account: Account | null = null;
  @Input() annonce: IAnnonce[] | null | any = null;
  @Input() categorie: ICategorie | null | any = null;

  annoncesList: IAnnonce[] = [];

  responsiveOptions;

  constructor() {
    this.responsiveOptions = [
      {
        breakpoint: '1024px',
        numVisible: 3,
        numScroll: 3,
      },
      {
        breakpoint: '768px',
        numVisible: 2,
        numScroll: 2,
      },
      {
        breakpoint: '560px',
        numVisible: 1,
        numScroll: 1,
      },
    ];
  }

  ngOnInit(): void {
    for (let i = 0; i < this.annonce.length; i++) {
      if (this.annonce[i].estTerminee === false) {
        this.annoncesList?.push(this.annonce[i]);
      }
    }

    console.log(this.annoncesList);
  }
}
