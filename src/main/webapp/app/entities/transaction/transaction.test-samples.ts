import dayjs from 'dayjs/esm';

import { Devise } from 'app/entities/enumerations/devise.model';

import { ITransaction, NewTransaction } from './transaction.model';

export const sampleWithRequiredData: ITransaction = {
  id: 73739,
  numeroMtn: 'wireless',
  montant: 52022,
  devise: Devise['DOLLAR'],
};

export const sampleWithPartialData: ITransaction = {
  id: 31020,
  numeroMtn: 'Avon Vaugirard',
  montant: 58125,
  devise: Devise['DLASI'],
  dateTransaction: dayjs('2022-09-09T09:09'),
  precision: 'olive',
};

export const sampleWithFullData: ITransaction = {
  id: 29096,
  numeroMtn: 'static Profound Bike',
  montant: 7504,
  devise: Devise['LEONE'],
  dateTransaction: dayjs('2022-09-09T03:58'),
  precision: 'navigating multi-byte primary',
};

export const sampleWithNewData: NewTransaction = {
  numeroMtn: 'up reboot deposit',
  montant: 71162,
  devise: Devise['DLASI'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
