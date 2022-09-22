package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Commentaire;
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
public class CommentaireRepositoryWithBagRelationshipsImpl implements CommentaireRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Commentaire> fetchBagRelationships(Page<Commentaire> commentaires) {
        return new PageImpl<>(
            fetchBagRelationships(commentaires.getContent()),
            commentaires.getPageable(),
            commentaires.getTotalElements()
        );
    }

    @Override
    public Optional<Commentaire> fetchBagRelationships(Optional<Commentaire> commentaire) {
        return Optional.empty();
    }

    @Override
    public List<Commentaire> fetchBagRelationships(List<Commentaire> commentaires) {
        return Optional.of(commentaires).map(this::fetchCategories).orElse(Collections.emptyList());
    }

    Commentaire fetchCategories(Commentaire result) {
        return entityManager
            .createQuery("select commentaire from Commentaire commentaire", Commentaire.class)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Commentaire> fetchCategories(List<Commentaire> commentaires) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, commentaires.size()).forEach(index -> order.put(commentaires.get(index).getId(), index));
        List<Commentaire> result = entityManager
            .createQuery("select distinct commentaire from Commentaire commentaire", Commentaire.class)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
