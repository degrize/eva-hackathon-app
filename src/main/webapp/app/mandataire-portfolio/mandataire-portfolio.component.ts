import { Component, OnInit } from '@angular/core';

declare var jquery: any;
declare var $: any;

@Component({
  selector: 'jhi-mandataire-portfolio',
  templateUrl: './mandataire-portfolio.component.html',
  styleUrls: ['./mandataire-portfolio.component.scss'],
})
export class MandatairePortfolioComponent implements OnInit {
  constructor() {
    this.start_javascript();
  }

  ngOnInit(): void {}
  start_javascript(): void {
    $(document).ready(function () {
      // slide-up script
      $('.scroll-up-btn').click(function () {
        $('html').animate({ scrollTop: 0 });
        // removing smooth scroll on slide-up button click
        $('html').css('scrollBehavior', 'auto');
      });

      $('.navbar .menu li a').click(function () {
        // applying again smooth scroll on menu items click
        $('html').css('scrollBehavior', 'smooth');
      });

      // toggle menu/navbar script
      $('.menu-btn').click(function () {
        $('.navbar .menu').toggleClass('active');
        $('.menu-btn i').toggleClass('active');
      });

      // typing text animation script
    });
  }
}
