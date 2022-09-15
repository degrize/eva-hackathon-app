package com.hackathon.eva.service.dto;

import java.io.Serializable;

public class AdminStatisticsDTO implements Serializable {

    private int nbreTransaction;
    private int nbreAnnonce;
    private int nbreAnnonceDemande;
    private int nbreComptePremium;
    private int nbreCompteNormal;

    public int getNbreTransaction() {
        return nbreTransaction;
    }

    public void setNbreTransaction(int nbreTransaction) {
        this.nbreTransaction = nbreTransaction;
    }

    public int getNbreAnnonce() {
        return nbreAnnonce;
    }

    public void setNbreAnnonce(int nbreAnnonce) {
        this.nbreAnnonce = nbreAnnonce;
    }

    public int getNbreAnnonceDemande() {
        return nbreAnnonceDemande;
    }

    public void setNbreAnnonceDemande(int nbreAnnonceDemande) {
        this.nbreAnnonceDemande = nbreAnnonceDemande;
    }

    public int getNbreComptePremium() {
        return nbreComptePremium;
    }

    public void setNbreComptePremium(int nbreComptePremium) {
        this.nbreComptePremium = nbreComptePremium;
    }

    public int getNbreCompteNormal() {
        return nbreCompteNormal;
    }

    public void setNbreCompteNormal(int nbreCompteNormal) {
        this.nbreCompteNormal = nbreCompteNormal;
    }

    @Override
    public String toString() {
        return (
            "AdminStatisticsDTO{" +
            "nbreTransaction=" +
            nbreTransaction +
            ", nbreAnnonce=" +
            nbreAnnonce +
            ", nbreAnnonceDemande=" +
            nbreAnnonceDemande +
            ", nbreComptePremium=" +
            nbreComptePremium +
            ", nbreCompteNormal=" +
            nbreCompteNormal +
            '}'
        );
    }
}
