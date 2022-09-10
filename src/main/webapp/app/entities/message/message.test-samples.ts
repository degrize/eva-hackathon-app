import { IMessage, NewMessage } from './message.model';

export const sampleWithRequiredData: IMessage = {
  id: 29027,
};

export const sampleWithPartialData: IMessage = {
  id: 63402,
  texte: 'SQL',
};

export const sampleWithFullData: IMessage = {
  id: 57303,
  texte: 'b hack b',
  fichierJoin: '../fake-data/blob/hipster.png',
  fichierJoinContentType: 'unknown',
};

export const sampleWithNewData: NewMessage = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
