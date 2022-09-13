package com.hackathon.eva.repository;

import com.hackathon.eva.domain.MandataireDelegateur;
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
}
