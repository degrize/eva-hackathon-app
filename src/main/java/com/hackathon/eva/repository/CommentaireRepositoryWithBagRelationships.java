package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Commentaire;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CommentaireRepositoryWithBagRelationships {
    Optional<Commentaire> fetchBagRelationships(Optional<Commentaire> commentaire);

    List<Commentaire> fetchBagRelationships(List<Commentaire> commentaires);

    Page<Commentaire> fetchBagRelationships(Page<Commentaire> commentaires);
}
