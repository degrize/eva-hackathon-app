package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Annonce;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AnnonceRepositoryWithBagRelationships {
    Optional<Annonce> fetchBagRelationships(Optional<Annonce> annonce);

    List<Annonce> fetchBagRelationships(List<Annonce> annonces);

    Page<Annonce> fetchBagRelationships(Page<Annonce> annonces);
}
