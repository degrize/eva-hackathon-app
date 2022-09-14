package com.hackathon.eva.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.hackathon.eva.domain.Aide} entity.
 */
@Schema(description = "Aide entity.\n@author EVA.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AideDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String message;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AideDTO)) {
            return false;
        }

        AideDTO categorieDTO = (AideDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categorieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", message='" + getMessage() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
