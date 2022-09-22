package com.hackathon.eva.web.rest;

import com.hackathon.eva.domain.Commentaire;
import com.hackathon.eva.repository.CommentaireRepository;
import com.hackathon.eva.service.CommentaireService;
import com.hackathon.eva.service.dto.CommentaireDTO;
import com.hackathon.eva.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Commentaire}.
 */
@RestController
@RequestMapping("/api")
public class CommetaireResource {

    private final Logger log = LoggerFactory.getLogger(CommetaireResource.class);

    private static final String ENTITY_NAME = "commentaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentaireService commentaireService;

    private final CommentaireRepository commentaireRepository;

    public CommetaireResource(CommentaireService commentaireService, CommentaireRepository commentaireRepository) {
        this.commentaireService = commentaireService;
        this.commentaireRepository = commentaireRepository;
    }

    /**
     * {@code POST  /commentaires} : Create a new commentaire.
     *
     * @param commentaireDTO the commentaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentaireDTO, or with status {@code 400 (Bad Request)} if the commentaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commentaires")
    public ResponseEntity<CommentaireDTO> createCommentaire(@Valid @RequestBody CommentaireDTO commentaireDTO) throws URISyntaxException {
        log.debug("REST request to save Commentaire : {}", commentaireDTO);
        if (commentaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new commentaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentaireDTO result = commentaireService.save(commentaireDTO);
        return ResponseEntity
            .created(new URI("/api/commentaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commentaires/:id} : Updates an existing commentaire.
     *
     * @param id the id of the commentaireDTO to save.
     * @param commentaireDTO the commentaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentaireDTO,
     * or with status {@code 400 (Bad Request)} if the commentaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commentaires/{id}")
    public ResponseEntity<CommentaireDTO> updateCommentaire(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CommentaireDTO commentaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Commentaire : {}, {}", id, commentaireDTO);
        if (commentaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommentaireDTO result = commentaireService.update(commentaireDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /commentaires/:id} : Partial updates given fields of an existing commentaire, field will ignore if it is null
     *
     * @param id the id of the commentaireDTO to save.
     * @param commentaireDTO the commentaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentaireDTO,
     * or with status {@code 400 (Bad Request)} if the commentaireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commentaireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commentaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/commentaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommentaireDTO> partialUpdateCommentaire(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CommentaireDTO commentaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Commentaire partially : {}, {}", id, commentaireDTO);
        if (commentaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommentaireDTO> result = commentaireService.partialUpdate(commentaireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentaireDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /commentaires/:id} : get the "id" commentaire.
     *
     * @param id the id of the commentaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commentaires/{id}")
    public ResponseEntity<CommentaireDTO> getCommentaire(@PathVariable Long id) {
        log.debug("REST request to get Commentaire : {}", id);
        Optional<CommentaireDTO> commentaireDTO = commentaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentaireDTO);
    }

    /**
     * {@code DELETE  /commentaires/:id} : delete the "id" commentaire.
     *
     * @param id the id of the commentaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commentaires/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Long id) {
        log.debug("REST request to delete Commentaire : {}", id);
        commentaireService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/commentaires/liste")
    public ResponseEntity<List<Commentaire>> getAllCommentairesNoPageble() {
        log.debug("REST request to get list of Commentaires");
        List<Commentaire> commentaireList = commentaireService.findAllNoPageble();
        return ResponseEntity.ok().body(commentaireList);
    }

    @GetMapping("/commentaires/search-list")
    public ResponseEntity<List<Commentaire>> getAllCommentairesSearchNoPageble() {
        log.debug("REST request to get list of search Commentaires");
        List<Commentaire> commentaireSearchList = commentaireService.findAllNoPagebleSearch();
        return ResponseEntity.ok().body(commentaireSearchList);
    }
}
