package com.hackathon.eva.service.impl;

import com.hackathon.eva.domain.Aide;
import com.hackathon.eva.repository.AideRepository;
import com.hackathon.eva.service.AideService;
import com.hackathon.eva.service.dto.AideDTO;
import com.hackathon.eva.service.mapper.AideMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Aide}.
 */
@Service
@Transactional
public class AideServiceImpl implements AideService {

    private final Logger log = LoggerFactory.getLogger(AideServiceImpl.class);

    private final AideRepository aideRepository;

    private final AideMapper aideMapper;

    public AideServiceImpl(AideRepository aideRepository, AideMapper aideMapper) {
        this.aideRepository = aideRepository;
        this.aideMapper = aideMapper;
    }

    @Override
    public AideDTO save(AideDTO aideDTO) {
        log.debug("Request to save Aide : {}", aideDTO);
        Aide aide = aideMapper.toEntity(aideDTO);
        aide = aideRepository.save(aide);
        return aideMapper.toDto(aide);
    }
}
