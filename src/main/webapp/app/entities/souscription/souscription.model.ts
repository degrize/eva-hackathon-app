import { IMandataireDelegateur } from 'app/entities/mandataire-delegateur/mandataire-delegateur.model';
import { EtatCompte } from 'app/entities/enumerations/etat-compte.model';

export interface ISouscription {
  id: number;
  etat?: EtatCompte | null;
  montant?: number | null;
  pourcentageDuDon?: number | null;
  mandataireDelegateurs?: Pick<IMandataireDelegateur, 'id' | 'numeroMomo'>[] | null;
}

export type NewSouscription = Omit<ISouscription, 'id'> & { id: null };
