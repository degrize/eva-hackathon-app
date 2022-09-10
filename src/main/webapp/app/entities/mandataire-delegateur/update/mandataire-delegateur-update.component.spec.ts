import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MandataireDelegateurFormService } from './mandataire-delegateur-form.service';
import { MandataireDelegateurService } from '../service/mandataire-delegateur.service';
import { IMandataireDelegateur } from '../mandataire-delegateur.model';

import { MandataireDelegateurUpdateComponent } from './mandataire-delegateur-update.component';

describe('MandataireDelegateur Management Update Component', () => {
  let comp: MandataireDelegateurUpdateComponent;
  let fixture: ComponentFixture<MandataireDelegateurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mandataireDelegateurFormService: MandataireDelegateurFormService;
  let mandataireDelegateurService: MandataireDelegateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MandataireDelegateurUpdateComponent],
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
      .overrideTemplate(MandataireDelegateurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MandataireDelegateurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mandataireDelegateurFormService = TestBed.inject(MandataireDelegateurFormService);
    mandataireDelegateurService = TestBed.inject(MandataireDelegateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const mandataireDelegateur: IMandataireDelegateur = { id: 456 };

      activatedRoute.data = of({ mandataireDelegateur });
      comp.ngOnInit();

      expect(comp.mandataireDelegateur).toEqual(mandataireDelegateur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMandataireDelegateur>>();
      const mandataireDelegateur = { id: 123 };
      jest.spyOn(mandataireDelegateurFormService, 'getMandataireDelegateur').mockReturnValue(mandataireDelegateur);
      jest.spyOn(mandataireDelegateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mandataireDelegateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mandataireDelegateur }));
      saveSubject.complete();

      // THEN
      expect(mandataireDelegateurFormService.getMandataireDelegateur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mandataireDelegateurService.update).toHaveBeenCalledWith(expect.objectContaining(mandataireDelegateur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMandataireDelegateur>>();
      const mandataireDelegateur = { id: 123 };
      jest.spyOn(mandataireDelegateurFormService, 'getMandataireDelegateur').mockReturnValue({ id: null });
      jest.spyOn(mandataireDelegateurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mandataireDelegateur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mandataireDelegateur }));
      saveSubject.complete();

      // THEN
      expect(mandataireDelegateurFormService.getMandataireDelegateur).toHaveBeenCalled();
      expect(mandataireDelegateurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMandataireDelegateur>>();
      const mandataireDelegateur = { id: 123 };
      jest.spyOn(mandataireDelegateurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mandataireDelegateur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mandataireDelegateurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
