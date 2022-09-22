package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Commentaire;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Commentaire entity.
 *
 * When extending this class, extend CommentaireRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface CommentaireRepository extends CommentaireRepositoryWithBagRelationships, JpaRepository<Commentaire, Long> {
    default Optional<Commentaire> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Commentaire> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Query("SELECT COUNT(commentaire) FROM Commentaire commentaire")
    int getCountCommentaire();

    @Query("select count(commentaire) from Commentaire commentaire")
    int getCountCommentaireParPeriode(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
