package com.hackathon.eva.service.dto;

import com.hackathon.eva.domain.enumeration.Devise;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hackathon.eva.domain.Transaction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeroMtn;

    @NotNull
    private Double montant;

    @NotNull
    private Devise devise;

    private Instant dateTransaction;

    private String precision;

    private Long transmeteurId;

    private Long receiverId;

    private Long annonceTransactionId;

    private AnnonceDTO annonce;

    private PostulantDTO postulant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroMtn() {
        return numeroMtn;
    }

    public void setNumeroMtn(String numeroMtn) {
        this.numeroMtn = numeroMtn;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Instant getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Instant dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public Long getTransmeteurId() {
        return transmeteurId;
    }

    public void setTransmeteurId(Long transmeteurId) {
        this.transmeteurId = transmeteurId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getAnnonceTransactionId() {
        return annonceTransactionId;
    }

    public void setAnnonceTransactionId(Long annonceTransactionId) {
        this.annonceTransactionId = annonceTransactionId;
    }

    public AnnonceDTO getAnnonce() {
        return annonce;
    }

    public void setAnnonce(AnnonceDTO annonce) {
        this.annonce = annonce;
    }

    public PostulantDTO getPostulant() {
        return postulant;
    }

    public void setPostulant(PostulantDTO postulant) {
        this.postulant = postulant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", numeroMtn='" + getNumeroMtn() + "'" +
            ", montant=" + getMontant() +
            ", devise='" + getDevise() + "'" +
            ", dateTransaction='" + getDateTransaction() + "'" +
            ", precision='" + getPrecision() + "'" +
            ", annonce=" + getAnnonce() +
            ", postulant=" + getPostulant() +
            ", transmeteurId='" + getTransmeteurId() + "'" +
            ", receiverId='" + getReceiverId() + "'" +
            ", annonceTransactionId='" + getAnnonceTransactionId() + "'" +
            "}";
    }
}
