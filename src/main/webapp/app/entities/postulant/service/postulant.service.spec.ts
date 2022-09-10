import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPostulant } from '../postulant.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../postulant.test-samples';

import { PostulantService } from './postulant.service';

const requireRestSample: IPostulant = {
  ...sampleWithRequiredData,
};

describe('Postulant Service', () => {
  let service: PostulantService;
  let httpMock: HttpTestingController;
  let expectedResult: IPostulant | IPostulant[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PostulantService);
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

    it('should create a Postulant', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const postulant = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(postulant).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Postulant', () => {
      const postulant = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(postulant).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Postulant', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Postulant', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Postulant', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPostulantToCollectionIfMissing', () => {
      it('should add a Postulant to an empty array', () => {
        const postulant: IPostulant = sampleWithRequiredData;
        expectedResult = service.addPostulantToCollectionIfMissing([], postulant);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(postulant);
      });

      it('should not add a Postulant to an array that contains it', () => {
        const postulant: IPostulant = sampleWithRequiredData;
        const postulantCollection: IPostulant[] = [
          {
            ...postulant,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPostulantToCollectionIfMissing(postulantCollection, postulant);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Postulant to an array that doesn't contain it", () => {
        const postulant: IPostulant = sampleWithRequiredData;
        const postulantCollection: IPostulant[] = [sampleWithPartialData];
        expectedResult = service.addPostulantToCollectionIfMissing(postulantCollection, postulant);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(postulant);
      });

      it('should add only unique Postulant to an array', () => {
        const postulantArray: IPostulant[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const postulantCollection: IPostulant[] = [sampleWithRequiredData];
        expectedResult = service.addPostulantToCollectionIfMissing(postulantCollection, ...postulantArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const postulant: IPostulant = sampleWithRequiredData;
        const postulant2: IPostulant = sampleWithPartialData;
        expectedResult = service.addPostulantToCollectionIfMissing([], postulant, postulant2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(postulant);
        expect(expectedResult).toContain(postulant2);
      });

      it('should accept null and undefined values', () => {
        const postulant: IPostulant = sampleWithRequiredData;
        expectedResult = service.addPostulantToCollectionIfMissing([], null, postulant, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(postulant);
      });

      it('should return initial array if no Postulant is added', () => {
        const postulantCollection: IPostulant[] = [sampleWithRequiredData];
        expectedResult = service.addPostulantToCollectionIfMissing(postulantCollection, undefined, null);
        expect(expectedResult).toEqual(postulantCollection);
      });
    });

    describe('comparePostulant', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePostulant(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePostulant(entity1, entity2);
        const compareResult2 = service.comparePostulant(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePostulant(entity1, entity2);
        const compareResult2 = service.comparePostulant(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePostulant(entity1, entity2);
        const compareResult2 = service.comparePostulant(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
