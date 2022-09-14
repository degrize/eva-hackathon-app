package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Aide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aide entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AideRepository extends JpaRepository<Aide, Long> {}
