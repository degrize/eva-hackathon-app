package com.hackathon.eva.service;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.Postulant;
import com.hackathon.eva.service.dto.PostulantDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hackathon.eva.domain.Postulant}.
 */
public interface PostulantService {
    /**
     * Save a postulant.
     *
     * @param postulantDTO the entity to save.
     * @return the persisted entity.
     */
    PostulantDTO save(PostulantDTO postulantDTO);

    /**
     * Updates a postulant.
     *
     * @param postulantDTO the entity to update.
     * @return the persisted entity.
     */
    PostulantDTO update(PostulantDTO postulantDTO);

    /**
     * Partially updates a postulant.
     *
     * @param postulantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostulantDTO> partialUpdate(PostulantDTO postulantDTO);

    /**
     * Get all the postulants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostulantDTO> findAll(Pageable pageable);
    /**
     * Get all the PostulantDTO where Transaction is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PostulantDTO> findAllWhereTransactionIsNull();

    /**
     * Get all the postulants with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostulantDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" postulant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostulantDTO> findOne(Long id);

    /**
     * Delete the "id" postulant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<Postulant> findAllNoPageble();
}
