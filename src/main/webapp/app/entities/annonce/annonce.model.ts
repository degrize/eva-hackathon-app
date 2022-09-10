import dayjs from 'dayjs/esm';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { IMandataireDelegateur } from 'app/entities/mandataire-delegateur/mandataire-delegateur.model';
import { IPostulant } from 'app/entities/postulant/postulant.model';

export interface IAnnonce {
  id: number;
  titre?: string | null;
  dateDeDelegation?: dayjs.Dayjs | null;
  dateDeDelais?: dayjs.Dayjs | null;
  tarif?: string | null;
  postulantRetenu?: number | null;
  imageVideo?: string | null;
  imageVideoContentType?: string | null;
  description?: string | null;
  categories?: Pick<ICategorie, 'id' | 'nom'>[] | null;
  mandataireDelegateur?: Pick<IMandataireDelegateur, 'id'> | null;
  postulants?: Pick<IPostulant, 'id'>[] | null;
}

export type NewAnnonce = Omit<IAnnonce, 'id'> & { id: null };
