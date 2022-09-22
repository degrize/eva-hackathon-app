package com.hackathon.eva.service;

import com.hackathon.eva.domain.Commentaire;
import com.hackathon.eva.service.dto.CommentaireDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Commentaire}.
 */
public interface CommentaireService {
    /**
     * Save a commentaire.
     *
     * @param commentaireDTO the entity to save.
     * @return the persisted entity.
     */
    CommentaireDTO save(CommentaireDTO commentaireDTO);

    /**
     * Updates a commentaire.
     *
     * @param commentaireDTO the entity to update.
     * @return the persisted entity.
     */
    CommentaireDTO update(CommentaireDTO commentaireDTO);

    /**
     * Partially updates a commentaire.
     *
     * @param commentaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommentaireDTO> partialUpdate(CommentaireDTO commentaireDTO);

    /**
     * Get all the commentaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommentaireDTO> findAll(Pageable pageable);
    /**
     * Get all the CommentaireDTO where Transaction is {@code null}.
     *
     * @return the {@link List} of entities.
     */

    Optional<CommentaireDTO> findOne(Long id);

    /**
     * Delete the "id" commentaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<Commentaire> findAllNoPageble();
    List<Commentaire> findAllNoPagebleSearch();
}
