package com.hackathon.eva.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.hackathon.eva.domain.Commentaire} entity.
 */
@Schema(description = "Commenatire entity.\n@author EVA.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentaireDTO implements Serializable {

    private Long id;

    @NotNull
    private String message;

    private String dateDeMessage;

    private String email;

    private String nomCommentateur;

    private MandataireDelegateurDTO mandataireDelegateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateDeMessage() {
        return dateDeMessage;
    }

    public void setDateDeMessage(String dateDeMessage) {
        this.dateDeMessage = dateDeMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomCommentateur() {
        return nomCommentateur;
    }

    public void setNomCommentateur(String nomCommentateur) {
        this.nomCommentateur = nomCommentateur;
    }

    public MandataireDelegateurDTO getMandataireDelegateur() {
        return mandataireDelegateur;
    }

    public void setMandataireDelegateur(MandataireDelegateurDTO mandataireDelegateur) {
        this.mandataireDelegateur = mandataireDelegateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentaireDTO)) {
            return false;
        }

        CommentaireDTO annonceDTO = (CommentaireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, annonceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentaireDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", dateDeMessage=" + getDateDeMessage() +
            ", mandataireDelegateur=" + getMandataireDelegateur() +
            ", email=" + getEmail() +
            ", nomCommentateur=" + getNomCommentateur() +
            "}";
    }
}
