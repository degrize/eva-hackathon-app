import { IPostulant, NewPostulant } from './postulant.model';

export const sampleWithRequiredData: IPostulant = {
  id: 6895,
  numeroMomo: 'Islands',
};

export const sampleWithPartialData: IPostulant = {
  id: 62110,
  numeroMomo: 'a',
  observation: 'Champagne-Ardenne Automotive Persistent',
};

export const sampleWithFullData: IPostulant = {
  id: 43869,
  numeroMomo: 'Money',
  observation: 'Chair',
};

export const sampleWithNewData: NewPostulant = {
  numeroMomo: 'Visionary communities bypass',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
