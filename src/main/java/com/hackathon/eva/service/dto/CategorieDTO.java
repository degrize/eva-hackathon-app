package com.hackathon.eva.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hackathon.eva.domain.Categorie} entity.
 */
@Schema(description = "Don entity.\n@author BEVE.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategorieDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieDTO)) {
            return false;
        }

        CategorieDTO categorieDTO = (CategorieDTO) o;
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
            ", description='" + getDescription() + "'" +
            "}";
    }
}
