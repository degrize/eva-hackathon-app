package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Postulant;
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
public class PostulantRepositoryWithBagRelationshipsImpl implements PostulantRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Postulant> fetchBagRelationships(Optional<Postulant> postulant) {
        return postulant.map(this::fetchAnnonces);
    }

    @Override
    public Page<Postulant> fetchBagRelationships(Page<Postulant> postulants) {
        return new PageImpl<>(fetchBagRelationships(postulants.getContent()), postulants.getPageable(), postulants.getTotalElements());
    }

    @Override
    public List<Postulant> fetchBagRelationships(List<Postulant> postulants) {
        return Optional.of(postulants).map(this::fetchAnnonces).orElse(Collections.emptyList());
    }

    Postulant fetchAnnonces(Postulant result) {
        return entityManager
            .createQuery(
                "select postulant from Postulant postulant left join fetch postulant.annonces where postulant is :postulant",
                Postulant.class
            )
            .setParameter("postulant", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Postulant> fetchAnnonces(List<Postulant> postulants) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, postulants.size()).forEach(index -> order.put(postulants.get(index).getId(), index));
        List<Postulant> result = entityManager
            .createQuery(
                "select distinct postulant from Postulant postulant left join fetch postulant.annonces where postulant in :postulants",
                Postulant.class
            )
            .setParameter("postulants", postulants)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
