package com.hackathon.eva.service.dto;

public class ReportDTO {

    private String dateCourante;

    private int totalAnnonce;

    private int totalAnnonceDemande;

    private int totalComptepremium;

    private int totalTransaction;

    public String getDateCourante() {
        return dateCourante;
    }

    public void setDateCourante(String dateCourante) {
        this.dateCourante = dateCourante;
    }

    public int getTotalAnnonce() {
        return totalAnnonce;
    }

    public void setTotalAnnonce(int totalAnnonce) {
        this.totalAnnonce = totalAnnonce;
    }

    public int getTotalAnnonceDemande() {
        return totalAnnonceDemande;
    }

    public void setTotalAnnonceDemande(int totalAnnonceDemande) {
        this.totalAnnonceDemande = totalAnnonceDemande;
    }

    public int getTotalComptepremium() {
        return totalComptepremium;
    }

    public void setTotalComptepremium(int totalComptepremium) {
        this.totalComptepremium = totalComptepremium;
    }

    public int getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(int totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    @Override
    public String toString() {
        return (
            "ReportDTO{" +
            "dateCourante='" +
            dateCourante +
            '\'' +
            ", totalAnnonce=" +
            totalAnnonce +
            ", totalAnnonceDemande=" +
            totalAnnonceDemande +
            ", totalComptepremium=" +
            totalComptepremium +
            ", totalTransaction=" +
            totalTransaction +
            '}'
        );
    }
}
