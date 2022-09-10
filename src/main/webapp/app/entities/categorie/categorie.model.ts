import { IAnnonce } from 'app/entities/annonce/annonce.model';

export interface ICategorie {
  id: number;
  nom?: string | null;
  description?: string | null;
  annonces?: Pick<IAnnonce, 'id' | 'titre'>[] | null;
}

export type NewCategorie = Omit<ICategorie, 'id'> & { id: null };
