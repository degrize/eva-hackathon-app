package com.hackathon.eva.web.rest;

import com.hackathon.eva.service.AdminStatistiqueService;
import com.hackathon.eva.service.dto.AdminStatisticsDTO;
import java.net.URISyntaxException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hackathon.eva.domain.Aide}.
 */
@RestController
@RequestMapping("/api")
public class AdminStatistiqueResource {

    private final Logger log = LoggerFactory.getLogger(AdminStatistiqueResource.class);

    private static final String ENTITY_NAME = "statistque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminStatistiqueService adminStatistiqueService;

    public AdminStatistiqueResource(AdminStatistiqueService adminStatistiqueService) {
        this.adminStatistiqueService = adminStatistiqueService;
    }

    @GetMapping("/statistques")
    public ResponseEntity<AdminStatisticsDTO> makeStatistique() throws URISyntaxException {
        log.debug("REST request to make AdminStatistics");

        Optional<AdminStatisticsDTO> adminStatisticsDTO = adminStatistiqueService.makeStatistique();
        return ResponseUtil.wrapOrNotFound(adminStatisticsDTO);
    }
}
