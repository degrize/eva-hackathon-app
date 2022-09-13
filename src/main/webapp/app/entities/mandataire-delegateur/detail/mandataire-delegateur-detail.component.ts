import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMandataireDelegateur } from '../mandataire-delegateur.model';

@Component({
  selector: 'jhi-mandataire-delegateur-detail',
  templateUrl: './mandataire-delegateur-detail.component.html',
})
export class MandataireDelegateurDetailComponent implements OnInit {
  mandataireDelegateur: IMandataireDelegateur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mandataireDelegateur }) => {
      this.mandataireDelegateur = mandataireDelegateur;
      console.log(mandataireDelegateur);
    });
  }

  previousState(): void {
    window.history.back();
  }
}
