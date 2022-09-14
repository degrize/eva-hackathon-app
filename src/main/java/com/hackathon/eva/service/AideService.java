package com.hackathon.eva.service;

import com.hackathon.eva.service.dto.AideDTO;

/**
 * Service Interface for managing {@link com.hackathon.eva.domain.Aide}.
 */
public interface AideService {
    /**
     * Save a aide.
     *
     * @param aideDTO the entity to save.
     * @return the persisted entity.
     */
    AideDTO save(AideDTO aideDTO);
}
