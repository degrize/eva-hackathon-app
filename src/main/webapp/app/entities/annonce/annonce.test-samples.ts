import dayjs from 'dayjs/esm';

import { IAnnonce, NewAnnonce } from './annonce.model';

export const sampleWithRequiredData: IAnnonce = {
  id: 9279,
  titre: 'Electronics',
  dateDeDelais: dayjs('2022-09-09'),
  tarif: 'Plastic blue Personal',
};

export const sampleWithPartialData: IAnnonce = {
  id: 4338,
  titre: 'Cheese b',
  dateDeDelais: dayjs('2022-09-09'),
  tarif: 'a',
};

export const sampleWithFullData: IAnnonce = {
  id: 23712,
  titre: 'b synergistic Car',
  dateDeDelegation: dayjs('2022-09-09T04:51'),
  dateDeDelais: dayjs('2022-09-09'),
  tarif: 'SSL Money',
  postulantRetenu: 29664,
  imageVideo: '../fake-data/blob/hipster.png',
  imageVideoContentType: 'unknown',
  description: 'mindshare cyan Plastic',
};

export const sampleWithNewData: NewAnnonce = {
  titre: 'Berkshire Dollar',
  dateDeDelais: dayjs('2022-09-09'),
  tarif: 'Centre c',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
