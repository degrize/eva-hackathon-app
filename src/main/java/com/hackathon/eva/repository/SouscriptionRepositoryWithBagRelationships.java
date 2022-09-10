package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Souscription;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SouscriptionRepositoryWithBagRelationships {
    Optional<Souscription> fetchBagRelationships(Optional<Souscription> souscription);

    List<Souscription> fetchBagRelationships(List<Souscription> souscriptions);

    Page<Souscription> fetchBagRelationships(Page<Souscription> souscriptions);
}
