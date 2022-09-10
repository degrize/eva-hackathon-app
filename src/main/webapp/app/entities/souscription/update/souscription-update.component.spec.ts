import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SouscriptionFormService } from './souscription-form.service';
import { SouscriptionService } from '../service/souscription.service';
import { ISouscription } from '../souscription.model';
import { IMandataireDelegateur } from 'app/entities/mandataire-delegateur/mandataire-delegateur.model';
import { MandataireDelegateurService } from 'app/entities/mandataire-delegateur/service/mandataire-delegateur.service';

import { SouscriptionUpdateComponent } from './souscription-update.component';

describe('Souscription Management Update Component', () => {
  let comp: SouscriptionUpdateComponent;
  let fixture: ComponentFixture<SouscriptionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let souscriptionFormService: SouscriptionFormService;
  let souscriptionService: SouscriptionService;
  let mandataireDelegateurService: MandataireDelegateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SouscriptionUpdateComponent],
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
      .overrideTemplate(SouscriptionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SouscriptionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    souscriptionFormService = TestBed.inject(SouscriptionFormService);
    souscriptionService = TestBed.inject(SouscriptionService);
    mandataireDelegateurService = TestBed.inject(MandataireDelegateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MandataireDelegateur query and add missing value', () => {
      const souscription: ISouscription = { id: 456 };
      const mandataireDelegateurs: IMandataireDelegateur[] = [{ id: 7951 }];
      souscription.mandataireDelegateurs = mandataireDelegateurs;

      const mandataireDelegateurCollection: IMandataireDelegateur[] = [{ id: 95144 }];
      jest.spyOn(mandataireDelegateurService, 'query').mockReturnValue(of(new HttpResponse({ body: mandataireDelegateurCollection })));
      const additionalMandataireDelegateurs = [...mandataireDelegateurs];
      const expectedCollection: IMandataireDelegateur[] = [...additionalMandataireDelegateurs, ...mandataireDelegateurCollection];
      jest.spyOn(mandataireDelegateurService, 'addMandataireDelegateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ souscription });
      comp.ngOnInit();

      expect(mandataireDelegateurService.query).toHaveBeenCalled();
      expect(mandataireDelegateurService.addMandataireDelegateurToCollectionIfMissing).toHaveBeenCalledWith(
        mandataireDelegateurCollection,
        ...additionalMandataireDelegateurs.map(expect.objectContaining)
      );
      expect(comp.mandataireDelegateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const souscription: ISouscription = { id: 456 };
      const mandataireDelegateur: IMandataireDelegateur = { id: 62320 };
      souscription.mandataireDelegateurs = [mandataireDelegateur];

      activatedRoute.data = of({ souscription });
      comp.ngOnInit();

      expect(comp.mandataireDelegateursSharedCollection).toContain(mandataireDelegateur);
      expect(comp.souscription).toEqual(souscription);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISouscription>>();
      const souscription = { id: 123 };
      jest.spyOn(souscriptionFormService, 'getSouscription').mockReturnValue(souscription);
      jest.spyOn(souscriptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ souscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: souscription }));
      saveSubject.complete();

      // THEN
      expect(souscriptionFormService.getSouscription).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(souscriptionService.update).toHaveBeenCalledWith(expect.objectContaining(souscription));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISouscription>>();
      const souscription = { id: 123 };
      jest.spyOn(souscriptionFormService, 'getSouscription').mockReturnValue({ id: null });
      jest.spyOn(souscriptionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ souscription: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: souscription }));
      saveSubject.complete();

      // THEN
      expect(souscriptionFormService.getSouscription).toHaveBeenCalled();
      expect(souscriptionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISouscription>>();
      const souscription = { id: 123 };
      jest.spyOn(souscriptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ souscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(souscriptionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
