import { ISouscription } from 'app/entities/souscription/souscription.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { EtatCompte } from 'app/entities/enumerations/etat-compte.model';
import { SituationMatrimoniale } from 'app/entities/enumerations/situation-matrimoniale.model';

export interface IMandataireDelegateur {
  id: number;
  nomDeFamille?: string | null;
  prenom?: string | null;
  contact?: string | null;
  email?: string | null;
  numeroMomo?: string | null;
  sexe?: Sexe | null;
  pays?: string | null;
  ville?: string | null;
  adresse?: string | null;
  etatCompte?: EtatCompte | null;
  situationMatrimoniale?: SituationMatrimoniale | null;
  souscriptions?: Pick<ISouscription, 'id' | 'etat'>[] | null;
}

export type NewMandataireDelegateur = Omit<IMandataireDelegateur, 'id'> & { id: null };
