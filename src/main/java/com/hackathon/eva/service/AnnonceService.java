package com.hackathon.eva.service;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.service.dto.AnnonceDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hackathon.eva.domain.Annonce}.
 */
public interface AnnonceService {
    /**
     * Save a annonce.
     *
     * @param annonceDTO the entity to save.
     * @return the persisted entity.
     */
    AnnonceDTO save(AnnonceDTO annonceDTO);

    /**
     * Updates a annonce.
     *
     * @param annonceDTO the entity to update.
     * @return the persisted entity.
     */
    AnnonceDTO update(AnnonceDTO annonceDTO);

    /**
     * Partially updates a annonce.
     *
     * @param annonceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnnonceDTO> partialUpdate(AnnonceDTO annonceDTO);

    /**
     * Get all the annonces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnnonceDTO> findAll(Pageable pageable);
    /**
     * Get all the AnnonceDTO where Transaction is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<AnnonceDTO> findAllWhereTransactionIsNull();

    /**
     * Get all the annonces with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnnonceDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" annonce.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnnonceDTO> findOne(Long id);

    /**
     * Delete the "id" annonce.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<Annonce> findAllNoPageble();
}
