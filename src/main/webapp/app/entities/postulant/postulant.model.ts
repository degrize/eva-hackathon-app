import { IAnnonce } from 'app/entities/annonce/annonce.model';

export interface IPostulant {
  id: number;
  numeroMomo?: string | null;
  observation?: string | null;
  annonces?: Pick<IAnnonce, 'id' | 'titre'>[] | null;
}

export type NewPostulant = Omit<IPostulant, 'id'> & { id: null };
