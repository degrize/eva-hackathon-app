import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AnnonceFormService } from './annonce-form.service';
import { AnnonceService } from '../service/annonce.service';
import { IAnnonce } from '../annonce.model';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { IMandataireDelegateur } from 'app/entities/mandataire-delegateur/mandataire-delegateur.model';
import { MandataireDelegateurService } from 'app/entities/mandataire-delegateur/service/mandataire-delegateur.service';

import { AnnonceUpdateComponent } from './annonce-update.component';

describe('Annonce Management Update Component', () => {
  let comp: AnnonceUpdateComponent;
  let fixture: ComponentFixture<AnnonceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let annonceFormService: AnnonceFormService;
  let annonceService: AnnonceService;
  let categorieService: CategorieService;
  let mandataireDelegateurService: MandataireDelegateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AnnonceUpdateComponent],
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
      .overrideTemplate(AnnonceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnnonceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    annonceFormService = TestBed.inject(AnnonceFormService);
    annonceService = TestBed.inject(AnnonceService);
    categorieService = TestBed.inject(CategorieService);
    mandataireDelegateurService = TestBed.inject(MandataireDelegateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Categorie query and add missing value', () => {
      const annonce: IAnnonce = { id: 456 };
      const categories: ICategorie[] = [{ id: 78310 }];
      annonce.categories = categories;

      const categorieCollection: ICategorie[] = [{ id: 51238 }];
      jest.spyOn(categorieService, 'query').mockReturnValue(of(new HttpResponse({ body: categorieCollection })));
      const additionalCategories = [...categories];
      const expectedCollection: ICategorie[] = [...additionalCategories, ...categorieCollection];
      jest.spyOn(categorieService, 'addCategorieToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ annonce });
      comp.ngOnInit();

      expect(categorieService.query).toHaveBeenCalled();
      expect(categorieService.addCategorieToCollectionIfMissing).toHaveBeenCalledWith(
        categorieCollection,
        ...additionalCategories.map(expect.objectContaining)
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MandataireDelegateur query and add missing value', () => {
      const annonce: IAnnonce = { id: 456 };
      const mandataireDelegateur: IMandataireDelegateur = { id: 62219 };
      annonce.mandataireDelegateur = mandataireDelegateur;

      const mandataireDelegateurCollection: IMandataireDelegateur[] = [{ id: 39899 }];
      jest.spyOn(mandataireDelegateurService, 'query').mockReturnValue(of(new HttpResponse({ body: mandataireDelegateurCollection })));
      const additionalMandataireDelegateurs = [mandataireDelegateur];
      const expectedCollection: IMandataireDelegateur[] = [...additionalMandataireDelegateurs, ...mandataireDelegateurCollection];
      jest.spyOn(mandataireDelegateurService, 'addMandataireDelegateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ annonce });
      comp.ngOnInit();

      expect(mandataireDelegateurService.query).toHaveBeenCalled();
      expect(mandataireDelegateurService.addMandataireDelegateurToCollectionIfMissing).toHaveBeenCalledWith(
        mandataireDelegateurCollection,
        ...additionalMandataireDelegateurs.map(expect.objectContaining)
      );
      expect(comp.mandataireDelegateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const annonce: IAnnonce = { id: 456 };
      const categorie: ICategorie = { id: 20203 };
      annonce.categories = [categorie];
      const mandataireDelegateur: IMandataireDelegateur = { id: 12480 };
      annonce.mandataireDelegateur = mandataireDelegateur;

      activatedRoute.data = of({ annonce });
      comp.ngOnInit();

      expect(comp.categoriesSharedCollection).toContain(categorie);
      expect(comp.mandataireDelegateursSharedCollection).toContain(mandataireDelegateur);
      expect(comp.annonce).toEqual(annonce);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnnonce>>();
      const annonce = { id: 123 };
      jest.spyOn(annonceFormService, 'getAnnonce').mockReturnValue(annonce);
      jest.spyOn(annonceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ annonce });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: annonce }));
      saveSubject.complete();

      // THEN
      expect(annonceFormService.getAnnonce).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(annonceService.update).toHaveBeenCalledWith(expect.objectContaining(annonce));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnnonce>>();
      const annonce = { id: 123 };
      jest.spyOn(annonceFormService, 'getAnnonce').mockReturnValue({ id: null });
      jest.spyOn(annonceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ annonce: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: annonce }));
      saveSubject.complete();

      // THEN
      expect(annonceFormService.getAnnonce).toHaveBeenCalled();
      expect(annonceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnnonce>>();
      const annonce = { id: 123 };
      jest.spyOn(annonceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ annonce });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(annonceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategorie', () => {
      it('Should forward to categorieService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categorieService, 'compareCategorie');
        comp.compareCategorie(entity, entity2);
        expect(categorieService.compareCategorie).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMandataireDelegateur', () => {
      it('Should forward to mandataireDelegateurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(mandataireDelegateurService, 'compareMandataireDelegateur');
        comp.compareMandataireDelegateur(entity, entity2);
        expect(mandataireDelegateurService.compareMandataireDelegateur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
