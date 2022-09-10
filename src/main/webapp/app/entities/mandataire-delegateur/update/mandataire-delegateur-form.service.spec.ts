import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mandataire-delegateur.test-samples';

import { MandataireDelegateurFormService } from './mandataire-delegateur-form.service';

describe('MandataireDelegateur Form Service', () => {
  let service: MandataireDelegateurFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MandataireDelegateurFormService);
  });

  describe('Service methods', () => {
    describe('createMandataireDelegateurFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMandataireDelegateurFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomDeFamille: expect.any(Object),
            prenom: expect.any(Object),
            contact: expect.any(Object),
            email: expect.any(Object),
            numeroMomo: expect.any(Object),
            sexe: expect.any(Object),
            pays: expect.any(Object),
            ville: expect.any(Object),
            adresse: expect.any(Object),
            etatCompte: expect.any(Object),
            situationMatrimoniale: expect.any(Object),
            souscriptions: expect.any(Object),
          })
        );
      });

      it('passing IMandataireDelegateur should create a new form with FormGroup', () => {
        const formGroup = service.createMandataireDelegateurFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomDeFamille: expect.any(Object),
            prenom: expect.any(Object),
            contact: expect.any(Object),
            email: expect.any(Object),
            numeroMomo: expect.any(Object),
            sexe: expect.any(Object),
            pays: expect.any(Object),
            ville: expect.any(Object),
            adresse: expect.any(Object),
            etatCompte: expect.any(Object),
            situationMatrimoniale: expect.any(Object),
            souscriptions: expect.any(Object),
          })
        );
      });
    });

    describe('getMandataireDelegateur', () => {
      it('should return NewMandataireDelegateur for default MandataireDelegateur initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMandataireDelegateurFormGroup(sampleWithNewData);

        const mandataireDelegateur = service.getMandataireDelegateur(formGroup) as any;

        expect(mandataireDelegateur).toMatchObject(sampleWithNewData);
      });

      it('should return NewMandataireDelegateur for empty MandataireDelegateur initial value', () => {
        const formGroup = service.createMandataireDelegateurFormGroup();

        const mandataireDelegateur = service.getMandataireDelegateur(formGroup) as any;

        expect(mandataireDelegateur).toMatchObject({});
      });

      it('should return IMandataireDelegateur', () => {
        const formGroup = service.createMandataireDelegateurFormGroup(sampleWithRequiredData);

        const mandataireDelegateur = service.getMandataireDelegateur(formGroup) as any;

        expect(mandataireDelegateur).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMandataireDelegateur should not enable id FormControl', () => {
        const formGroup = service.createMandataireDelegateurFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMandataireDelegateur should disable id FormControl', () => {
        const formGroup = service.createMandataireDelegateurFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
