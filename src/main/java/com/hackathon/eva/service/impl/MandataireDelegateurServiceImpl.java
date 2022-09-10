package com.hackathon.eva.service.impl;

import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.repository.MandataireDelegateurRepository;
import com.hackathon.eva.service.MandataireDelegateurService;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import com.hackathon.eva.service.mapper.MandataireDelegateurMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MandataireDelegateur}.
 */
@Service
@Transactional
public class MandataireDelegateurServiceImpl implements MandataireDelegateurService {

    private final Logger log = LoggerFactory.getLogger(MandataireDelegateurServiceImpl.class);

    private final MandataireDelegateurRepository mandataireDelegateurRepository;

    private final MandataireDelegateurMapper mandataireDelegateurMapper;

    public MandataireDelegateurServiceImpl(
        MandataireDelegateurRepository mandataireDelegateurRepository,
        MandataireDelegateurMapper mandataireDelegateurMapper
    ) {
        this.mandataireDelegateurRepository = mandataireDelegateurRepository;
        this.mandataireDelegateurMapper = mandataireDelegateurMapper;
    }

    @Override
    public MandataireDelegateurDTO save(MandataireDelegateurDTO mandataireDelegateurDTO) {
        log.debug("Request to save MandataireDelegateur : {}", mandataireDelegateurDTO);
        MandataireDelegateur mandataireDelegateur = mandataireDelegateurMapper.toEntity(mandataireDelegateurDTO);
        mandataireDelegateur = mandataireDelegateurRepository.save(mandataireDelegateur);
        return mandataireDelegateurMapper.toDto(mandataireDelegateur);
    }

    @Override
    public MandataireDelegateurDTO update(MandataireDelegateurDTO mandataireDelegateurDTO) {
        log.debug("Request to update MandataireDelegateur : {}", mandataireDelegateurDTO);
        MandataireDelegateur mandataireDelegateur = mandataireDelegateurMapper.toEntity(mandataireDelegateurDTO);
        mandataireDelegateur = mandataireDelegateurRepository.save(mandataireDelegateur);
        return mandataireDelegateurMapper.toDto(mandataireDelegateur);
    }

    @Override
    public Optional<MandataireDelegateurDTO> partialUpdate(MandataireDelegateurDTO mandataireDelegateurDTO) {
        log.debug("Request to partially update MandataireDelegateur : {}", mandataireDelegateurDTO);

        return mandataireDelegateurRepository
            .findById(mandataireDelegateurDTO.getId())
            .map(existingMandataireDelegateur -> {
                mandataireDelegateurMapper.partialUpdate(existingMandataireDelegateur, mandataireDelegateurDTO);

                return existingMandataireDelegateur;
            })
            .map(mandataireDelegateurRepository::save)
            .map(mandataireDelegateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MandataireDelegateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MandataireDelegateurs");
        return mandataireDelegateurRepository.findAll(pageable).map(mandataireDelegateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MandataireDelegateurDTO> findOne(Long id) {
        log.debug("Request to get MandataireDelegateur : {}", id);
        return mandataireDelegateurRepository.findById(id).map(mandataireDelegateurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MandataireDelegateur : {}", id);
        mandataireDelegateurRepository.deleteById(id);
    }
}
