package com.hackathon.eva.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hackathon.eva.IntegrationTest;
import com.hackathon.eva.domain.Annonce;
import com.hackathon.eva.repository.AnnonceRepository;
import com.hackathon.eva.service.AnnonceService;
import com.hackathon.eva.service.dto.AnnonceDTO;
import com.hackathon.eva.service.mapper.AnnonceMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AnnonceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnnonceResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DE_DELEGATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DE_DELEGATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_DATE_DE_DELAIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_DELAIS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TARIF = "AAAAAAAAAA";
    private static final String UPDATED_TARIF = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSTULANT_RETENU = 1;
    private static final Integer UPDATED_POSTULANT_RETENU = 2;

    private static final byte[] DEFAULT_IMAGE_VIDEO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_VIDEO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_VIDEO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_VIDEO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/annonces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnnonceRepository annonceRepository;

    @Mock
    private AnnonceRepository annonceRepositoryMock;

    @Autowired
    private AnnonceMapper annonceMapper;

    @Mock
    private AnnonceService annonceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnnonceMockMvc;

    private Annonce annonce;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Annonce createEntity(EntityManager em) {
        Annonce annonce = new Annonce()
            .titre(DEFAULT_TITRE)
            .dateDeDelegation(DEFAULT_DATE_DE_DELEGATION)
            .dateDeDelais(DEFAULT_DATE_DE_DELAIS)
            .tarif(DEFAULT_TARIF)
            .postulantRetenu(DEFAULT_POSTULANT_RETENU)
            .imageVideo(DEFAULT_IMAGE_VIDEO)
            .imageVideoContentType(DEFAULT_IMAGE_VIDEO_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return annonce;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Annonce createUpdatedEntity(EntityManager em) {
        Annonce annonce = new Annonce()
            .titre(UPDATED_TITRE)
            .dateDeDelegation(UPDATED_DATE_DE_DELEGATION)
            .dateDeDelais(UPDATED_DATE_DE_DELAIS)
            .tarif(UPDATED_TARIF)
            .postulantRetenu(UPDATED_POSTULANT_RETENU)
            .imageVideo(UPDATED_IMAGE_VIDEO)
            .imageVideoContentType(UPDATED_IMAGE_VIDEO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        return annonce;
    }

    @BeforeEach
    public void initTest() {
        annonce = createEntity(em);
    }

    @Test
    @Transactional
    void createAnnonce() throws Exception {
        int databaseSizeBeforeCreate = annonceRepository.findAll().size();
        // Create the Annonce
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);
        restAnnonceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annonceDTO)))
            .andExpect(status().isCreated());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeCreate + 1);
        Annonce testAnnonce = annonceList.get(annonceList.size() - 1);
        assertThat(testAnnonce.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testAnnonce.getDateDeDelegation()).isEqualTo(DEFAULT_DATE_DE_DELEGATION);
        assertThat(testAnnonce.getDateDeDelais()).isEqualTo(DEFAULT_DATE_DE_DELAIS);
        assertThat(testAnnonce.getTarif()).isEqualTo(DEFAULT_TARIF);
        assertThat(testAnnonce.getPostulantRetenu()).isEqualTo(DEFAULT_POSTULANT_RETENU);
        assertThat(testAnnonce.getImageVideo()).isEqualTo(DEFAULT_IMAGE_VIDEO);
        assertThat(testAnnonce.getImageVideoContentType()).isEqualTo(DEFAULT_IMAGE_VIDEO_CONTENT_TYPE);
        assertThat(testAnnonce.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createAnnonceWithExistingId() throws Exception {
        // Create the Annonce with an existing ID
        annonce.setId(1L);
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        int databaseSizeBeforeCreate = annonceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnonceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annonceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitreIsRequired() throws Exception {
        int databaseSizeBeforeTest = annonceRepository.findAll().size();
        // set the field null
        annonce.setTitre(null);

        // Create the Annonce, which fails.
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        restAnnonceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annonceDTO)))
            .andExpect(status().isBadRequest());

        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateDeDelaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = annonceRepository.findAll().size();
        // set the field null
        annonce.setDateDeDelais(null);

        // Create the Annonce, which fails.
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        restAnnonceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annonceDTO)))
            .andExpect(status().isBadRequest());

        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTarifIsRequired() throws Exception {
        int databaseSizeBeforeTest = annonceRepository.findAll().size();
        // set the field null
        annonce.setTarif(null);

        // Create the Annonce, which fails.
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        restAnnonceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annonceDTO)))
            .andExpect(status().isBadRequest());

        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnnonces() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);

        // Get all the annonceList
        restAnnonceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annonce.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].dateDeDelegation").value(hasItem(DEFAULT_DATE_DE_DELEGATION.toString())))
            .andExpect(jsonPath("$.[*].dateDeDelais").value(hasItem(DEFAULT_DATE_DE_DELAIS.toString())))
            .andExpect(jsonPath("$.[*].tarif").value(hasItem(DEFAULT_TARIF)))
            .andExpect(jsonPath("$.[*].postulantRetenu").value(hasItem(DEFAULT_POSTULANT_RETENU)))
            .andExpect(jsonPath("$.[*].imageVideoContentType").value(hasItem(DEFAULT_IMAGE_VIDEO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageVideo").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_VIDEO))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnnoncesWithEagerRelationshipsIsEnabled() throws Exception {
        when(annonceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnnonceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(annonceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnnoncesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(annonceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnnonceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(annonceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnnonce() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);

        // Get the annonce
        restAnnonceMockMvc
            .perform(get(ENTITY_API_URL_ID, annonce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(annonce.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.dateDeDelegation").value(DEFAULT_DATE_DE_DELEGATION.toString()))
            .andExpect(jsonPath("$.dateDeDelais").value(DEFAULT_DATE_DE_DELAIS.toString()))
            .andExpect(jsonPath("$.tarif").value(DEFAULT_TARIF))
            .andExpect(jsonPath("$.postulantRetenu").value(DEFAULT_POSTULANT_RETENU))
            .andExpect(jsonPath("$.imageVideoContentType").value(DEFAULT_IMAGE_VIDEO_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageVideo").value(Base64Utils.encodeToString(DEFAULT_IMAGE_VIDEO)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingAnnonce() throws Exception {
        // Get the annonce
        restAnnonceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnnonce() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);

        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();

        // Update the annonce
        Annonce updatedAnnonce = annonceRepository.findById(annonce.getId()).get();
        // Disconnect from session so that the updates on updatedAnnonce are not directly saved in db
        em.detach(updatedAnnonce);
        updatedAnnonce
            .titre(UPDATED_TITRE)
            .dateDeDelegation(UPDATED_DATE_DE_DELEGATION)
            .dateDeDelais(UPDATED_DATE_DE_DELAIS)
            .tarif(UPDATED_TARIF)
            .postulantRetenu(UPDATED_POSTULANT_RETENU)
            .imageVideo(UPDATED_IMAGE_VIDEO)
            .imageVideoContentType(UPDATED_IMAGE_VIDEO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        AnnonceDTO annonceDTO = annonceMapper.toDto(updatedAnnonce);

        restAnnonceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, annonceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(annonceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
        Annonce testAnnonce = annonceList.get(annonceList.size() - 1);
        assertThat(testAnnonce.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testAnnonce.getDateDeDelegation()).isEqualTo(UPDATED_DATE_DE_DELEGATION);
        assertThat(testAnnonce.getDateDeDelais()).isEqualTo(UPDATED_DATE_DE_DELAIS);
        assertThat(testAnnonce.getTarif()).isEqualTo(UPDATED_TARIF);
        assertThat(testAnnonce.getPostulantRetenu()).isEqualTo(UPDATED_POSTULANT_RETENU);
        assertThat(testAnnonce.getImageVideo()).isEqualTo(UPDATED_IMAGE_VIDEO);
        assertThat(testAnnonce.getImageVideoContentType()).isEqualTo(UPDATED_IMAGE_VIDEO_CONTENT_TYPE);
        assertThat(testAnnonce.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingAnnonce() throws Exception {
        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();
        annonce.setId(count.incrementAndGet());

        // Create the Annonce
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, annonceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(annonceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnnonce() throws Exception {
        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();
        annonce.setId(count.incrementAndGet());

        // Create the Annonce
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(annonceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnnonce() throws Exception {
        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();
        annonce.setId(count.incrementAndGet());

        // Create the Annonce
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(annonceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnnonceWithPatch() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);

        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();

        // Update the annonce using partial update
        Annonce partialUpdatedAnnonce = new Annonce();
        partialUpdatedAnnonce.setId(annonce.getId());

        partialUpdatedAnnonce
            .titre(UPDATED_TITRE)
            .dateDeDelais(UPDATED_DATE_DE_DELAIS)
            .postulantRetenu(UPDATED_POSTULANT_RETENU)
            .imageVideo(UPDATED_IMAGE_VIDEO)
            .imageVideoContentType(UPDATED_IMAGE_VIDEO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);

        restAnnonceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnonce.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnonce))
            )
            .andExpect(status().isOk());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
        Annonce testAnnonce = annonceList.get(annonceList.size() - 1);
        assertThat(testAnnonce.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testAnnonce.getDateDeDelegation()).isEqualTo(DEFAULT_DATE_DE_DELEGATION);
        assertThat(testAnnonce.getDateDeDelais()).isEqualTo(UPDATED_DATE_DE_DELAIS);
        assertThat(testAnnonce.getTarif()).isEqualTo(DEFAULT_TARIF);
        assertThat(testAnnonce.getPostulantRetenu()).isEqualTo(UPDATED_POSTULANT_RETENU);
        assertThat(testAnnonce.getImageVideo()).isEqualTo(UPDATED_IMAGE_VIDEO);
        assertThat(testAnnonce.getImageVideoContentType()).isEqualTo(UPDATED_IMAGE_VIDEO_CONTENT_TYPE);
        assertThat(testAnnonce.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateAnnonceWithPatch() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);

        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();

        // Update the annonce using partial update
        Annonce partialUpdatedAnnonce = new Annonce();
        partialUpdatedAnnonce.setId(annonce.getId());

        partialUpdatedAnnonce
            .titre(UPDATED_TITRE)
            .dateDeDelegation(UPDATED_DATE_DE_DELEGATION)
            .dateDeDelais(UPDATED_DATE_DE_DELAIS)
            .tarif(UPDATED_TARIF)
            .postulantRetenu(UPDATED_POSTULANT_RETENU)
            .imageVideo(UPDATED_IMAGE_VIDEO)
            .imageVideoContentType(UPDATED_IMAGE_VIDEO_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);

        restAnnonceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnonce.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnonce))
            )
            .andExpect(status().isOk());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
        Annonce testAnnonce = annonceList.get(annonceList.size() - 1);
        assertThat(testAnnonce.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testAnnonce.getDateDeDelegation()).isEqualTo(UPDATED_DATE_DE_DELEGATION);
        assertThat(testAnnonce.getDateDeDelais()).isEqualTo(UPDATED_DATE_DE_DELAIS);
        assertThat(testAnnonce.getTarif()).isEqualTo(UPDATED_TARIF);
        assertThat(testAnnonce.getPostulantRetenu()).isEqualTo(UPDATED_POSTULANT_RETENU);
        assertThat(testAnnonce.getImageVideo()).isEqualTo(UPDATED_IMAGE_VIDEO);
        assertThat(testAnnonce.getImageVideoContentType()).isEqualTo(UPDATED_IMAGE_VIDEO_CONTENT_TYPE);
        assertThat(testAnnonce.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingAnnonce() throws Exception {
        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();
        annonce.setId(count.incrementAndGet());

        // Create the Annonce
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, annonceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(annonceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnnonce() throws Exception {
        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();
        annonce.setId(count.incrementAndGet());

        // Create the Annonce
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(annonceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnnonce() throws Exception {
        int databaseSizeBeforeUpdate = annonceRepository.findAll().size();
        annonce.setId(count.incrementAndGet());

        // Create the Annonce
        AnnonceDTO annonceDTO = annonceMapper.toDto(annonce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnonceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(annonceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Annonce in the database
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnnonce() throws Exception {
        // Initialize the database
        annonceRepository.saveAndFlush(annonce);

        int databaseSizeBeforeDelete = annonceRepository.findAll().size();

        // Delete the annonce
        restAnnonceMockMvc
            .perform(delete(ENTITY_API_URL_ID, annonce.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Annonce> annonceList = annonceRepository.findAll();
        assertThat(annonceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
