package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Annonce;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Annonce entity.
 *
 * When extending this class, extend AnnonceRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface AnnonceRepository extends AnnonceRepositoryWithBagRelationships, JpaRepository<Annonce, Long> {
    default Optional<Annonce> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Annonce> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Annonce> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    @Query("SELECT COUNT(annonce) FROM Annonce annonce")
    int getCountAnnonce();

    @Query("select count(annonce) from Annonce annonce")
    int getCountAnnonceParPeriode(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
