import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPostulant } from '../postulant.model';

@Component({
  selector: 'jhi-postulant-detail',
  templateUrl: './postulant-detail.component.html',
})
export class PostulantDetailComponent implements OnInit {
  postulant: IPostulant | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postulant }) => {
      this.postulant = postulant;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
