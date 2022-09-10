package com.hackathon.eva.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hackathon.eva.domain.Annonce} entity.
 */
@Schema(description = "Donnateur entity.\n@author BEVE.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnnonceDTO implements Serializable {

    private Long id;

    @NotNull
    private String titre;

    private Instant dateDeDelegation;

    @NotNull
    private LocalDate dateDeDelais;

    @NotNull
    private String tarif;

    private Integer postulantRetenu;

    @Lob
    private byte[] imageVideo;

    private String imageVideoContentType;
    private String description;

    private Set<CategorieDTO> categories = new HashSet<>();

    private MandataireDelegateurDTO mandataireDelegateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Instant getDateDeDelegation() {
        return dateDeDelegation;
    }

    public void setDateDeDelegation(Instant dateDeDelegation) {
        this.dateDeDelegation = dateDeDelegation;
    }

    public LocalDate getDateDeDelais() {
        return dateDeDelais;
    }

    public void setDateDeDelais(LocalDate dateDeDelais) {
        this.dateDeDelais = dateDeDelais;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public Integer getPostulantRetenu() {
        return postulantRetenu;
    }

    public void setPostulantRetenu(Integer postulantRetenu) {
        this.postulantRetenu = postulantRetenu;
    }

    public byte[] getImageVideo() {
        return imageVideo;
    }

    public void setImageVideo(byte[] imageVideo) {
        this.imageVideo = imageVideo;
    }

    public String getImageVideoContentType() {
        return imageVideoContentType;
    }

    public void setImageVideoContentType(String imageVideoContentType) {
        this.imageVideoContentType = imageVideoContentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CategorieDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategorieDTO> categories) {
        this.categories = categories;
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
        if (!(o instanceof AnnonceDTO)) {
            return false;
        }

        AnnonceDTO annonceDTO = (AnnonceDTO) o;
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
        return "AnnonceDTO{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", dateDeDelegation='" + getDateDeDelegation() + "'" +
            ", dateDeDelais='" + getDateDeDelais() + "'" +
            ", tarif='" + getTarif() + "'" +
            ", postulantRetenu=" + getPostulantRetenu() +
            ", imageVideo='" + getImageVideo() + "'" +
            ", description='" + getDescription() + "'" +
            ", categories=" + getCategories() +
            ", mandataireDelegateur=" + getMandataireDelegateur() +
            "}";
    }
}
