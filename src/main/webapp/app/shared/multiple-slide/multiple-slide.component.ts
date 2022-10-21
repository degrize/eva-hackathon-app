import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-multiple-slide',
  templateUrl: './multiple-slide.component.html',
  styleUrls: ['./multiple-slide.component.scss'],
})
export class MultipleSlideComponent implements OnInit {
  data = [1, 2, 3, 4];

  constructor() {}

  ngOnInit(): void {
    this.startSlider();
  }

  startSlider(): void {
    const inputRadios = document.getElementsByTagName('input');
    const divDots = document.getElementById('dots');
    const divTestimonials = document.getElementById('testimonials');
    if (divDots && divTestimonials) {
      let radioLabel = divDots.getElementsByTagName('label');
      let testimonialsLabel = divTestimonials.getElementsByTagName('label');
      for (let i = 0; i < inputRadios.length; i++) {
        if (inputRadios[i].type === 'radio') {
          if (inputRadios[i].name === 'testimonial') {
            inputRadios[i].addEventListener('change', function () {
              for (let j = 0; j < radioLabel.length; j++) {
                if (radioLabel[j].getAttribute('for') === inputRadios[i].id) {
                  radioLabel[j].style.transform = 'scale(2)';
                  radioLabel[j].style.backgroundColor = '#fff';
                  radioLabel[j].style.boxShadow = '0px 0px 0px 3px #dddddd24';
                } else {
                  radioLabel[j].style.transform = 'scale(1)';
                  radioLabel[j].style.backgroundColor = '#413B52';
                  radioLabel[j].style.boxShadow = '0px 0px 0px 0px';
                }
              }

              for (let k = 0; k < testimonialsLabel.length; k++) {
                testimonialsLabel[k].style.transform = 'none';
                testimonialsLabel[k].style.zIndex = '1';
              }

              let index;
              let labelTst;
              let labelTst2;
              let labelTst3;
              let labelTst4;
              let labelTst5;
              if (i + 3 > testimonialsLabel.length) {
                index = i + 2 - 5;
                labelTst = document.getElementById('lab' + index);
                alert(index);
              } else {
                index = i;
                labelTst = document.getElementById('lab' + index + 3);
              }

              if (i + 2 > testimonialsLabel.length) {
                index = i + 1 - 5;
                labelTst2 = document.getElementById('lab' + index);
                alert(index);
              } else {
                index = i;
                labelTst2 = document.getElementById('lab' + index + 2);
              }

              if (i + 5 > testimonialsLabel.length) {
                index = i + 4 - 5;
                labelTst3 = document.getElementById('lab' + index);
                alert(index);
              } else {
                index = i;
                labelTst3 = document.getElementById('lab' + index + 5);
              }

              if (i + 4 > testimonialsLabel.length) {
                index = i + 3 - 5;
                labelTst4 = document.getElementById('lab' + index);
                alert(index);
              } else {
                index = i;
                labelTst4 = document.getElementById('lab' + index + 4);
              }

              if (i + 1 > testimonialsLabel.length) {
                index = i - 5;
                labelTst5 = document.getElementById('lab' + index);
                alert(index);
              } else {
                index = i;
                labelTst5 = document.getElementById('lab' + index + 1);
              }

              if (labelTst && labelTst2 && labelTst3 && labelTst4 && labelTst5) {
                labelTst.style.transform = 'translate3d(600px, 0, -180px) rotateY(-25deg)';
                labelTst.style.zIndex = '2';

                labelTst2.style.transform = 'translate3d(300px, 0, -90px) rotateY(-15deg)';
                labelTst2.style.zIndex = '3';

                labelTst3.style.transform = 'translate3d(-300px, 0, -90px) rotateY(15deg)';
                labelTst3.style.zIndex = '3';

                labelTst4.style.transform = 'translate3d(-600px, 0, -180px) rotateY(25deg)';
                labelTst5.style.zIndex = '4';
              }
            });
          }
        }
      }
    }
  }
}
