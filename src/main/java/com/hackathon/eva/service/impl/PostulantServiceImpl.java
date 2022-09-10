package com.hackathon.eva.service.impl;

import com.hackathon.eva.domain.Postulant;
import com.hackathon.eva.repository.PostulantRepository;
import com.hackathon.eva.service.PostulantService;
import com.hackathon.eva.service.dto.PostulantDTO;
import com.hackathon.eva.service.mapper.PostulantMapper;
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
 * Service Implementation for managing {@link Postulant}.
 */
@Service
@Transactional
public class PostulantServiceImpl implements PostulantService {

    private final Logger log = LoggerFactory.getLogger(PostulantServiceImpl.class);

    private final PostulantRepository postulantRepository;

    private final PostulantMapper postulantMapper;

    public PostulantServiceImpl(PostulantRepository postulantRepository, PostulantMapper postulantMapper) {
        this.postulantRepository = postulantRepository;
        this.postulantMapper = postulantMapper;
    }

    @Override
    public PostulantDTO save(PostulantDTO postulantDTO) {
        log.debug("Request to save Postulant : {}", postulantDTO);
        Postulant postulant = postulantMapper.toEntity(postulantDTO);
        postulant = postulantRepository.save(postulant);
        return postulantMapper.toDto(postulant);
    }

    @Override
    public PostulantDTO update(PostulantDTO postulantDTO) {
        log.debug("Request to update Postulant : {}", postulantDTO);
        Postulant postulant = postulantMapper.toEntity(postulantDTO);
        postulant = postulantRepository.save(postulant);
        return postulantMapper.toDto(postulant);
    }

    @Override
    public Optional<PostulantDTO> partialUpdate(PostulantDTO postulantDTO) {
        log.debug("Request to partially update Postulant : {}", postulantDTO);

        return postulantRepository
            .findById(postulantDTO.getId())
            .map(existingPostulant -> {
                postulantMapper.partialUpdate(existingPostulant, postulantDTO);

                return existingPostulant;
            })
            .map(postulantRepository::save)
            .map(postulantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostulantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Postulants");
        return postulantRepository.findAll(pageable).map(postulantMapper::toDto);
    }

    public Page<PostulantDTO> findAllWithEagerRelationships(Pageable pageable) {
        return postulantRepository.findAllWithEagerRelationships(pageable).map(postulantMapper::toDto);
    }

    /**
     *  Get all the postulants where Transaction is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PostulantDTO> findAllWhereTransactionIsNull() {
        log.debug("Request to get all postulants where Transaction is null");
        return StreamSupport
            .stream(postulantRepository.findAll().spliterator(), false)
            .filter(postulant -> postulant.getTransaction() == null)
            .map(postulantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostulantDTO> findOne(Long id) {
        log.debug("Request to get Postulant : {}", id);
        return postulantRepository.findOneWithEagerRelationships(id).map(postulantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Postulant : {}", id);
        postulantRepository.deleteById(id);
    }
}
