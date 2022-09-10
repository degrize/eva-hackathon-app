import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandataireDelegateurDetailComponent } from './mandataire-delegateur-detail.component';

describe('MandataireDelegateur Management Detail Component', () => {
  let comp: MandataireDelegateurDetailComponent;
  let fixture: ComponentFixture<MandataireDelegateurDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MandataireDelegateurDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mandataireDelegateur: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MandataireDelegateurDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MandataireDelegateurDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mandataireDelegateur on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mandataireDelegateur).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
