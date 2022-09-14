package com.hackathon.eva.web.rest;

import com.hackathon.eva.repository.AideRepository;
import com.hackathon.eva.service.AideService;
import com.hackathon.eva.service.MailService;
import com.hackathon.eva.service.dto.AideDTO;
import com.hackathon.eva.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link com.hackathon.eva.domain.Aide}.
 */
@RestController
@RequestMapping("/api")
public class AideResource {

    private final Logger log = LoggerFactory.getLogger(AideResource.class);

    private static final String ENTITY_NAME = "aide";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AideService aideService;

    private final AideRepository aideRepository;

    private final MailService mailService;

    public AideResource(AideService aideService, AideRepository aideRepository, MailService mailService) {
        this.aideService = aideService;
        this.aideRepository = aideRepository;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /aides} : Create a new aide.
     *
     * @param aideDTO the aideDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aideDTO, or with status {@code 400 (Bad Request)} if the aide has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aide")
    public ResponseEntity<AideDTO> createAide(@Valid @RequestBody AideDTO aideDTO) throws URISyntaxException {
        log.debug("REST request to save Aide : {}", aideDTO);
        if (aideDTO.getId() != null) {
            throw new BadRequestAlertException("A new aide cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AideDTO result = aideService.save(aideDTO);
        mailService.sendAideEmail(aideDTO);
        return ResponseEntity
            .created(new URI("/api/aides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
