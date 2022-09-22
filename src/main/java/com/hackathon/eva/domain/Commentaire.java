package com.hackathon.eva.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Donnateur entity.\n@author BEVE.
 */
@Entity
@Table(name = "commentaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Commentaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "message")
    private String message;

    @Column(name = "email")
    private String email;

    @Column(name = "nom_commentateur")
    private String nomCommentateur;

    @Column(name = "date_de_message")
    private String dateDeMessage;

    @ManyToOne
    @JsonIgnoreProperties(value = { "commentaires", "souscriptions" }, allowSetters = true)
    private MandataireDelegateur mandataireDelegateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Commentaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public Commentaire message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateDeMessage() {
        return dateDeMessage;
    }

    public Commentaire dateDeMessage(String dateDeMessage) {
        this.setDateDeMessage(dateDeMessage);
        return this;
    }

    public void setDateDeMessage(String dateDeMessage) {
        this.dateDeMessage = dateDeMessage;
    }

    public MandataireDelegateur getMandataireDelegateur() {
        return this.mandataireDelegateur;
    }

    public String getEmail() {
        return email;
    }

    public Commentaire email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomCommentateur() {
        return nomCommentateur;
    }

    public Commentaire nomCommentateur(String nomCommentateur) {
        this.setNomCommentateur(nomCommentateur);
        return this;
    }

    public void setNomCommentateur(String nomCommentateur) {
        this.nomCommentateur = nomCommentateur;
    }

    public void setMandataireDelegateur(MandataireDelegateur mandataireDelegateur) {
        this.mandataireDelegateur = mandataireDelegateur;
    }

    public Commentaire mandataireDelegateur(MandataireDelegateur mandataireDelegateur) {
        this.setMandataireDelegateur(mandataireDelegateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commentaire)) {
            return false;
        }
        return id != null && id.equals(((Commentaire) o).id);
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
            ", message='" + getMessage() + "'" +
            ", dateDeMessage='" + getDateDeMessage() + "'" +
            ", email='" + getEmail() + "'" +
            ", nomCommentateur='" + getNomCommentateur() + "'" +
            "}";
    }
}
