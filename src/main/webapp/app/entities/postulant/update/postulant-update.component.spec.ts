import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PostulantFormService } from './postulant-form.service';
import { PostulantService } from '../service/postulant.service';
import { IPostulant } from '../postulant.model';
import { IAnnonce } from 'app/entities/annonce/annonce.model';
import { AnnonceService } from 'app/entities/annonce/service/annonce.service';

import { PostulantUpdateComponent } from './postulant-update.component';

describe('Postulant Management Update Component', () => {
  let comp: PostulantUpdateComponent;
  let fixture: ComponentFixture<PostulantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let postulantFormService: PostulantFormService;
  let postulantService: PostulantService;
  let annonceService: AnnonceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PostulantUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PostulantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PostulantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    postulantFormService = TestBed.inject(PostulantFormService);
    postulantService = TestBed.inject(PostulantService);
    annonceService = TestBed.inject(AnnonceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Annonce query and add missing value', () => {
      const postulant: IPostulant = { id: 456 };
      const annonces: IAnnonce[] = [{ id: 80429 }];
      postulant.annonces = annonces;

      const annonceCollection: IAnnonce[] = [{ id: 73815 }];
      jest.spyOn(annonceService, 'query').mockReturnValue(of(new HttpResponse({ body: annonceCollection })));
      const additionalAnnonces = [...annonces];
      const expectedCollection: IAnnonce[] = [...additionalAnnonces, ...annonceCollection];
      jest.spyOn(annonceService, 'addAnnonceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ postulant });
      comp.ngOnInit();

      expect(annonceService.query).toHaveBeenCalled();
      expect(annonceService.addAnnonceToCollectionIfMissing).toHaveBeenCalledWith(
        annonceCollection,
        ...additionalAnnonces.map(expect.objectContaining)
      );
      expect(comp.annoncesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const postulant: IPostulant = { id: 456 };
      const annonce: IAnnonce = { id: 51870 };
      postulant.annonces = [annonce];

      activatedRoute.data = of({ postulant });
      comp.ngOnInit();

      expect(comp.annoncesSharedCollection).toContain(annonce);
      expect(comp.postulant).toEqual(postulant);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPostulant>>();
      const postulant = { id: 123 };
      jest.spyOn(postulantFormService, 'getPostulant').mockReturnValue(postulant);
      jest.spyOn(postulantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ postulant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: postulant }));
      saveSubject.complete();

      // THEN
      expect(postulantFormService.getPostulant).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(postulantService.update).toHaveBeenCalledWith(expect.objectContaining(postulant));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPostulant>>();
      const postulant = { id: 123 };
      jest.spyOn(postulantFormService, 'getPostulant').mockReturnValue({ id: null });
      jest.spyOn(postulantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ postulant: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: postulant }));
      saveSubject.complete();

      // THEN
      expect(postulantFormService.getPostulant).toHaveBeenCalled();
      expect(postulantService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPostulant>>();
      const postulant = { id: 123 };
      jest.spyOn(postulantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ postulant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(postulantService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAnnonce', () => {
      it('Should forward to annonceService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(annonceService, 'compareAnnonce');
        comp.compareAnnonce(entity, entity2);
        expect(annonceService.compareAnnonce).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
