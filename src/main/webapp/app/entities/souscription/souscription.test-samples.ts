import { EtatCompte } from 'app/entities/enumerations/etat-compte.model';

import { ISouscription, NewSouscription } from './souscription.model';

export const sampleWithRequiredData: ISouscription = {
  id: 25092,
  etat: EtatCompte['PREMIUM'],
  montant: 59246,
  pourcentageDuDon: 25433,
};

export const sampleWithPartialData: ISouscription = {
  id: 81403,
  etat: EtatCompte['PREMIUM'],
  montant: 73739,
  pourcentageDuDon: 71753,
};

export const sampleWithFullData: ISouscription = {
  id: 81168,
  etat: EtatCompte['NORMAL'],
  montant: 51913,
  pourcentageDuDon: 69259,
};

export const sampleWithNewData: NewSouscription = {
  etat: EtatCompte['NORMAL'],
  montant: 31913,
  pourcentageDuDon: 73064,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
