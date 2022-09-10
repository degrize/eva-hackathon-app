package com.hackathon.eva.service.impl;

import com.hackathon.eva.domain.Souscription;
import com.hackathon.eva.repository.SouscriptionRepository;
import com.hackathon.eva.service.SouscriptionService;
import com.hackathon.eva.service.dto.SouscriptionDTO;
import com.hackathon.eva.service.mapper.SouscriptionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Souscription}.
 */
@Service
@Transactional
public class SouscriptionServiceImpl implements SouscriptionService {

    private final Logger log = LoggerFactory.getLogger(SouscriptionServiceImpl.class);

    private final SouscriptionRepository souscriptionRepository;

    private final SouscriptionMapper souscriptionMapper;

    public SouscriptionServiceImpl(SouscriptionRepository souscriptionRepository, SouscriptionMapper souscriptionMapper) {
        this.souscriptionRepository = souscriptionRepository;
        this.souscriptionMapper = souscriptionMapper;
    }

    @Override
    public SouscriptionDTO save(SouscriptionDTO souscriptionDTO) {
        log.debug("Request to save Souscription : {}", souscriptionDTO);
        Souscription souscription = souscriptionMapper.toEntity(souscriptionDTO);
        souscription = souscriptionRepository.save(souscription);
        return souscriptionMapper.toDto(souscription);
    }

    @Override
    public SouscriptionDTO update(SouscriptionDTO souscriptionDTO) {
        log.debug("Request to update Souscription : {}", souscriptionDTO);
        Souscription souscription = souscriptionMapper.toEntity(souscriptionDTO);
        souscription = souscriptionRepository.save(souscription);
        return souscriptionMapper.toDto(souscription);
    }

    @Override
    public Optional<SouscriptionDTO> partialUpdate(SouscriptionDTO souscriptionDTO) {
        log.debug("Request to partially update Souscription : {}", souscriptionDTO);

        return souscriptionRepository
            .findById(souscriptionDTO.getId())
            .map(existingSouscription -> {
                souscriptionMapper.partialUpdate(existingSouscription, souscriptionDTO);

                return existingSouscription;
            })
            .map(souscriptionRepository::save)
            .map(souscriptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SouscriptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Souscriptions");
        return souscriptionRepository.findAll(pageable).map(souscriptionMapper::toDto);
    }

    public Page<SouscriptionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return souscriptionRepository.findAllWithEagerRelationships(pageable).map(souscriptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SouscriptionDTO> findOne(Long id) {
        log.debug("Request to get Souscription : {}", id);
        return souscriptionRepository.findOneWithEagerRelationships(id).map(souscriptionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Souscription : {}", id);
        souscriptionRepository.deleteById(id);
    }
}
