package com.hackathon.eva.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_transmeteur")
    private String nomTransmeteur;

    @Column(name = "date_envoie")
    private String dateEnvoie;

    @Column(name = "texte")
    private String texte;

    @Lob
    @Column(name = "fichier_join")
    private byte[] fichierJoin;

    @Column(name = "fichier_join_content_type")
    private String fichierJoinContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "messages", "categories", "transaction", "mandataireDelegateur", "postulants" }, allowSetters = true)
    private Annonce annonce;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Message id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexte() {
        return this.texte;
    }

    public Message texte(String texte) {
        this.setTexte(texte);
        return this;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getNomTransmeteur() {
        return nomTransmeteur;
    }

    public Message nomTransmeteur(String nomTransmeteur) {
        this.setNomTransmeteur(nomTransmeteur);
        return this;
    }

    public void setNomTransmeteur(String nomTransmeteur) {
        this.nomTransmeteur = nomTransmeteur;
    }

    public String getDateEnvoie() {
        return dateEnvoie;
    }

    public Message dateEnvoie(String dateEnvoie) {
        this.setDateEnvoie(dateEnvoie);
        return this;
    }

    public void setDateEnvoie(String dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    public byte[] getFichierJoin() {
        return this.fichierJoin;
    }

    public Message fichierJoin(byte[] fichierJoin) {
        this.setFichierJoin(fichierJoin);
        return this;
    }

    public void setFichierJoin(byte[] fichierJoin) {
        this.fichierJoin = fichierJoin;
    }

    public String getFichierJoinContentType() {
        return this.fichierJoinContentType;
    }

    public Message fichierJoinContentType(String fichierJoinContentType) {
        this.fichierJoinContentType = fichierJoinContentType;
        return this;
    }

    public void setFichierJoinContentType(String fichierJoinContentType) {
        this.fichierJoinContentType = fichierJoinContentType;
    }

    public Annonce getAnnonce() {
        return this.annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public Message annonce(Annonce annonce) {
        this.setAnnonce(annonce);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", texte='" + getTexte() + "'" +
            ", dateEnvoie='" + getDateEnvoie() + "'" +
            ", nomTransmeteur='" + getNomTransmeteur() + "'" +
            ", fichierJoin='" + getFichierJoin() + "'" +
            ", fichierJoinContentType='" + getFichierJoinContentType() + "'" +
            "}";
    }
}
