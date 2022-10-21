package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.Categorie;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Categorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieRepository extends CategorieRepositoryWithBagRelationships, JpaRepository<Categorie, Long> {
    default List<Categorie> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }
}
