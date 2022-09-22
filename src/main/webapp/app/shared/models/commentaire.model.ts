import { IMandataireDelegateur } from '../../entities/mandataire-delegateur/mandataire-delegateur.model';
import { IAnnonce } from '../../entities/annonce/annonce.model';

export interface ICommentaire {
  id: number;
  mandataireDelegateur?: IMandataireDelegateur | null;
  message?: string | null;
  dateDeMessage?: string | null;
  email?: string | null;
  nomCommentateur?: string | null;
}
export type NewCommentaire = Omit<ICommentaire, 'id'> & { id: null };
