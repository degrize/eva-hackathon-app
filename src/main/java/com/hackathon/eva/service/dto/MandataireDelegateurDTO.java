package com.hackathon.eva.service.dto;

import com.hackathon.eva.domain.User;
import com.hackathon.eva.domain.enumeration.EtatCompte;
import com.hackathon.eva.domain.enumeration.Sexe;
import com.hackathon.eva.domain.enumeration.SituationMatrimoniale;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.hackathon.eva.domain.MandataireDelegateur} entity.
 */
@Schema(description = "not an ignored comment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MandataireDelegateurDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomDeFamille;

    @NotNull
    private String prenom;

    @NotNull
    private String contact;

    @NotNull
    private String email;

    @NotNull
    private String numeroMomo;

    @NotNull
    private Sexe sexe;

    private String pays;

    private String ville;

    private String adresse;

    private EtatCompte etatCompte;

    private SituationMatrimoniale situationMatrimoniale;

    @Lob
    private byte[] photo;

    private String photoContentType;

    private UserDTO jhiUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDeFamille() {
        return nomDeFamille;
    }

    public void setNomDeFamille(String nomDeFamille) {
        this.nomDeFamille = nomDeFamille;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroMomo() {
        return numeroMomo;
    }

    public void setNumeroMomo(String numeroMomo) {
        this.numeroMomo = numeroMomo;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public EtatCompte getEtatCompte() {
        return etatCompte;
    }

    public void setEtatCompte(EtatCompte etatCompte) {
        this.etatCompte = etatCompte;
    }

    public SituationMatrimoniale getSituationMatrimoniale() {
        return situationMatrimoniale;
    }

    public void setSituationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.situationMatrimoniale = situationMatrimoniale;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public UserDTO getUser() {
        return jhiUser;
    }

    public void setUser(UserDTO user) {
        this.jhiUser = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MandataireDelegateurDTO)) {
            return false;
        }

        MandataireDelegateurDTO mandataireDelegateurDTO = (MandataireDelegateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mandataireDelegateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MandataireDelegateurDTO{" +
            "id=" + getId() +
            ", nomDeFamille='" + getNomDeFamille() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            ", numeroMomo='" + getNumeroMomo() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", pays='" + getPays() + "'" +
            ", ville='" + getVille() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", etatCompte='" + getEtatCompte() + "'" +
            ", situationMatrimoniale='" + getSituationMatrimoniale() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", mandataireDelegateur=" + getUser() +
            "}";
    }
}
