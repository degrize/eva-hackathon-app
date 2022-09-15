package com.hackathon.eva.service.impl;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.repository.AnnonceRepository;
import com.hackathon.eva.service.AnnonceService;
import com.hackathon.eva.service.dto.AnnonceDTO;
import com.hackathon.eva.service.mapper.AnnonceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Annonce}.
 */
@Service
@Transactional
public class AnnonceServiceImpl implements AnnonceService {

    private final Logger log = LoggerFactory.getLogger(AnnonceServiceImpl.class);

    private final AnnonceRepository annonceRepository;

    private final AnnonceMapper annonceMapper;

    public AnnonceServiceImpl(AnnonceRepository annonceRepository, AnnonceMapper annonceMapper) {
        this.annonceRepository = annonceRepository;
        this.annonceMapper = annonceMapper;
    }

    @Override
    public AnnonceDTO save(AnnonceDTO annonceDTO) {
        log.debug("Request to save Annonce : {}", annonceDTO);
        Annonce annonce = annonceMapper.toEntity(annonceDTO);
        annonce = annonceRepository.save(annonce);
        return annonceMapper.toDto(annonce);
    }

    @Override
    public AnnonceDTO update(AnnonceDTO annonceDTO) {
        log.debug("Request to update Annonce : {}", annonceDTO);
        Annonce annonce = annonceMapper.toEntity(annonceDTO);
        annonce = annonceRepository.save(annonce);
        return annonceMapper.toDto(annonce);
    }

    @Override
    public Optional<AnnonceDTO> partialUpdate(AnnonceDTO annonceDTO) {
        log.debug("Request to partially update Annonce : {}", annonceDTO);

        return annonceRepository
            .findById(annonceDTO.getId())
            .map(existingAnnonce -> {
                annonceMapper.partialUpdate(existingAnnonce, annonceDTO);

                return existingAnnonce;
            })
            .map(annonceRepository::save)
            .map(annonceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnonceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Annonces");
        return annonceRepository.findAll(pageable).map(annonceMapper::toDto);
    }

    public Page<AnnonceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return annonceRepository.findAllWithEagerRelationships(pageable).map(annonceMapper::toDto);
    }

    /**
     *  Get all the annonces where Transaction is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnnonceDTO> findAllWhereTransactionIsNull() {
        log.debug("Request to get all annonces where Transaction is null");
        return StreamSupport
            .stream(annonceRepository.findAll().spliterator(), false)
            .filter(annonce -> annonce.getTransaction() == null)
            .map(annonceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnnonceDTO> findOne(Long id) {
        log.debug("Request to get Annonce : {}", id);
        return annonceRepository.findOneWithEagerRelationships(id).map(annonceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Annonce : {}", id);
        annonceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Annonce> findAllNoPageble() {
        log.debug("Request to get list of Annonces");
        return annonceRepository.findAllWithEagerRelationships();
    }
}
