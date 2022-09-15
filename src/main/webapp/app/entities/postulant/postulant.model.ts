import { IAnnonce } from 'app/entities/annonce/annonce.model';
import { IMandataireDelegateur } from '../mandataire-delegateur/mandataire-delegateur.model';

export interface IPostulant {
  id: number;
  numeroMomo?: string | null;
  observation?: string | null;
  mandataireDelegateur?: IMandataireDelegateur | null;
  annonces?: Pick<IAnnonce, 'id' | 'titre'>[] | null;
}

export type NewPostulant = Omit<IPostulant, 'id'> & { id: null };
