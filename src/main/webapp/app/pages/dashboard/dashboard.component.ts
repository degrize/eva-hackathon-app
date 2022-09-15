import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js';
import { Subject } from 'rxjs';
import { IAdminStatistics } from '../../entities/enumerations/admin-statistics';
import { IAnnonce } from '../../entities/annonce/annonce.model';
import { ICategorie } from '../../entities/categorie/categorie.model';
import { AnnonceService } from '../../entities/annonce/service/annonce.service';
import { CategorieService } from '../../entities/categorie/service/categorie.service';
import { HttpResponse } from '@angular/common/http';
import List from 'list.js';
import { IMandataireDelegateur } from '../../entities/mandataire-delegateur/mandataire-delegateur.model';
import Swal from 'sweetalert2';
import { DashboardService } from './dashboard.service';

// core components

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  public datasets: any;
  public data: any;
  public salesChart?: any;
  public clicked: boolean = true;
  public clicked1: boolean = false;

  annonces?: IAnnonce[];
  categories?: ICategorie[];
  adminStatistique?: IAdminStatistics;

  dataCategorie = {
    nom: [''],
    data: [0],
  };

  adminStatistics?: IAdminStatistics | null;

  private readonly destroy$ = new Subject<void>();

  constructor(
    protected annonceService: AnnonceService,
    protected categorieService: CategorieService,
    protected dashboardService: DashboardService
  ) {}

  ngOnInit() {
    this.loadAnnonceList();
    this.loadAdminStatistique();
    this.datasets = [
      [0, 20, 10, 30, 15, 40, 20, 60, 60],
      [0, 20, 5, 25, 10, 30, 15, 40, 40],
    ];
    this.data = this.datasets[0];

    this.chartJsFunction();
  }

  chartJsFunction(): void {
    var chartSales = <HTMLCanvasElement>document.getElementById('chart-sales-dark');

    // Init chart
    var salesChart = new Chart(chartSales, {
      type: 'bar',
      data: {
        labels: this.dataCategorie.nom,
        datasets: [
          {
            label: 'Categorie des annonces',
            data: this.dataCategorie.data,
            backgroundColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)'],
          },
        ],
      },
      options: {
        responsive: true,
      },
    });

    var chartOrders = <HTMLCanvasElement>document.getElementById('chart-orders');
    var salesChart = new Chart(chartOrders, {
      type: 'line',
      data: {
        labels: this.dataCategorie.nom,
        datasets: [
          {
            label: 'Categorie des annonces',
            data: this.dataCategorie.data,
            backgroundColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)'],
          },
        ],
      },
      options: {
        responsive: true,
      },
    });
  }

  public updateOptions() {
    if (this.salesChart?.data.datasets) {
      this.salesChart.data.datasets[0].data = this.data;
      this.salesChart?.update();
    }
  }

  loadAnnonceList(): void {
    this.annonceService.getAnnonceList().subscribe(
      (res: HttpResponse<IAnnonce[]>) => {
        this.annonces = res.body ?? [];
        new List('users', {
          valueNames: ['name', 'budget', 'status', 'completion'],
          listClass: 'list',
        });

        this.onSuccess();
      },
      () => {
        this.onErrorAnnonce();
      }
    );
  }

  loadCategorieList(): void {
    this.categorieService.getCategorieList().subscribe(
      (res: HttpResponse<ICategorie[]>) => {
        this.categories = res.body ?? [];
        this.onSuccessCategorie();
      },
      () => {
        this.onError();
      }
    );
  }

  loadAdminStatistique(): void {
    this.dashboardService.getAdminStatistique().subscribe(
      (res: HttpResponse<IAdminStatistics>) => {
        this.adminStatistique = res.body ?? undefined;
        this.onSuccess();
      },
      () => {
        this.onError();
      }
    );
  }

  onSuccessCategorie(): void {
    this.dataCategorie.nom = [];
    this.dataCategorie.data = [];
    this.categories?.forEach(categorie => {
      let count = 0;
      if (categorie?.nom != null) {
        this.dataCategorie.nom.push(categorie?.nom);
      } else {
        this.dataCategorie.nom.push('Cateegorie 1');
      }

      this.annonces?.forEach(annonce => {
        if (annonce?.categories) {
          annonce.categories.forEach(categorieAnnonce => {
            if (categorieAnnonce?.id === categorie?.id) {
              count++;
            }
          });
        }
      });
      this.dataCategorie.data.push(count);
    });
  }

  protected onError(): void {
    console.log('Erreur find user all informations');
  }

  protected onSuccess(): void {
    this.loadCategorieList();
  }

  protected onErrorAnnonce(): void {
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
      icon: 'warning',
      title: 'Aucune annonce trouvée',
    });
  }
}
