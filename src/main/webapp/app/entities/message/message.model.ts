import { IAnnonce } from 'app/entities/annonce/annonce.model';

export interface IMessage {
  id: number;
  texte?: string | null;
  fichierJoin?: string | null;
  fichierJoinContentType?: string | null;
  annonce?: Pick<IAnnonce, 'id' | 'titre'> | null;
}

export type NewMessage = Omit<IMessage, 'id'> & { id: null };
