export interface IAdminStatistics {
  nbreTransaction?: number;
  nbreAnnonce?: number;
  nbreAnnonceDemande?: number;
  nbreComptePremium?: number;
  nbreCompteNormal?: number;
}

export class AdminStatistics implements IAdminStatistics {
  constructor(
    public nbreTransaction?: number,
    public nbreAnnonce?: number,
    public nbreAnnonceDemande?: number,
    public nbreComptePremium?: number,
    public nbreCompteNormal?: number
  ) {}
}
