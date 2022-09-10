import { ICategorie, NewCategorie } from './categorie.model';

export const sampleWithRequiredData: ICategorie = {
  id: 37918,
  nom: 'connect',
};

export const sampleWithPartialData: ICategorie = {
  id: 4633,
  nom: 'calculating Specialiste',
  description: 'c withdrawal yellow',
};

export const sampleWithFullData: ICategorie = {
  id: 25142,
  nom: 'systems',
  description: 'matrix',
};

export const sampleWithNewData: NewCategorie = {
  nom: 'alliance',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
