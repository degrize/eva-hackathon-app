package com.hackathon.eva.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hackathon.eva.IntegrationTest;
import com.hackathon.eva.domain.Souscription;
import com.hackathon.eva.domain.enumeration.EtatCompte;
import com.hackathon.eva.repository.SouscriptionRepository;
import com.hackathon.eva.service.SouscriptionService;
import com.hackathon.eva.service.dto.SouscriptionDTO;
import com.hackathon.eva.service.mapper.SouscriptionMapper;
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

/**
 * Integration tests for the {@link SouscriptionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SouscriptionResourceIT {

    private static final EtatCompte DEFAULT_ETAT = EtatCompte.PREMIUM;
    private static final EtatCompte UPDATED_ETAT = EtatCompte.NORMAL;

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Double DEFAULT_POURCENTAGE_DU_DON = 1D;
    private static final Double UPDATED_POURCENTAGE_DU_DON = 2D;

    private static final String ENTITY_API_URL = "/api/souscriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SouscriptionRepository souscriptionRepository;

    @Mock
    private SouscriptionRepository souscriptionRepositoryMock;

    @Autowired
    private SouscriptionMapper souscriptionMapper;

    @Mock
    private SouscriptionService souscriptionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSouscriptionMockMvc;

    private Souscription souscription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Souscription createEntity(EntityManager em) {
        Souscription souscription = new Souscription()
            .etat(DEFAULT_ETAT)
            .montant(DEFAULT_MONTANT)
            .pourcentageDuDon(DEFAULT_POURCENTAGE_DU_DON);
        return souscription;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Souscription createUpdatedEntity(EntityManager em) {
        Souscription souscription = new Souscription()
            .etat(UPDATED_ETAT)
            .montant(UPDATED_MONTANT)
            .pourcentageDuDon(UPDATED_POURCENTAGE_DU_DON);
        return souscription;
    }

    @BeforeEach
    public void initTest() {
        souscription = createEntity(em);
    }

    @Test
    @Transactional
    void createSouscription() throws Exception {
        int databaseSizeBeforeCreate = souscriptionRepository.findAll().size();
        // Create the Souscription
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);
        restSouscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Souscription testSouscription = souscriptionList.get(souscriptionList.size() - 1);
        assertThat(testSouscription.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testSouscription.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testSouscription.getPourcentageDuDon()).isEqualTo(DEFAULT_POURCENTAGE_DU_DON);
    }

    @Test
    @Transactional
    void createSouscriptionWithExistingId() throws Exception {
        // Create the Souscription with an existing ID
        souscription.setId(1L);
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);

        int databaseSizeBeforeCreate = souscriptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSouscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = souscriptionRepository.findAll().size();
        // set the field null
        souscription.setEtat(null);

        // Create the Souscription, which fails.
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);

        restSouscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = souscriptionRepository.findAll().size();
        // set the field null
        souscription.setMontant(null);

        // Create the Souscription, which fails.
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);

        restSouscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPourcentageDuDonIsRequired() throws Exception {
        int databaseSizeBeforeTest = souscriptionRepository.findAll().size();
        // set the field null
        souscription.setPourcentageDuDon(null);

        // Create the Souscription, which fails.
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);

        restSouscriptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSouscriptions() throws Exception {
        // Initialize the database
        souscriptionRepository.saveAndFlush(souscription);

        // Get all the souscriptionList
        restSouscriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(souscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].pourcentageDuDon").value(hasItem(DEFAULT_POURCENTAGE_DU_DON.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSouscriptionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(souscriptionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSouscriptionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(souscriptionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSouscriptionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(souscriptionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSouscriptionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(souscriptionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSouscription() throws Exception {
        // Initialize the database
        souscriptionRepository.saveAndFlush(souscription);

        // Get the souscription
        restSouscriptionMockMvc
            .perform(get(ENTITY_API_URL_ID, souscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(souscription.getId().intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.pourcentageDuDon").value(DEFAULT_POURCENTAGE_DU_DON.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingSouscription() throws Exception {
        // Get the souscription
        restSouscriptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSouscription() throws Exception {
        // Initialize the database
        souscriptionRepository.saveAndFlush(souscription);

        int databaseSizeBeforeUpdate = souscriptionRepository.findAll().size();

        // Update the souscription
        Souscription updatedSouscription = souscriptionRepository.findById(souscription.getId()).get();
        // Disconnect from session so that the updates on updatedSouscription are not directly saved in db
        em.detach(updatedSouscription);
        updatedSouscription.etat(UPDATED_ETAT).montant(UPDATED_MONTANT).pourcentageDuDon(UPDATED_POURCENTAGE_DU_DON);
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(updatedSouscription);

        restSouscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, souscriptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeUpdate);
        Souscription testSouscription = souscriptionList.get(souscriptionList.size() - 1);
        assertThat(testSouscription.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testSouscription.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testSouscription.getPourcentageDuDon()).isEqualTo(UPDATED_POURCENTAGE_DU_DON);
    }

    @Test
    @Transactional
    void putNonExistingSouscription() throws Exception {
        int databaseSizeBeforeUpdate = souscriptionRepository.findAll().size();
        souscription.setId(count.incrementAndGet());

        // Create the Souscription
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSouscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, souscriptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSouscription() throws Exception {
        int databaseSizeBeforeUpdate = souscriptionRepository.findAll().size();
        souscription.setId(count.incrementAndGet());

        // Create the Souscription
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSouscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSouscription() throws Exception {
        int databaseSizeBeforeUpdate = souscriptionRepository.findAll().size();
        souscription.setId(count.incrementAndGet());

        // Create the Souscription
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSouscriptionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSouscriptionWithPatch() throws Exception {
        // Initialize the database
        souscriptionRepository.saveAndFlush(souscription);

        int databaseSizeBeforeUpdate = souscriptionRepository.findAll().size();

        // Update the souscription using partial update
        Souscription partialUpdatedSouscription = new Souscription();
        partialUpdatedSouscription.setId(souscription.getId());

        partialUpdatedSouscription.etat(UPDATED_ETAT);

        restSouscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSouscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSouscription))
            )
            .andExpect(status().isOk());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeUpdate);
        Souscription testSouscription = souscriptionList.get(souscriptionList.size() - 1);
        assertThat(testSouscription.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testSouscription.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testSouscription.getPourcentageDuDon()).isEqualTo(DEFAULT_POURCENTAGE_DU_DON);
    }

    @Test
    @Transactional
    void fullUpdateSouscriptionWithPatch() throws Exception {
        // Initialize the database
        souscriptionRepository.saveAndFlush(souscription);

        int databaseSizeBeforeUpdate = souscriptionRepository.findAll().size();

        // Update the souscription using partial update
        Souscription partialUpdatedSouscription = new Souscription();
        partialUpdatedSouscription.setId(souscription.getId());

        partialUpdatedSouscription.etat(UPDATED_ETAT).montant(UPDATED_MONTANT).pourcentageDuDon(UPDATED_POURCENTAGE_DU_DON);

        restSouscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSouscription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSouscription))
            )
            .andExpect(status().isOk());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeUpdate);
        Souscription testSouscription = souscriptionList.get(souscriptionList.size() - 1);
        assertThat(testSouscription.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testSouscription.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testSouscription.getPourcentageDuDon()).isEqualTo(UPDATED_POURCENTAGE_DU_DON);
    }

    @Test
    @Transactional
    void patchNonExistingSouscription() throws Exception {
        int databaseSizeBeforeUpdate = souscriptionRepository.findAll().size();
        souscription.setId(count.incrementAndGet());

        // Create the Souscription
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSouscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, souscriptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSouscription() throws Exception {
        int databaseSizeBeforeUpdate = souscriptionRepository.findAll().size();
        souscription.setId(count.incrementAndGet());

        // Create the Souscription
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSouscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSouscription() throws Exception {
        int databaseSizeBeforeUpdate = souscriptionRepository.findAll().size();
        souscription.setId(count.incrementAndGet());

        // Create the Souscription
        SouscriptionDTO souscriptionDTO = souscriptionMapper.toDto(souscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSouscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(souscriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Souscription in the database
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSouscription() throws Exception {
        // Initialize the database
        souscriptionRepository.saveAndFlush(souscription);

        int databaseSizeBeforeDelete = souscriptionRepository.findAll().size();

        // Delete the souscription
        restSouscriptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, souscription.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Souscription> souscriptionList = souscriptionRepository.findAll();
        assertThat(souscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
