package com.hackathon.eva.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Donnateur entity.\n@author BEVE.
 */
@Entity
@Table(name = "annonce")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Annonce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "date_de_delegation")
    private Instant dateDeDelegation;

    @NotNull
    @Column(name = "date_de_delais", nullable = false)
    private LocalDate dateDeDelais;

    @NotNull
    @Column(name = "tarif", nullable = false)
    private String tarif;

    @Column(name = "postulant_retenu")
    private Integer postulantRetenu;

    @Lob
    @Column(name = "image_video")
    private byte[] imageVideo;

    @Column(name = "image_video_content_type")
    private String imageVideoContentType;

    @Column(name = "description")
    private String description;

    @Column(name = "duree")
    private String duree;

    @Column(name = "categorie_search")
    private String categorieSearch;

    @OneToMany(mappedBy = "annonce")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annonce" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_annonce__categorie",
        joinColumns = @JoinColumn(name = "annonce_id"),
        inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annonces" }, allowSetters = true)
    private Set<Categorie> categories = new HashSet<>();

    @JsonIgnoreProperties(value = { "annonce", "postulant" }, allowSetters = true)
    @OneToOne(mappedBy = "annonce")
    private Transaction transaction;

    @ManyToOne
    @JsonIgnoreProperties(value = { "annonces", "souscriptions" }, allowSetters = true)
    private MandataireDelegateur mandataireDelegateur;

    @ManyToMany(mappedBy = "annonces")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annonces", "transaction" }, allowSetters = true)
    private Set<Postulant> postulants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Annonce id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public Annonce titre(String titre) {
        this.setTitre(titre);
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Instant getDateDeDelegation() {
        return this.dateDeDelegation;
    }

    public Annonce dateDeDelegation(Instant dateDeDelegation) {
        this.setDateDeDelegation(dateDeDelegation);
        return this;
    }

    public void setDateDeDelegation(Instant dateDeDelegation) {
        this.dateDeDelegation = dateDeDelegation;
    }

    public LocalDate getDateDeDelais() {
        return this.dateDeDelais;
    }

    public Annonce dateDeDelais(LocalDate dateDeDelais) {
        this.setDateDeDelais(dateDeDelais);
        return this;
    }

    public void setDateDeDelais(LocalDate dateDeDelais) {
        this.dateDeDelais = dateDeDelais;
    }

    public String getTarif() {
        return this.tarif;
    }

    public Annonce tarif(String tarif) {
        this.setTarif(tarif);
        return this;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public Integer getPostulantRetenu() {
        return this.postulantRetenu;
    }

    public Annonce postulantRetenu(Integer postulantRetenu) {
        this.setPostulantRetenu(postulantRetenu);
        return this;
    }

    public void setPostulantRetenu(Integer postulantRetenu) {
        this.postulantRetenu = postulantRetenu;
    }

    public byte[] getImageVideo() {
        return this.imageVideo;
    }

    public Annonce imageVideo(byte[] imageVideo) {
        this.setImageVideo(imageVideo);
        return this;
    }

    public void setImageVideo(byte[] imageVideo) {
        this.imageVideo = imageVideo;
    }

    public String getImageVideoContentType() {
        return this.imageVideoContentType;
    }

    public Annonce imageVideoContentType(String imageVideoContentType) {
        this.imageVideoContentType = imageVideoContentType;
        return this;
    }

    public void setImageVideoContentType(String imageVideoContentType) {
        this.imageVideoContentType = imageVideoContentType;
    }

    public String getDescription() {
        return this.description;
    }

    public Annonce description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return duree;
    }

    public Annonce duree(String duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getCategorieSearch() {
        return categorieSearch;
    }

    public Annonce categorieSearch(String categorieSearch) {
        this.setCategorieSearch(categorieSearch);
        return this;
    }

    public void setCategorieSearch(String categorieSearch) {
        this.categorieSearch = categorieSearch;
    }

    public Set<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<Message> messages) {
        if (this.messages != null) {
            this.messages.forEach(i -> i.setAnnonce(null));
        }
        if (messages != null) {
            messages.forEach(i -> i.setAnnonce(this));
        }
        this.messages = messages;
    }

    public Annonce messages(Set<Message> messages) {
        this.setMessages(messages);
        return this;
    }

    public Annonce addMessage(Message message) {
        this.messages.add(message);
        message.setAnnonce(this);
        return this;
    }

    public Annonce removeMessage(Message message) {
        this.messages.remove(message);
        message.setAnnonce(null);
        return this;
    }

    public Set<Categorie> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public Annonce categories(Set<Categorie> categories) {
        this.setCategories(categories);
        return this;
    }

    public Annonce addCategorie(Categorie categorie) {
        this.categories.add(categorie);
        categorie.getAnnonces().add(this);
        return this;
    }

    public Annonce removeCategorie(Categorie categorie) {
        this.categories.remove(categorie);
        categorie.getAnnonces().remove(this);
        return this;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public void setTransaction(Transaction transaction) {
        if (this.transaction != null) {
            this.transaction.setAnnonce(null);
        }
        if (transaction != null) {
            transaction.setAnnonce(this);
        }
        this.transaction = transaction;
    }

    public Annonce transaction(Transaction transaction) {
        this.setTransaction(transaction);
        return this;
    }

    public MandataireDelegateur getMandataireDelegateur() {
        return this.mandataireDelegateur;
    }

    public void setMandataireDelegateur(MandataireDelegateur mandataireDelegateur) {
        this.mandataireDelegateur = mandataireDelegateur;
    }

    public Annonce mandataireDelegateur(MandataireDelegateur mandataireDelegateur) {
        this.setMandataireDelegateur(mandataireDelegateur);
        return this;
    }

    public Set<Postulant> getPostulants() {
        return this.postulants;
    }

    public void setPostulants(Set<Postulant> postulants) {
        if (this.postulants != null) {
            this.postulants.forEach(i -> i.removeAnnonce(this));
        }
        if (postulants != null) {
            postulants.forEach(i -> i.addAnnonce(this));
        }
        this.postulants = postulants;
    }

    public Annonce postulants(Set<Postulant> postulants) {
        this.setPostulants(postulants);
        return this;
    }

    public Annonce addPostulant(Postulant postulant) {
        this.postulants.add(postulant);
        postulant.getAnnonces().add(this);
        return this;
    }

    public Annonce removePostulant(Postulant postulant) {
        this.postulants.remove(postulant);
        postulant.getAnnonces().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Annonce)) {
            return false;
        }
        return id != null && id.equals(((Annonce) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Annonce{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", dateDeDelegation='" + getDateDeDelegation() + "'" +
            ", dateDeDelais='" + getDateDeDelais() + "'" +
            ", tarif='" + getTarif() + "'" +
            ", postulantRetenu=" + getPostulantRetenu() +
            ", imageVideo='" + getImageVideo() + "'" +
            ", imageVideoContentType='" + getImageVideoContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", duree='" + getDuree() + "'" +
            ", categorieSearch='" + getCategorieSearch() + "'" +
            "}";
    }
}
