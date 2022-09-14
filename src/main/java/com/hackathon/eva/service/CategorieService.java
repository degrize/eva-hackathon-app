package com.hackathon.eva.service;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.Categorie;
import com.hackathon.eva.service.dto.CategorieDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hackathon.eva.domain.Categorie}.
 */
public interface CategorieService {
    /**
     * Save a categorie.
     *
     * @param categorieDTO the entity to save.
     * @return the persisted entity.
     */
    CategorieDTO save(CategorieDTO categorieDTO);

    /**
     * Updates a categorie.
     *
     * @param categorieDTO the entity to update.
     * @return the persisted entity.
     */
    CategorieDTO update(CategorieDTO categorieDTO);

    /**
     * Partially updates a categorie.
     *
     * @param categorieDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategorieDTO> partialUpdate(CategorieDTO categorieDTO);

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategorieDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categorie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorieDTO> findOne(Long id);

    /**
     * Delete the "id" categorie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<Categorie> findAllNoPageble();
}
