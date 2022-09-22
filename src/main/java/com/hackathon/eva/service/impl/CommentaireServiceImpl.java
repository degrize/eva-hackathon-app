package com.hackathon.eva.service.impl;

import com.hackathon.eva.domain.Commentaire;
import com.hackathon.eva.repository.CommentaireRepository;
import com.hackathon.eva.service.CommentaireService;
import com.hackathon.eva.service.dto.CommentaireDTO;
import com.hackathon.eva.service.mapper.CommentaireMapper;
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
 * Service Implementation for managing {@link Commentaire}.
 */
@Service
@Transactional
public class CommentaireServiceImpl implements CommentaireService {

    private final Logger log = LoggerFactory.getLogger(CommentaireServiceImpl.class);

    private final CommentaireRepository commentaireRepository;

    private final CommentaireMapper commentaireMapper;

    public CommentaireServiceImpl(CommentaireRepository commentaireRepository, CommentaireMapper commentaireMapper) {
        this.commentaireRepository = commentaireRepository;
        this.commentaireMapper = commentaireMapper;
    }

    @Override
    public CommentaireDTO save(CommentaireDTO commentaireDTO) {
        log.debug("Request to save Commentaire : {}", commentaireDTO);
        Commentaire commentaire = commentaireMapper.toEntity(commentaireDTO);
        commentaire = commentaireRepository.save(commentaire);
        return commentaireMapper.toDto(commentaire);
    }

    @Override
    public CommentaireDTO update(CommentaireDTO commentaireDTO) {
        log.debug("Request to update Commentaire : {}", commentaireDTO);
        Commentaire commentaire = commentaireMapper.toEntity(commentaireDTO);
        commentaire = commentaireRepository.save(commentaire);
        return commentaireMapper.toDto(commentaire);
    }

    @Override
    public Optional<CommentaireDTO> partialUpdate(CommentaireDTO commentaireDTO) {
        log.debug("Request to partially update Commentaire : {}", commentaireDTO);

        return commentaireRepository
            .findById(commentaireDTO.getId())
            .map(existingCommentaire -> {
                commentaireMapper.partialUpdate(existingCommentaire, commentaireDTO);

                return existingCommentaire;
            })
            .map(commentaireRepository::save)
            .map(commentaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentaireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Commentaires");
        return commentaireRepository.findAll(pageable).map(commentaireMapper::toDto);
    }

    /**
     *  Get all the commentaires where Transaction is {@code null}.
     *  @return the list of entities.
     */

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentaireDTO> findOne(Long id) {
        log.debug("Request to get Commentaire : {}", id);
        return commentaireRepository.findOneWithEagerRelationships(id).map(commentaireMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commentaire : {}", id);
        commentaireRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commentaire> findAllNoPageble() {
        log.debug("Request to get list of Commentaires");
        return commentaireRepository.findAllWithEagerRelationships();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commentaire> findAllNoPagebleSearch() {
        log.debug("Request to get list of Commentaires Search");
        List<Commentaire> commentaireList = commentaireRepository.findAllWithEagerRelationships();
        return commentaireList;
    }
}
