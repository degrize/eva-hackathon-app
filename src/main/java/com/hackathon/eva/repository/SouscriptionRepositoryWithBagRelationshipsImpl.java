package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Souscription;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SouscriptionRepositoryWithBagRelationshipsImpl implements SouscriptionRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Souscription> fetchBagRelationships(Optional<Souscription> souscription) {
        return souscription.map(this::fetchMandataireDelegateurs);
    }

    @Override
    public Page<Souscription> fetchBagRelationships(Page<Souscription> souscriptions) {
        return new PageImpl<>(
            fetchBagRelationships(souscriptions.getContent()),
            souscriptions.getPageable(),
            souscriptions.getTotalElements()
        );
    }

    @Override
    public List<Souscription> fetchBagRelationships(List<Souscription> souscriptions) {
        return Optional.of(souscriptions).map(this::fetchMandataireDelegateurs).orElse(Collections.emptyList());
    }

    Souscription fetchMandataireDelegateurs(Souscription result) {
        return entityManager
            .createQuery(
                "select souscription from Souscription souscription left join fetch souscription.mandataireDelegateurs where souscription is :souscription",
                Souscription.class
            )
            .setParameter("souscription", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Souscription> fetchMandataireDelegateurs(List<Souscription> souscriptions) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, souscriptions.size()).forEach(index -> order.put(souscriptions.get(index).getId(), index));
        List<Souscription> result = entityManager
            .createQuery(
                "select distinct souscription from Souscription souscription left join fetch souscription.mandataireDelegateurs where souscription in :souscriptions",
                Souscription.class
            )
            .setParameter("souscriptions", souscriptions)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
