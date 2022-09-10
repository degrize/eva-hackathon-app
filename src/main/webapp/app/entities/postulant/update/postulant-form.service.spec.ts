import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../postulant.test-samples';

import { PostulantFormService } from './postulant-form.service';

describe('Postulant Form Service', () => {
  let service: PostulantFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PostulantFormService);
  });

  describe('Service methods', () => {
    describe('createPostulantFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPostulantFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeroMomo: expect.any(Object),
            observation: expect.any(Object),
            annonces: expect.any(Object),
          })
        );
      });

      it('passing IPostulant should create a new form with FormGroup', () => {
        const formGroup = service.createPostulantFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeroMomo: expect.any(Object),
            observation: expect.any(Object),
            annonces: expect.any(Object),
          })
        );
      });
    });

    describe('getPostulant', () => {
      it('should return NewPostulant for default Postulant initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPostulantFormGroup(sampleWithNewData);

        const postulant = service.getPostulant(formGroup) as any;

        expect(postulant).toMatchObject(sampleWithNewData);
      });

      it('should return NewPostulant for empty Postulant initial value', () => {
        const formGroup = service.createPostulantFormGroup();

        const postulant = service.getPostulant(formGroup) as any;

        expect(postulant).toMatchObject({});
      });

      it('should return IPostulant', () => {
        const formGroup = service.createPostulantFormGroup(sampleWithRequiredData);

        const postulant = service.getPostulant(formGroup) as any;

        expect(postulant).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPostulant should not enable id FormControl', () => {
        const formGroup = service.createPostulantFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPostulant should disable id FormControl', () => {
        const formGroup = service.createPostulantFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
