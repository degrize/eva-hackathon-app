import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMandataireDelegateur } from '../mandataire-delegateur.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../mandataire-delegateur.test-samples';

import { MandataireDelegateurService } from './mandataire-delegateur.service';

const requireRestSample: IMandataireDelegateur = {
  ...sampleWithRequiredData,
};

describe('MandataireDelegateur Service', () => {
  let service: MandataireDelegateurService;
  let httpMock: HttpTestingController;
  let expectedResult: IMandataireDelegateur | IMandataireDelegateur[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MandataireDelegateurService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a MandataireDelegateur', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const mandataireDelegateur = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mandataireDelegateur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MandataireDelegateur', () => {
      const mandataireDelegateur = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mandataireDelegateur).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MandataireDelegateur', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MandataireDelegateur', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MandataireDelegateur', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMandataireDelegateurToCollectionIfMissing', () => {
      it('should add a MandataireDelegateur to an empty array', () => {
        const mandataireDelegateur: IMandataireDelegateur = sampleWithRequiredData;
        expectedResult = service.addMandataireDelegateurToCollectionIfMissing([], mandataireDelegateur);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mandataireDelegateur);
      });

      it('should not add a MandataireDelegateur to an array that contains it', () => {
        const mandataireDelegateur: IMandataireDelegateur = sampleWithRequiredData;
        const mandataireDelegateurCollection: IMandataireDelegateur[] = [
          {
            ...mandataireDelegateur,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMandataireDelegateurToCollectionIfMissing(mandataireDelegateurCollection, mandataireDelegateur);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MandataireDelegateur to an array that doesn't contain it", () => {
        const mandataireDelegateur: IMandataireDelegateur = sampleWithRequiredData;
        const mandataireDelegateurCollection: IMandataireDelegateur[] = [sampleWithPartialData];
        expectedResult = service.addMandataireDelegateurToCollectionIfMissing(mandataireDelegateurCollection, mandataireDelegateur);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mandataireDelegateur);
      });

      it('should add only unique MandataireDelegateur to an array', () => {
        const mandataireDelegateurArray: IMandataireDelegateur[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mandataireDelegateurCollection: IMandataireDelegateur[] = [sampleWithRequiredData];
        expectedResult = service.addMandataireDelegateurToCollectionIfMissing(mandataireDelegateurCollection, ...mandataireDelegateurArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mandataireDelegateur: IMandataireDelegateur = sampleWithRequiredData;
        const mandataireDelegateur2: IMandataireDelegateur = sampleWithPartialData;
        expectedResult = service.addMandataireDelegateurToCollectionIfMissing([], mandataireDelegateur, mandataireDelegateur2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mandataireDelegateur);
        expect(expectedResult).toContain(mandataireDelegateur2);
      });

      it('should accept null and undefined values', () => {
        const mandataireDelegateur: IMandataireDelegateur = sampleWithRequiredData;
        expectedResult = service.addMandataireDelegateurToCollectionIfMissing([], null, mandataireDelegateur, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mandataireDelegateur);
      });

      it('should return initial array if no MandataireDelegateur is added', () => {
        const mandataireDelegateurCollection: IMandataireDelegateur[] = [sampleWithRequiredData];
        expectedResult = service.addMandataireDelegateurToCollectionIfMissing(mandataireDelegateurCollection, undefined, null);
        expect(expectedResult).toEqual(mandataireDelegateurCollection);
      });
    });

    describe('compareMandataireDelegateur', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMandataireDelegateur(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMandataireDelegateur(entity1, entity2);
        const compareResult2 = service.compareMandataireDelegateur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMandataireDelegateur(entity1, entity2);
        const compareResult2 = service.compareMandataireDelegateur(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMandataireDelegateur(entity1, entity2);
        const compareResult2 = service.compareMandataireDelegateur(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
