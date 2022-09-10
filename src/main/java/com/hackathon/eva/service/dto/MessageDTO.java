package com.hackathon.eva.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.hackathon.eva.domain.Message} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MessageDTO implements Serializable {

    private Long id;

    private String texte;

    @Lob
    private byte[] fichierJoin;

    private String fichierJoinContentType;
    private AnnonceDTO annonce;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public byte[] getFichierJoin() {
        return fichierJoin;
    }

    public void setFichierJoin(byte[] fichierJoin) {
        this.fichierJoin = fichierJoin;
    }

    public String getFichierJoinContentType() {
        return fichierJoinContentType;
    }

    public void setFichierJoinContentType(String fichierJoinContentType) {
        this.fichierJoinContentType = fichierJoinContentType;
    }

    public AnnonceDTO getAnnonce() {
        return annonce;
    }

    public void setAnnonce(AnnonceDTO annonce) {
        this.annonce = annonce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageDTO)) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, messageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", texte='" + getTexte() + "'" +
            ", fichierJoin='" + getFichierJoin() + "'" +
            ", annonce=" + getAnnonce() +
            "}";
    }
}
