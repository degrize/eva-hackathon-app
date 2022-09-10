import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PostulantDetailComponent } from './postulant-detail.component';

describe('Postulant Management Detail Component', () => {
  let comp: PostulantDetailComponent;
  let fixture: ComponentFixture<PostulantDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PostulantDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ postulant: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PostulantDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PostulantDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load postulant on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.postulant).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
