package com.hackathon.eva.repository;

import com.hackathon.eva.domain.MandataireDelegateur;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MandataireDelegateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MandataireDelegateurRepository extends JpaRepository<MandataireDelegateur, Long> {
    @Query(
        "select mandataireDelegateur from MandataireDelegateur mandataireDelegateur left join fetch mandataireDelegateur.jhiUser where mandataireDelegateur.jhiUser.id =:id"
    )
    MandataireDelegateur findByJhiUserId(@Param("id") Long id);

    @Query(
        "SELECT COUNT(mandataireDelegateur) FROM MandataireDelegateur mandataireDelegateur where mandataireDelegateur.etatCompte = 'NORMAL'"
    )
    int getCountMandataireDelegateurNormal();

    @Query(
        "SELECT COUNT(mandataireDelegateur) FROM MandataireDelegateur mandataireDelegateur where mandataireDelegateur.etatCompte = 'PREMIUM'"
    )
    int getCountMandataireDelegateurPremium();

    @Query("select count(mandataireDelegateur) from MandataireDelegateur mandataireDelegateur")
    int getCountMandataireDelegateurPremiumPeriode(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(
        "select mandataireDelegateur FROM MandataireDelegateur mandataireDelegateur WHERE (mandataireDelegateur.nomDeFamille Like %:nomprenom%) or (mandataireDelegateur.prenom like %:nomprenom%)"
    )
    List<MandataireDelegateur> findMandataireDelegateurByLikeNomDeFamilleAndPrenom(@Param("nomprenom") String nomprenom);

    @Query(
        "select distinct mandataireDelegateur from MandataireDelegateur mandataireDelegateur left join fetch mandataireDelegateur.annonces where mandataireDelegateur.id =:id"
    )
    MandataireDelegateur findByMandateurId(@Param("id") Long id);
}
