package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Annonce;
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
public class AnnonceRepositoryWithBagRelationshipsImpl implements AnnonceRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Annonce> fetchBagRelationships(Optional<Annonce> annonce) {
        return annonce.map(this::fetchCategories);
    }

    @Override
    public Page<Annonce> fetchBagRelationships(Page<Annonce> annonces) {
        return new PageImpl<>(fetchBagRelationships(annonces.getContent()), annonces.getPageable(), annonces.getTotalElements());
    }

    @Override
    public List<Annonce> fetchBagRelationships(List<Annonce> annonces) {
        return Optional.of(annonces).map(this::fetchCategories).orElse(Collections.emptyList());
    }

    Annonce fetchCategories(Annonce result) {
        return entityManager
            .createQuery("select annonce from Annonce annonce left join fetch annonce.categories where annonce is :annonce", Annonce.class)
            .setParameter("annonce", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Annonce> fetchCategories(List<Annonce> annonces) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, annonces.size()).forEach(index -> order.put(annonces.get(index).getId(), index));
        List<Annonce> result = entityManager
            .createQuery(
                "select distinct annonce from Annonce annonce left join fetch annonce.categories where annonce in :annonces",
                Annonce.class
            )
            .setParameter("annonces", annonces)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
