package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Postulant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PostulantRepositoryWithBagRelationships {
    Optional<Postulant> fetchBagRelationships(Optional<Postulant> postulant);

    List<Postulant> fetchBagRelationships(List<Postulant> postulants);

    Page<Postulant> fetchBagRelationships(Page<Postulant> postulants);
}
