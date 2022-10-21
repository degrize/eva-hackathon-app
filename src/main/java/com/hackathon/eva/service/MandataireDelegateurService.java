package com.hackathon.eva.service;

import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.DocumentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.hackathon.eva.domain.MandataireDelegateur}.
 */
public interface MandataireDelegateurService {
    /**
     * Save a mandataireDelegateur.
     *
     * @param mandataireDelegateurDTO the entity to save.
     * @return the persisted entity.
     */
    MandataireDelegateurDTO save(MandataireDelegateurDTO mandataireDelegateurDTO);

    /**
     * Updates a mandataireDelegateur.
     *
     * @param mandataireDelegateurDTO the entity to update.
     * @return the persisted entity.
     */
    MandataireDelegateurDTO update(MandataireDelegateurDTO mandataireDelegateurDTO);

    /**
     * Partially updates a mandataireDelegateur.
     *
     * @param mandataireDelegateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MandataireDelegateurDTO> partialUpdate(MandataireDelegateurDTO mandataireDelegateurDTO);

    /**
     * Get all the mandataireDelegateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MandataireDelegateurDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mandataireDelegateur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MandataireDelegateurDTO> findOne(Long id);

    /**
     * Delete the "id" mandataireDelegateur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    MandataireDelegateur findUser(Long id);

    List<MandataireDelegateur> findAllByNomPrenom(String nomprenom);

    ByteArrayInputStream generateExcelFile(long id) throws IOException, InvalidFormatException, DocumentException;
}
