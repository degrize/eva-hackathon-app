package com.hackathon.eva.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Postulant.
 */
@Entity
@Table(name = "postulant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Postulant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "numero_momo", nullable = false)
    private String numeroMomo;

    @Column(name = "observation")
    private String observation;

    @ManyToMany
    @JoinTable(
        name = "rel_postulant__annonce",
        joinColumns = @JoinColumn(name = "postulant_id"),
        inverseJoinColumns = @JoinColumn(name = "annonce_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "messages", "categories", "transaction", "mandataireDelegateur", "postulants" }, allowSetters = true)
    private Set<Annonce> annonces = new HashSet<>();

    @JsonIgnoreProperties(value = { "annonce", "postulant" }, allowSetters = true)
    @OneToOne(mappedBy = "postulant")
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Postulant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroMomo() {
        return this.numeroMomo;
    }

    public Postulant numeroMomo(String numeroMomo) {
        this.setNumeroMomo(numeroMomo);
        return this;
    }

    public void setNumeroMomo(String numeroMomo) {
        this.numeroMomo = numeroMomo;
    }

    public String getObservation() {
        return this.observation;
    }

    public Postulant observation(String observation) {
        this.setObservation(observation);
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Set<Annonce> getAnnonces() {
        return this.annonces;
    }

    public void setAnnonces(Set<Annonce> annonces) {
        this.annonces = annonces;
    }

    public Postulant annonces(Set<Annonce> annonces) {
        this.setAnnonces(annonces);
        return this;
    }

    public Postulant addAnnonce(Annonce annonce) {
        this.annonces.add(annonce);
        annonce.getPostulants().add(this);
        return this;
    }

    public Postulant removeAnnonce(Annonce annonce) {
        this.annonces.remove(annonce);
        annonce.getPostulants().remove(this);
        return this;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public void setTransaction(Transaction transaction) {
        if (this.transaction != null) {
            this.transaction.setPostulant(null);
        }
        if (transaction != null) {
            transaction.setPostulant(this);
        }
        this.transaction = transaction;
    }

    public Postulant transaction(Transaction transaction) {
        this.setTransaction(transaction);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Postulant)) {
            return false;
        }
        return id != null && id.equals(((Postulant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Postulant{" +
            "id=" + getId() +
            ", numeroMomo='" + getNumeroMomo() + "'" +
            ", observation='" + getObservation() + "'" +
            "}";
    }
}
