import { Component, Input, OnInit } from '@angular/core';
import { Account } from '../../core/auth/account.model';

@Component({
  selector: 'jhi-hero-page',
  templateUrl: './hero-page.component.html',
  styleUrls: ['./hero-page.component.scss'],
})
export class HeroPageComponent implements OnInit {
  @Input() account: Account | null = null;

  imagesList = ['tr.png', 'md.png', 'av.png', 'pr.png'];
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

  ngOnInit(): void {}
}
