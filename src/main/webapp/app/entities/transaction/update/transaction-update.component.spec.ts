import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransactionFormService } from './transaction-form.service';
import { TransactionService } from '../service/transaction.service';
import { ITransaction } from '../transaction.model';
import { IAnnonce } from 'app/entities/annonce/annonce.model';
import { AnnonceService } from 'app/entities/annonce/service/annonce.service';
import { IPostulant } from 'app/entities/postulant/postulant.model';
import { PostulantService } from 'app/entities/postulant/service/postulant.service';

import { TransactionUpdateComponent } from './transaction-update.component';

describe('Transaction Management Update Component', () => {
  let comp: TransactionUpdateComponent;
  let fixture: ComponentFixture<TransactionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transactionFormService: TransactionFormService;
  let transactionService: TransactionService;
  let annonceService: AnnonceService;
  let postulantService: PostulantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransactionUpdateComponent],
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
      .overrideTemplate(TransactionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransactionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transactionFormService = TestBed.inject(TransactionFormService);
    transactionService = TestBed.inject(TransactionService);
    annonceService = TestBed.inject(AnnonceService);
    postulantService = TestBed.inject(PostulantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call annonce query and add missing value', () => {
      const transaction: ITransaction = { id: 456 };
      const annonce: IAnnonce = { id: 79643 };
      transaction.annonce = annonce;

      const annonceCollection: IAnnonce[] = [{ id: 8292 }];
      jest.spyOn(annonceService, 'query').mockReturnValue(of(new HttpResponse({ body: annonceCollection })));
      const expectedCollection: IAnnonce[] = [annonce, ...annonceCollection];
      jest.spyOn(annonceService, 'addAnnonceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transaction });
      comp.ngOnInit();

      expect(annonceService.query).toHaveBeenCalled();
      expect(annonceService.addAnnonceToCollectionIfMissing).toHaveBeenCalledWith(annonceCollection, annonce);
      expect(comp.annoncesCollection).toEqual(expectedCollection);
    });

    it('Should call postulant query and add missing value', () => {
      const transaction: ITransaction = { id: 456 };
      const postulant: IPostulant = { id: 13059 };
      transaction.postulant = postulant;

      const postulantCollection: IPostulant[] = [{ id: 24744 }];
      jest.spyOn(postulantService, 'query').mockReturnValue(of(new HttpResponse({ body: postulantCollection })));
      const expectedCollection: IPostulant[] = [postulant, ...postulantCollection];
      jest.spyOn(postulantService, 'addPostulantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transaction });
      comp.ngOnInit();

      expect(postulantService.query).toHaveBeenCalled();
      expect(postulantService.addPostulantToCollectionIfMissing).toHaveBeenCalledWith(postulantCollection, postulant);
      expect(comp.postulantsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transaction: ITransaction = { id: 456 };
      const annonce: IAnnonce = { id: 17065 };
      transaction.annonce = annonce;
      const postulant: IPostulant = { id: 30417 };
      transaction.postulant = postulant;

      activatedRoute.data = of({ transaction });
      comp.ngOnInit();

      expect(comp.annoncesCollection).toContain(annonce);
      expect(comp.postulantsCollection).toContain(postulant);
      expect(comp.transaction).toEqual(transaction);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransaction>>();
      const transaction = { id: 123 };
      jest.spyOn(transactionFormService, 'getTransaction').mockReturnValue(transaction);
      jest.spyOn(transactionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transaction }));
      saveSubject.complete();

      // THEN
      expect(transactionFormService.getTransaction).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(transactionService.update).toHaveBeenCalledWith(expect.objectContaining(transaction));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransaction>>();
      const transaction = { id: 123 };
      jest.spyOn(transactionFormService, 'getTransaction').mockReturnValue({ id: null });
      jest.spyOn(transactionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transaction: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transaction }));
      saveSubject.complete();

      // THEN
      expect(transactionFormService.getTransaction).toHaveBeenCalled();
      expect(transactionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITransaction>>();
      const transaction = { id: 123 };
      jest.spyOn(transactionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transaction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transactionService.update).toHaveBeenCalled();
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

    describe('comparePostulant', () => {
      it('Should forward to postulantService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(postulantService, 'comparePostulant');
        comp.comparePostulant(entity, entity2);
        expect(postulantService.comparePostulant).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
