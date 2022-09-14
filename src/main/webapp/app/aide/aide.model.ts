export interface IAide {
  id: number;
  message?: string | null;
  nom?: string | null;
  email?: number | null;
}

export type NewAide = Omit<IAide, 'id'> & { id: null };
