package com.hackathon.eva.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hackathon.eva.domain.enumeration.EtatCompte;
import com.hackathon.eva.domain.enumeration.Sexe;
import com.hackathon.eva.domain.enumeration.SituationMatrimoniale;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "mandataire_delegateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MandataireDelegateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom_de_famille", nullable = false)
    private String nomDeFamille;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "numero_momo", nullable = false)
    private String numeroMomo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @Column(name = "pays")
    private String pays;

    @Column(name = "ville")
    private String ville;

    @Column(name = "adresse")
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_compte")
    private EtatCompte etatCompte;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation_matrimoniale")
    private SituationMatrimoniale situationMatrimoniale;

    @OneToMany(mappedBy = "mandataireDelegateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "messages", "categories", "transaction", "mandataireDelegateur", "postulants" }, allowSetters = true)
    private Set<Annonce> annonces = new HashSet<>();

    @ManyToMany(mappedBy = "mandataireDelegateurs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mandataireDelegateurs" }, allowSetters = true)
    private Set<Souscription> souscriptions = new HashSet<>();

    // Ajout de mes propres champs

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private User jhiUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MandataireDelegateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDeFamille() {
        return this.nomDeFamille;
    }

    public MandataireDelegateur nomDeFamille(String nomDeFamille) {
        this.setNomDeFamille(nomDeFamille);
        return this;
    }

    public void setNomDeFamille(String nomDeFamille) {
        this.nomDeFamille = nomDeFamille;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public MandataireDelegateur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getContact() {
        return this.contact;
    }

    public MandataireDelegateur contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return this.email;
    }

    public MandataireDelegateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroMomo() {
        return this.numeroMomo;
    }

    public MandataireDelegateur numeroMomo(String numeroMomo) {
        this.setNumeroMomo(numeroMomo);
        return this;
    }

    public void setNumeroMomo(String numeroMomo) {
        this.numeroMomo = numeroMomo;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public MandataireDelegateur sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getPays() {
        return this.pays;
    }

    public MandataireDelegateur pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return this.ville;
    }

    public MandataireDelegateur ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public MandataireDelegateur adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public EtatCompte getEtatCompte() {
        return this.etatCompte;
    }

    public MandataireDelegateur etatCompte(EtatCompte etatCompte) {
        this.setEtatCompte(etatCompte);
        return this;
    }

    public void setEtatCompte(EtatCompte etatCompte) {
        this.etatCompte = etatCompte;
    }

    public SituationMatrimoniale getSituationMatrimoniale() {
        return this.situationMatrimoniale;
    }

    public MandataireDelegateur situationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.setSituationMatrimoniale(situationMatrimoniale);
        return this;
    }

    public void setSituationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.situationMatrimoniale = situationMatrimoniale;
    }

    public Set<Annonce> getAnnonces() {
        return this.annonces;
    }

    public void setAnnonces(Set<Annonce> annonces) {
        if (this.annonces != null) {
            this.annonces.forEach(i -> i.setMandataireDelegateur(null));
        }
        if (annonces != null) {
            annonces.forEach(i -> i.setMandataireDelegateur(this));
        }
        this.annonces = annonces;
    }

    public MandataireDelegateur annonces(Set<Annonce> annonces) {
        this.setAnnonces(annonces);
        return this;
    }

    public MandataireDelegateur addAnnonce(Annonce annonce) {
        this.annonces.add(annonce);
        annonce.setMandataireDelegateur(this);
        return this;
    }

    public MandataireDelegateur removeAnnonce(Annonce annonce) {
        this.annonces.remove(annonce);
        annonce.setMandataireDelegateur(null);
        return this;
    }

    public Set<Souscription> getSouscriptions() {
        return this.souscriptions;
    }

    public void setSouscriptions(Set<Souscription> souscriptions) {
        if (this.souscriptions != null) {
            this.souscriptions.forEach(i -> i.removeMandataireDelegateur(this));
        }
        if (souscriptions != null) {
            souscriptions.forEach(i -> i.addMandataireDelegateur(this));
        }
        this.souscriptions = souscriptions;
    }

    public MandataireDelegateur souscriptions(Set<Souscription> souscriptions) {
        this.setSouscriptions(souscriptions);
        return this;
    }

    public MandataireDelegateur addSouscription(Souscription souscription) {
        this.souscriptions.add(souscription);
        souscription.getMandataireDelegateurs().add(this);
        return this;
    }

    public MandataireDelegateur removeSouscription(Souscription souscription) {
        this.souscriptions.remove(souscription);
        souscription.getMandataireDelegateurs().remove(this);
        return this;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public MandataireDelegateur photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public MandataireDelegateur photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public User getUser() {
        return this.jhiUser;
    }

    public void setUser(User user) {
        this.jhiUser = user;
    }

    public MandataireDelegateur jhiUser(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MandataireDelegateur)) {
            return false;
        }
        return id != null && id.equals(((MandataireDelegateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MandataireDelegateur{" +
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
            "}";
    }
}
