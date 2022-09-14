import { Component, OnInit } from '@angular/core';
import { AnnonceService } from '../entities/annonce/service/annonce.service';
import { IAnnonce } from '../entities/annonce/annonce.model';
import { HttpResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import List from 'list.js';

@Component({
  selector: 'jhi-liste-annonces',
  templateUrl: './liste-annonces.component.html',
  styleUrls: ['./liste-annonces.component.scss'],
})
export class ListeAnnoncesComponent implements OnInit {
  entries: number = 10;
  selected: any[] = [];
  temp: any[] = [];
  activeRow: any;
  rows: any = [
    {
      name: 'Michael Bruce',
      position: 'Javascript Developer',
      office: 'Singapore',
      age: '29',
      start: '2011/06/27',
      salary: '$183,000',
    },
    {
      name: 'Donna Snider',
      position: 'Customer Support',
      office: 'New York',
      age: '27',
      start: '2011/01/25',
      salary: '$112,000',
    },
  ];
  annonces?: IAnnonce[];

  constructor(protected annonceService: AnnonceService) {}

  entriesChange($event: any) {
    this.entries = $event.target?.value;
  }

  filterTable($event: any) {
    let val = $event.target.value;
    this.temp = this.rows.filter(function (d: { [x: string]: string }) {
      for (let key in d) {
        if (d[key].toLowerCase().indexOf(val) !== -1) {
          return true;
        }
      }
      return false;
    });
  }
  onSelect({ selected }: any) {
    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
  }
  onActivate(event: { row: any }) {
    this.activeRow = event.row;
  }

  ngOnInit(): void {
    new List('users', {
      valueNames: ['name', 'budget', 'status', 'completion'],
      listClass: 'list',
    });

    this.startJsFile();
    this.loadAnnonceList();
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

  startJsFile(): void {
    const day = document.querySelector('.day .numb');
    const hour = document.querySelector('.hour .numb');
    const min = document.querySelector('.min .numb');
    const sec = document.querySelector('.sec .numb');
    let timer = setInterval(() => {
      let currentDate = new Date().getTime();
      let launchDate = new Date('Sep 18, 2020 13:00:00').getTime();
      let duration = launchDate - currentDate;
      let days = Math.floor(duration / (1000 * 60 * 60 * 24));
      let hours = Math.floor((duration % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      let minutes = Math.floor((duration % (1000 * 60 * 60)) / (1000 * 60));
      let seconds = Math.floor((duration % (1000 * 60)) / 1000);

      if (day) {
        day.innerHTML = String(days);
        if (days < 10) {
          day.innerHTML = '0' + days;
        }
      }

      if (hour && min && sec) {
        hour.innerHTML = String(hours);
        min.innerHTML = String(minutes);
        sec.innerHTML = String(seconds);

        if (hours < 10) {
          hour.innerHTML = '0' + hours;
        }
        if (minutes < 10) {
          min.innerHTML = '0' + minutes;
        }
        if (seconds < 10) {
          sec.innerHTML = '0' + seconds;
        }
      }

      if (duration < 0) {
        clearInterval(timer);
      }
    }, 1000);
  }

  protected onSuccess(): void {
    if (this.annonces) {
      console.log(this.annonces);
      this.temp = this.annonces.map((prop: any, key: any) => {
        return {
          ...prop,
          id: key,
        };
      });
    }
  }

  protected onError(): void {
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
