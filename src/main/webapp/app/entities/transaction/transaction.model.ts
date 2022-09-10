import dayjs from 'dayjs/esm';
import { IAnnonce } from 'app/entities/annonce/annonce.model';
import { IPostulant } from 'app/entities/postulant/postulant.model';
import { Devise } from 'app/entities/enumerations/devise.model';

export interface ITransaction {
  id: number;
  numeroMtn?: string | null;
  montant?: number | null;
  devise?: Devise | null;
  dateTransaction?: dayjs.Dayjs | null;
  precision?: string | null;
  annonce?: Pick<IAnnonce, 'id' | 'titre'> | null;
  postulant?: Pick<IPostulant, 'id'> | null;
}

export type NewTransaction = Omit<ITransaction, 'id'> & { id: null };
