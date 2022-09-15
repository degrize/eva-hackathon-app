package com.hackathon.eva.web.rest;

import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.domain.Postulant;
import com.hackathon.eva.repository.PostulantRepository;
import com.hackathon.eva.service.PostulantService;
import com.hackathon.eva.service.dto.PostulantDTO;
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
 * REST controller for managing {@link com.hackathon.eva.domain.Postulant}.
 */
@RestController
@RequestMapping("/api")
public class PostulantResource {

    private final Logger log = LoggerFactory.getLogger(PostulantResource.class);

    private static final String ENTITY_NAME = "postulant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostulantService postulantService;

    private final PostulantRepository postulantRepository;

    public PostulantResource(PostulantService postulantService, PostulantRepository postulantRepository) {
        this.postulantService = postulantService;
        this.postulantRepository = postulantRepository;
    }

    /**
     * {@code POST  /postulants} : Create a new postulant.
     *
     * @param postulantDTO the postulantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postulantDTO, or with status {@code 400 (Bad Request)} if the postulant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/postulants")
    public ResponseEntity<PostulantDTO> createPostulant(@Valid @RequestBody PostulantDTO postulantDTO) throws URISyntaxException {
        log.debug("REST request to save Postulant : {}", postulantDTO);
        if (postulantDTO.getId() != null) {
            throw new BadRequestAlertException("A new postulant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostulantDTO result = postulantService.save(postulantDTO);
        return ResponseEntity
            .created(new URI("/api/postulants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /postulants/:id} : Updates an existing postulant.
     *
     * @param id the id of the postulantDTO to save.
     * @param postulantDTO the postulantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postulantDTO,
     * or with status {@code 400 (Bad Request)} if the postulantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postulantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/postulants/{id}")
    public ResponseEntity<PostulantDTO> updatePostulant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PostulantDTO postulantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Postulant : {}, {}", id, postulantDTO);
        if (postulantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postulantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postulantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PostulantDTO result = postulantService.update(postulantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postulantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /postulants/:id} : Partial updates given fields of an existing postulant, field will ignore if it is null
     *
     * @param id the id of the postulantDTO to save.
     * @param postulantDTO the postulantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postulantDTO,
     * or with status {@code 400 (Bad Request)} if the postulantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the postulantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the postulantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/postulants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PostulantDTO> partialUpdatePostulant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PostulantDTO postulantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Postulant partially : {}, {}", id, postulantDTO);
        if (postulantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postulantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postulantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PostulantDTO> result = postulantService.partialUpdate(postulantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postulantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /postulants} : get all the postulants.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postulants in body.
     */
    @GetMapping("/postulants")
    public ResponseEntity<List<PostulantDTO>> getAllPostulants(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        if ("transaction-is-null".equals(filter)) {
            log.debug("REST request to get all Postulants where transaction is null");
            return new ResponseEntity<>(postulantService.findAllWhereTransactionIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Postulants");
        Page<PostulantDTO> page;
        if (eagerload) {
            page = postulantService.findAllWithEagerRelationships(pageable);
        } else {
            page = postulantService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /postulants/:id} : get the "id" postulant.
     *
     * @param id the id of the postulantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postulantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/postulants/{id}")
    public ResponseEntity<PostulantDTO> getPostulant(@PathVariable Long id) {
        log.debug("REST request to get Postulant : {}", id);
        Optional<PostulantDTO> postulantDTO = postulantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postulantDTO);
    }

    /**
     * {@code DELETE  /postulants/:id} : delete the "id" postulant.
     *
     * @param id the id of the postulantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/postulants/{id}")
    public ResponseEntity<Void> deletePostulant(@PathVariable Long id) {
        log.debug("REST request to delete Postulant : {}", id);
        postulantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/postulants/liste")
    public ResponseEntity<List<Postulant>> getAllPostulantNoPageble() {
        log.debug("REST request to get list of postulants");
        List<Postulant> postulantList = postulantService.findAllNoPageble();
        return ResponseEntity.ok().body(postulantList);
    }
}
