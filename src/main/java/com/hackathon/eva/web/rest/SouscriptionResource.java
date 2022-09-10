package com.hackathon.eva.web.rest;

import com.hackathon.eva.repository.SouscriptionRepository;
import com.hackathon.eva.service.SouscriptionService;
import com.hackathon.eva.service.dto.SouscriptionDTO;
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
 * REST controller for managing {@link com.hackathon.eva.domain.Souscription}.
 */
@RestController
@RequestMapping("/api")
public class SouscriptionResource {

    private final Logger log = LoggerFactory.getLogger(SouscriptionResource.class);

    private static final String ENTITY_NAME = "souscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SouscriptionService souscriptionService;

    private final SouscriptionRepository souscriptionRepository;

    public SouscriptionResource(SouscriptionService souscriptionService, SouscriptionRepository souscriptionRepository) {
        this.souscriptionService = souscriptionService;
        this.souscriptionRepository = souscriptionRepository;
    }

    /**
     * {@code POST  /souscriptions} : Create a new souscription.
     *
     * @param souscriptionDTO the souscriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new souscriptionDTO, or with status {@code 400 (Bad Request)} if the souscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/souscriptions")
    public ResponseEntity<SouscriptionDTO> createSouscription(@Valid @RequestBody SouscriptionDTO souscriptionDTO)
        throws URISyntaxException {
        log.debug("REST request to save Souscription : {}", souscriptionDTO);
        if (souscriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new souscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SouscriptionDTO result = souscriptionService.save(souscriptionDTO);
        return ResponseEntity
            .created(new URI("/api/souscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /souscriptions/:id} : Updates an existing souscription.
     *
     * @param id the id of the souscriptionDTO to save.
     * @param souscriptionDTO the souscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated souscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the souscriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the souscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/souscriptions/{id}")
    public ResponseEntity<SouscriptionDTO> updateSouscription(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SouscriptionDTO souscriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Souscription : {}, {}", id, souscriptionDTO);
        if (souscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, souscriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!souscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SouscriptionDTO result = souscriptionService.update(souscriptionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, souscriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /souscriptions/:id} : Partial updates given fields of an existing souscription, field will ignore if it is null
     *
     * @param id the id of the souscriptionDTO to save.
     * @param souscriptionDTO the souscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated souscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the souscriptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the souscriptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the souscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/souscriptions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SouscriptionDTO> partialUpdateSouscription(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SouscriptionDTO souscriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Souscription partially : {}, {}", id, souscriptionDTO);
        if (souscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, souscriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!souscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SouscriptionDTO> result = souscriptionService.partialUpdate(souscriptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, souscriptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /souscriptions} : get all the souscriptions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of souscriptions in body.
     */
    @GetMapping("/souscriptions")
    public ResponseEntity<List<SouscriptionDTO>> getAllSouscriptions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Souscriptions");
        Page<SouscriptionDTO> page;
        if (eagerload) {
            page = souscriptionService.findAllWithEagerRelationships(pageable);
        } else {
            page = souscriptionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /souscriptions/:id} : get the "id" souscription.
     *
     * @param id the id of the souscriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the souscriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/souscriptions/{id}")
    public ResponseEntity<SouscriptionDTO> getSouscription(@PathVariable Long id) {
        log.debug("REST request to get Souscription : {}", id);
        Optional<SouscriptionDTO> souscriptionDTO = souscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(souscriptionDTO);
    }

    /**
     * {@code DELETE  /souscriptions/:id} : delete the "id" souscription.
     *
     * @param id the id of the souscriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/souscriptions/{id}")
    public ResponseEntity<Void> deleteSouscription(@PathVariable Long id) {
        log.debug("REST request to delete Souscription : {}", id);
        souscriptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
