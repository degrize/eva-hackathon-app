package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Postulant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Postulant entity.
 *
 * When extending this class, extend PostulantRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PostulantRepository extends PostulantRepositoryWithBagRelationships, JpaRepository<Postulant, Long> {
    default Optional<Postulant> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Postulant> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Postulant> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    @Query("SELECT COUNT(postulant) FROM Postulant postulant")
    int getCountPostulant();

    @Query("select count(postulant) from Postulant postulant")
    int getCountPostulantParPeriode(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
