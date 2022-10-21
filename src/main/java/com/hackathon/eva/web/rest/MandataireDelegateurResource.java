package com.hackathon.eva.web.rest;

import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.domain.User;
import com.hackathon.eva.repository.MandataireDelegateurRepository;
import com.hackathon.eva.repository.UserRepository;
import com.hackathon.eva.service.MandataireDelegateurService;
import com.hackathon.eva.service.UserService;
import com.hackathon.eva.service.dto.AdminUserDTO;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import com.hackathon.eva.service.dto.UserDTO;
import com.hackathon.eva.web.rest.errors.BadRequestAlertException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
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
 * REST controller for managing {@link com.hackathon.eva.domain.MandataireDelegateur}.
 */
@RestController
@RequestMapping("/api")
public class MandataireDelegateurResource {

    private final Logger log = LoggerFactory.getLogger(MandataireDelegateurResource.class);

    private static final String ENTITY_NAME = "mandataireDelegateur";

    @Autowired
    private UserService userService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MandataireDelegateurService mandataireDelegateurService;

    private final MandataireDelegateurRepository mandataireDelegateurRepository;

    public MandataireDelegateurResource(
        MandataireDelegateurService mandataireDelegateurService,
        MandataireDelegateurRepository mandataireDelegateurRepository
    ) {
        this.mandataireDelegateurService = mandataireDelegateurService;
        this.mandataireDelegateurRepository = mandataireDelegateurRepository;
    }

    /**
     * {@code POST  /mandataire-delegateurs} : Create a new mandataireDelegateur.
     *
     * @param mandataireDelegateurDTO the mandataireDelegateurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mandataireDelegateurDTO, or with status {@code 400 (Bad Request)} if the mandataireDelegateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mandataire-delegateurs")
    public ResponseEntity<MandataireDelegateurDTO> createMandataireDelegateur(
        @Valid @RequestBody MandataireDelegateurDTO mandataireDelegateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MandataireDelegateur : {}", mandataireDelegateurDTO);
        if (mandataireDelegateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new mandataireDelegateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MandataireDelegateurDTO result = mandataireDelegateurService.save(mandataireDelegateurDTO);
        return ResponseEntity
            .created(new URI("/api/mandataire-delegateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mandataire-delegateurs/:id} : Updates an existing mandataireDelegateur.
     *
     * @param id the id of the mandataireDelegateurDTO to save.
     * @param mandataireDelegateurDTO the mandataireDelegateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mandataireDelegateurDTO,
     * or with status {@code 400 (Bad Request)} if the mandataireDelegateurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mandataireDelegateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mandataire-delegateurs/{id}")
    public ResponseEntity<MandataireDelegateurDTO> updateMandataireDelegateur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MandataireDelegateurDTO mandataireDelegateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MandataireDelegateur : {}, {}", id, mandataireDelegateurDTO);
        if (mandataireDelegateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mandataireDelegateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mandataireDelegateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MandataireDelegateurDTO result = mandataireDelegateurService.update(mandataireDelegateurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mandataireDelegateurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mandataire-delegateurs/:id} : Partial updates given fields of an existing mandataireDelegateur, field will ignore if it is null
     *
     * @param id the id of the mandataireDelegateurDTO to save.
     * @param mandataireDelegateurDTO the mandataireDelegateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mandataireDelegateurDTO,
     * or with status {@code 400 (Bad Request)} if the mandataireDelegateurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mandataireDelegateurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mandataireDelegateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mandataire-delegateurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MandataireDelegateurDTO> partialUpdateMandataireDelegateur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MandataireDelegateurDTO mandataireDelegateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MandataireDelegateur partially : {}, {}", id, mandataireDelegateurDTO);
        if (mandataireDelegateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mandataireDelegateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mandataireDelegateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MandataireDelegateurDTO> result = mandataireDelegateurService.partialUpdate(mandataireDelegateurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mandataireDelegateurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mandataire-delegateurs} : get all the mandataireDelegateurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mandataireDelegateurs in body.
     */
    @GetMapping("/mandataire-delegateurs")
    public ResponseEntity<List<MandataireDelegateurDTO>> getAllMandataireDelegateurs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of MandataireDelegateurs");
        Page<MandataireDelegateurDTO> page = mandataireDelegateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mandataire-delegateurs/:id} : get the "id" mandataireDelegateur.
     *
     * @param id the id of the mandataireDelegateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mandataireDelegateurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mandataire-delegateurs/{id}")
    public ResponseEntity<MandataireDelegateurDTO> getMandataireDelegateur(@PathVariable Long id) {
        log.debug("REST request to get MandataireDelegateur : {}", id);
        Optional<MandataireDelegateurDTO> mandataireDelegateurDTO = mandataireDelegateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mandataireDelegateurDTO);
    }

    /**
     * {@code DELETE  /mandataire-delegateurs/:id} : delete the "id" mandataireDelegateur.
     *
     * @param id the id of the mandataireDelegateurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mandataire-delegateurs/{id}")
    public ResponseEntity<Void> deleteMandataireDelegateur(@PathVariable Long id) {
        log.debug("REST request to delete MandataireDelegateur : {}", id);
        mandataireDelegateurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping(value = "/mandataire-delegateurs/account", params = { "login" })
    public ResponseEntity<MandataireDelegateur> findUser(@RequestParam(value = "login") String login) {
        log.debug(" =========REST request to get a User : {}", login);

        User userCurrent = userService.findUser(login);
        MandataireDelegateur result = mandataireDelegateurService.findUser(userCurrent.getId());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/mandataire-delegateurs/search/{nomprenom}")
    public ResponseEntity<List<MandataireDelegateur>> getSearchMandataireDelegateurNoPageable(@PathVariable String nomprenom) {
        log.debug("REST request to get a page of MandataireDelegateur serach ");

        List<MandataireDelegateur> mandataireDelegateurs = mandataireDelegateurService.findAllByNomPrenom(nomprenom);
        return ResponseEntity.ok().body(mandataireDelegateurs);
    }

    @RequestMapping("/mandataire-delegateurs/excel/{id}")
    public ResponseEntity<InputStreamResource> viewExcel(@PathVariable long id)
        throws IOException, InvalidFormatException, DocumentException {
        String title = "transactions-eva";
        ByteArrayInputStream in = mandataireDelegateurService.generateExcelFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + title + ".xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }
}
