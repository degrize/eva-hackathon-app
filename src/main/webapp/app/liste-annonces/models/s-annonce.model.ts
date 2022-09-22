import { ICategorie } from '../../entities/categorie/categorie.model';
import dayjs from 'dayjs/esm';
import { IPostulant } from '../../entities/postulant/postulant.model';
import { IMandataireDelegateur } from '../../entities/mandataire-delegateur/mandataire-delegateur.model';

export class SAnnonce {
  id!: number;
  titre!: string;
  categorieSearch!: string;
  dateDeDelegation!: dayjs.Dayjs;
  dateDeDelais!: dayjs.Dayjs;
  tarif!: string;
  mandataireDelegateur?: IMandataireDelegateur | null;
  duree!: string;
  imageUrl!: string;
  description!: string;
  photoUrl!: string;
  imageVideo!: string;
  imageVideoContentType!: string;
  postulantRetenu!: number;
  postulants!: IPostulant[];
  categories!: Pick<ICategorie, 'id' | 'nom'>[];
}
