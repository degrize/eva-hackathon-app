package com.hackathon.eva.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hackathon.eva.domain.Postulant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostulantDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeroMomo;

    private String observation;

    private Set<AnnonceDTO> annonces = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroMomo() {
        return numeroMomo;
    }

    public void setNumeroMomo(String numeroMomo) {
        this.numeroMomo = numeroMomo;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Set<AnnonceDTO> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(Set<AnnonceDTO> annonces) {
        this.annonces = annonces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostulantDTO)) {
            return false;
        }

        PostulantDTO postulantDTO = (PostulantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, postulantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostulantDTO{" +
            "id=" + getId() +
            ", numeroMomo='" + getNumeroMomo() + "'" +
            ", observation='" + getObservation() + "'" +
            ", annonces=" + getAnnonces() +
            "}";
    }
}
