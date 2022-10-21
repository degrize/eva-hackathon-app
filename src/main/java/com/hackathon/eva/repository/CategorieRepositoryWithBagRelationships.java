package com.hackathon.eva.repository;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.Categorie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CategorieRepositoryWithBagRelationships {
    List<Categorie> fetchBagRelationships(List<Categorie> categories);
}
