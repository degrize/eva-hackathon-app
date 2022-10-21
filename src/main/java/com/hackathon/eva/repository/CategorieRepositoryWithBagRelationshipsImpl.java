package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.Categorie;
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
public class CategorieRepositoryWithBagRelationshipsImpl implements CategorieRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Categorie> fetchBagRelationships(List<Categorie> categories) {
        return Optional.of(categories).map(this::fetchCategories).orElse(Collections.emptyList());
    }

    Categorie fetchCategories(Categorie result) {
        return entityManager
            .createQuery(
                "select categorie from Categorie categorie left join fetch categorie.annonces where categorie is :categorie",
                Categorie.class
            )
            .setParameter("categorie", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Categorie> fetchCategories(List<Categorie> categories) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, categories.size()).forEach(index -> order.put(categories.get(index).getId(), index));
        List<Categorie> result = entityManager
            .createQuery(
                "select distinct categorie from Categorie categorie left join fetch categorie.annonces where (categorie in :categories)",
                Categorie.class
            )
            .setParameter("categories", categories)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
