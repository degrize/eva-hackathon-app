package com.hackathon.eva.repository;

import com.hackathon.eva.domain.MandataireDelegateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MandataireDelegateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MandataireDelegateurRepository extends JpaRepository<MandataireDelegateur, Long> {}
