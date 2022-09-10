package com.hackathon.eva.web.rest;

import com.hackathon.eva.repository.AnnonceRepository;
import com.hackathon.eva.service.AnnonceService;
import com.hackathon.eva.service.dto.AnnonceDTO;
import com.hackathon.eva.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.hackathon.eva.domain.Annonce}.
 */
@RestController
@RequestMapping("/api")
public class AnnonceResource {

    private final Logger log = LoggerFactory.getLogger(AnnonceResource.class);

    private static final String ENTITY_NAME = "annonce";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnnonceService annonceService;

    private final AnnonceRepository annonceRepository;

    public AnnonceResource(AnnonceService annonceService, AnnonceRepository annonceRepository) {
        this.annonceService = annonceService;
        this.annonceRepository = annonceRepository;
    }

    /**
     * {@code POST  /annonces} : Create a new annonce.
     *
     * @param annonceDTO the annonceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annonceDTO, or with status {@code 400 (Bad Request)} if the annonce has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/annonces")
    public ResponseEntity<AnnonceDTO> createAnnonce(@Valid @RequestBody AnnonceDTO annonceDTO) throws URISyntaxException {
        log.debug("REST request to save Annonce : {}", annonceDTO);
        if (annonceDTO.getId() != null) {
            throw new BadRequestAlertException("A new annonce cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnnonceDTO result = annonceService.save(annonceDTO);
        return ResponseEntity
            .created(new URI("/api/annonces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /annonces/:id} : Updates an existing annonce.
     *
     * @param id the id of the annonceDTO to save.
     * @param annonceDTO the annonceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annonceDTO,
     * or with status {@code 400 (Bad Request)} if the annonceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the annonceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/annonces/{id}")
    public ResponseEntity<AnnonceDTO> updateAnnonce(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnnonceDTO annonceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Annonce : {}, {}", id, annonceDTO);
        if (annonceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, annonceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!annonceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnnonceDTO result = annonceService.update(annonceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annonceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /annonces/:id} : Partial updates given fields of an existing annonce, field will ignore if it is null
     *
     * @param id the id of the annonceDTO to save.
     * @param annonceDTO the annonceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annonceDTO,
     * or with status {@code 400 (Bad Request)} if the annonceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the annonceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the annonceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/annonces/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnnonceDTO> partialUpdateAnnonce(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnnonceDTO annonceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Annonce partially : {}, {}", id, annonceDTO);
        if (annonceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, annonceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!annonceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnnonceDTO> result = annonceService.partialUpdate(annonceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, annonceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /annonces} : get all the annonces.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annonces in body.
     */
    @GetMapping("/annonces")
    public ResponseEntity<List<AnnonceDTO>> getAllAnnonces(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        if ("transaction-is-null".equals(filter)) {
            log.debug("REST request to get all Annonces where transaction is null");
            return new ResponseEntity<>(annonceService.findAllWhereTransactionIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Annonces");
        Page<AnnonceDTO> page;
        if (eagerload) {
            page = annonceService.findAllWithEagerRelationships(pageable);
        } else {
            page = annonceService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /annonces/:id} : get the "id" annonce.
     *
     * @param id the id of the annonceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annonceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/annonces/{id}")
    public ResponseEntity<AnnonceDTO> getAnnonce(@PathVariable Long id) {
        log.debug("REST request to get Annonce : {}", id);
        Optional<AnnonceDTO> annonceDTO = annonceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(annonceDTO);
    }

    /**
     * {@code DELETE  /annonces/:id} : delete the "id" annonce.
     *
     * @param id the id of the annonceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/annonces/{id}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        log.debug("REST request to delete Annonce : {}", id);
        annonceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
