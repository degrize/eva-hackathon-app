import { Sexe } from 'app/entities/enumerations/sexe.model';
import { EtatCompte } from 'app/entities/enumerations/etat-compte.model';
import { SituationMatrimoniale } from 'app/entities/enumerations/situation-matrimoniale.model';

import { IMandataireDelegateur, NewMandataireDelegateur } from './mandataire-delegateur.model';

export const sampleWithRequiredData: IMandataireDelegateur = {
  id: 10869,
  nomDeFamille: 'Buckinghamshire protocol olive',
  prenom: 'SCSI Île-de-France',
  contact: 'teal',
  email: 'Julie51@hotmail.fr',
  numeroMomo: 'input',
  sexe: Sexe['F'],
};

export const sampleWithPartialData: IMandataireDelegateur = {
  id: 2508,
  nomDeFamille: 'Ergonomic Dinar Corse',
  prenom: 'functionalities IB Inverse',
  contact: 'Franche-Comté alarm',
  email: 'lie_Gautier@hotmail.fr',
  numeroMomo: 'Horizontal',
  sexe: Sexe['JE_PREFERE_NE_PAS_LE_DIRE'],
  etatCompte: EtatCompte['NORMAL'],
};

export const sampleWithFullData: IMandataireDelegateur = {
  id: 48327,
  nomDeFamille: 'models',
  prenom: 'Grocery Avon open-source',
  contact: 'Panoramas index',
  email: 'Flicie12@hotmail.fr',
  numeroMomo: 'communities Refined',
  sexe: Sexe['M'],
  pays: 'copying Architecte Chair',
  ville: 'Ergonomic Grocery',
  adresse: 'Borders knowledge',
  etatCompte: EtatCompte['NORMAL'],
  situationMatrimoniale: SituationMatrimoniale['CONCUBINAGE'],
};

export const sampleWithNewData: NewMandataireDelegateur = {
  nomDeFamille: 'Bourgogne b',
  prenom: 'Picardie',
  contact: 'Vanuatu Picardie',
  email: 'Aveline_Morin@yahoo.fr',
  numeroMomo: 'Cloned invoice',
  sexe: Sexe['JE_PREFERE_NE_PAS_LE_DIRE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
