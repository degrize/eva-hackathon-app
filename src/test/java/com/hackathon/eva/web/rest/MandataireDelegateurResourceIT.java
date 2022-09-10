package com.hackathon.eva.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hackathon.eva.IntegrationTest;
import com.hackathon.eva.domain.MandataireDelegateur;
import com.hackathon.eva.domain.enumeration.EtatCompte;
import com.hackathon.eva.domain.enumeration.Sexe;
import com.hackathon.eva.domain.enumeration.SituationMatrimoniale;
import com.hackathon.eva.repository.MandataireDelegateurRepository;
import com.hackathon.eva.service.dto.MandataireDelegateurDTO;
import com.hackathon.eva.service.mapper.MandataireDelegateurMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MandataireDelegateurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MandataireDelegateurResourceIT {

    private static final String DEFAULT_NOM_DE_FAMILLE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DE_FAMILLE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_MOMO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_MOMO = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.F;
    private static final Sexe UPDATED_SEXE = Sexe.M;

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final EtatCompte DEFAULT_ETAT_COMPTE = EtatCompte.PREMIUM;
    private static final EtatCompte UPDATED_ETAT_COMPTE = EtatCompte.NORMAL;

    private static final SituationMatrimoniale DEFAULT_SITUATION_MATRIMONIALE = SituationMatrimoniale.CELIBATAIRE;
    private static final SituationMatrimoniale UPDATED_SITUATION_MATRIMONIALE = SituationMatrimoniale.FIANCE;

    private static final String ENTITY_API_URL = "/api/mandataire-delegateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MandataireDelegateurRepository mandataireDelegateurRepository;

    @Autowired
    private MandataireDelegateurMapper mandataireDelegateurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMandataireDelegateurMockMvc;

    private MandataireDelegateur mandataireDelegateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MandataireDelegateur createEntity(EntityManager em) {
        MandataireDelegateur mandataireDelegateur = new MandataireDelegateur()
            .nomDeFamille(DEFAULT_NOM_DE_FAMILLE)
            .prenom(DEFAULT_PRENOM)
            .contact(DEFAULT_CONTACT)
            .email(DEFAULT_EMAIL)
            .numeroMomo(DEFAULT_NUMERO_MOMO)
            .sexe(DEFAULT_SEXE)
            .pays(DEFAULT_PAYS)
            .ville(DEFAULT_VILLE)
            .adresse(DEFAULT_ADRESSE)
            .etatCompte(DEFAULT_ETAT_COMPTE)
            .situationMatrimoniale(DEFAULT_SITUATION_MATRIMONIALE);
        return mandataireDelegateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MandataireDelegateur createUpdatedEntity(EntityManager em) {
        MandataireDelegateur mandataireDelegateur = new MandataireDelegateur()
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .contact(UPDATED_CONTACT)
            .email(UPDATED_EMAIL)
            .numeroMomo(UPDATED_NUMERO_MOMO)
            .sexe(UPDATED_SEXE)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .adresse(UPDATED_ADRESSE)
            .etatCompte(UPDATED_ETAT_COMPTE)
            .situationMatrimoniale(UPDATED_SITUATION_MATRIMONIALE);
        return mandataireDelegateur;
    }

    @BeforeEach
    public void initTest() {
        mandataireDelegateur = createEntity(em);
    }

    @Test
    @Transactional
    void createMandataireDelegateur() throws Exception {
        int databaseSizeBeforeCreate = mandataireDelegateurRepository.findAll().size();
        // Create the MandataireDelegateur
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);
        restMandataireDelegateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeCreate + 1);
        MandataireDelegateur testMandataireDelegateur = mandataireDelegateurList.get(mandataireDelegateurList.size() - 1);
        assertThat(testMandataireDelegateur.getNomDeFamille()).isEqualTo(DEFAULT_NOM_DE_FAMILLE);
        assertThat(testMandataireDelegateur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMandataireDelegateur.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testMandataireDelegateur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMandataireDelegateur.getNumeroMomo()).isEqualTo(DEFAULT_NUMERO_MOMO);
        assertThat(testMandataireDelegateur.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testMandataireDelegateur.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testMandataireDelegateur.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testMandataireDelegateur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testMandataireDelegateur.getEtatCompte()).isEqualTo(DEFAULT_ETAT_COMPTE);
        assertThat(testMandataireDelegateur.getSituationMatrimoniale()).isEqualTo(DEFAULT_SITUATION_MATRIMONIALE);
    }

    @Test
    @Transactional
    void createMandataireDelegateurWithExistingId() throws Exception {
        // Create the MandataireDelegateur with an existing ID
        mandataireDelegateur.setId(1L);
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        int databaseSizeBeforeCreate = mandataireDelegateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMandataireDelegateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomDeFamilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = mandataireDelegateurRepository.findAll().size();
        // set the field null
        mandataireDelegateur.setNomDeFamille(null);

        // Create the MandataireDelegateur, which fails.
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        restMandataireDelegateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = mandataireDelegateurRepository.findAll().size();
        // set the field null
        mandataireDelegateur.setPrenom(null);

        // Create the MandataireDelegateur, which fails.
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        restMandataireDelegateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = mandataireDelegateurRepository.findAll().size();
        // set the field null
        mandataireDelegateur.setContact(null);

        // Create the MandataireDelegateur, which fails.
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        restMandataireDelegateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = mandataireDelegateurRepository.findAll().size();
        // set the field null
        mandataireDelegateur.setEmail(null);

        // Create the MandataireDelegateur, which fails.
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        restMandataireDelegateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroMomoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mandataireDelegateurRepository.findAll().size();
        // set the field null
        mandataireDelegateur.setNumeroMomo(null);

        // Create the MandataireDelegateur, which fails.
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        restMandataireDelegateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mandataireDelegateurRepository.findAll().size();
        // set the field null
        mandataireDelegateur.setSexe(null);

        // Create the MandataireDelegateur, which fails.
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        restMandataireDelegateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMandataireDelegateurs() throws Exception {
        // Initialize the database
        mandataireDelegateurRepository.saveAndFlush(mandataireDelegateur);

        // Get all the mandataireDelegateurList
        restMandataireDelegateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mandataireDelegateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomDeFamille").value(hasItem(DEFAULT_NOM_DE_FAMILLE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].numeroMomo").value(hasItem(DEFAULT_NUMERO_MOMO)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].etatCompte").value(hasItem(DEFAULT_ETAT_COMPTE.toString())))
            .andExpect(jsonPath("$.[*].situationMatrimoniale").value(hasItem(DEFAULT_SITUATION_MATRIMONIALE.toString())));
    }

    @Test
    @Transactional
    void getMandataireDelegateur() throws Exception {
        // Initialize the database
        mandataireDelegateurRepository.saveAndFlush(mandataireDelegateur);

        // Get the mandataireDelegateur
        restMandataireDelegateurMockMvc
            .perform(get(ENTITY_API_URL_ID, mandataireDelegateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mandataireDelegateur.getId().intValue()))
            .andExpect(jsonPath("$.nomDeFamille").value(DEFAULT_NOM_DE_FAMILLE))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.numeroMomo").value(DEFAULT_NUMERO_MOMO))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.etatCompte").value(DEFAULT_ETAT_COMPTE.toString()))
            .andExpect(jsonPath("$.situationMatrimoniale").value(DEFAULT_SITUATION_MATRIMONIALE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMandataireDelegateur() throws Exception {
        // Get the mandataireDelegateur
        restMandataireDelegateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMandataireDelegateur() throws Exception {
        // Initialize the database
        mandataireDelegateurRepository.saveAndFlush(mandataireDelegateur);

        int databaseSizeBeforeUpdate = mandataireDelegateurRepository.findAll().size();

        // Update the mandataireDelegateur
        MandataireDelegateur updatedMandataireDelegateur = mandataireDelegateurRepository.findById(mandataireDelegateur.getId()).get();
        // Disconnect from session so that the updates on updatedMandataireDelegateur are not directly saved in db
        em.detach(updatedMandataireDelegateur);
        updatedMandataireDelegateur
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .contact(UPDATED_CONTACT)
            .email(UPDATED_EMAIL)
            .numeroMomo(UPDATED_NUMERO_MOMO)
            .sexe(UPDATED_SEXE)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .adresse(UPDATED_ADRESSE)
            .etatCompte(UPDATED_ETAT_COMPTE)
            .situationMatrimoniale(UPDATED_SITUATION_MATRIMONIALE);
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(updatedMandataireDelegateur);

        restMandataireDelegateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mandataireDelegateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isOk());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeUpdate);
        MandataireDelegateur testMandataireDelegateur = mandataireDelegateurList.get(mandataireDelegateurList.size() - 1);
        assertThat(testMandataireDelegateur.getNomDeFamille()).isEqualTo(UPDATED_NOM_DE_FAMILLE);
        assertThat(testMandataireDelegateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMandataireDelegateur.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testMandataireDelegateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMandataireDelegateur.getNumeroMomo()).isEqualTo(UPDATED_NUMERO_MOMO);
        assertThat(testMandataireDelegateur.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testMandataireDelegateur.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testMandataireDelegateur.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testMandataireDelegateur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testMandataireDelegateur.getEtatCompte()).isEqualTo(UPDATED_ETAT_COMPTE);
        assertThat(testMandataireDelegateur.getSituationMatrimoniale()).isEqualTo(UPDATED_SITUATION_MATRIMONIALE);
    }

    @Test
    @Transactional
    void putNonExistingMandataireDelegateur() throws Exception {
        int databaseSizeBeforeUpdate = mandataireDelegateurRepository.findAll().size();
        mandataireDelegateur.setId(count.incrementAndGet());

        // Create the MandataireDelegateur
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMandataireDelegateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mandataireDelegateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMandataireDelegateur() throws Exception {
        int databaseSizeBeforeUpdate = mandataireDelegateurRepository.findAll().size();
        mandataireDelegateur.setId(count.incrementAndGet());

        // Create the MandataireDelegateur
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandataireDelegateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMandataireDelegateur() throws Exception {
        int databaseSizeBeforeUpdate = mandataireDelegateurRepository.findAll().size();
        mandataireDelegateur.setId(count.incrementAndGet());

        // Create the MandataireDelegateur
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandataireDelegateurMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMandataireDelegateurWithPatch() throws Exception {
        // Initialize the database
        mandataireDelegateurRepository.saveAndFlush(mandataireDelegateur);

        int databaseSizeBeforeUpdate = mandataireDelegateurRepository.findAll().size();

        // Update the mandataireDelegateur using partial update
        MandataireDelegateur partialUpdatedMandataireDelegateur = new MandataireDelegateur();
        partialUpdatedMandataireDelegateur.setId(mandataireDelegateur.getId());

        partialUpdatedMandataireDelegateur
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .contact(UPDATED_CONTACT)
            .numeroMomo(UPDATED_NUMERO_MOMO)
            .sexe(UPDATED_SEXE)
            .ville(UPDATED_VILLE)
            .adresse(UPDATED_ADRESSE)
            .situationMatrimoniale(UPDATED_SITUATION_MATRIMONIALE);

        restMandataireDelegateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMandataireDelegateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMandataireDelegateur))
            )
            .andExpect(status().isOk());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeUpdate);
        MandataireDelegateur testMandataireDelegateur = mandataireDelegateurList.get(mandataireDelegateurList.size() - 1);
        assertThat(testMandataireDelegateur.getNomDeFamille()).isEqualTo(UPDATED_NOM_DE_FAMILLE);
        assertThat(testMandataireDelegateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMandataireDelegateur.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testMandataireDelegateur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMandataireDelegateur.getNumeroMomo()).isEqualTo(UPDATED_NUMERO_MOMO);
        assertThat(testMandataireDelegateur.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testMandataireDelegateur.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testMandataireDelegateur.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testMandataireDelegateur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testMandataireDelegateur.getEtatCompte()).isEqualTo(DEFAULT_ETAT_COMPTE);
        assertThat(testMandataireDelegateur.getSituationMatrimoniale()).isEqualTo(UPDATED_SITUATION_MATRIMONIALE);
    }

    @Test
    @Transactional
    void fullUpdateMandataireDelegateurWithPatch() throws Exception {
        // Initialize the database
        mandataireDelegateurRepository.saveAndFlush(mandataireDelegateur);

        int databaseSizeBeforeUpdate = mandataireDelegateurRepository.findAll().size();

        // Update the mandataireDelegateur using partial update
        MandataireDelegateur partialUpdatedMandataireDelegateur = new MandataireDelegateur();
        partialUpdatedMandataireDelegateur.setId(mandataireDelegateur.getId());

        partialUpdatedMandataireDelegateur
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .contact(UPDATED_CONTACT)
            .email(UPDATED_EMAIL)
            .numeroMomo(UPDATED_NUMERO_MOMO)
            .sexe(UPDATED_SEXE)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .adresse(UPDATED_ADRESSE)
            .etatCompte(UPDATED_ETAT_COMPTE)
            .situationMatrimoniale(UPDATED_SITUATION_MATRIMONIALE);

        restMandataireDelegateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMandataireDelegateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMandataireDelegateur))
            )
            .andExpect(status().isOk());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeUpdate);
        MandataireDelegateur testMandataireDelegateur = mandataireDelegateurList.get(mandataireDelegateurList.size() - 1);
        assertThat(testMandataireDelegateur.getNomDeFamille()).isEqualTo(UPDATED_NOM_DE_FAMILLE);
        assertThat(testMandataireDelegateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMandataireDelegateur.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testMandataireDelegateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMandataireDelegateur.getNumeroMomo()).isEqualTo(UPDATED_NUMERO_MOMO);
        assertThat(testMandataireDelegateur.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testMandataireDelegateur.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testMandataireDelegateur.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testMandataireDelegateur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testMandataireDelegateur.getEtatCompte()).isEqualTo(UPDATED_ETAT_COMPTE);
        assertThat(testMandataireDelegateur.getSituationMatrimoniale()).isEqualTo(UPDATED_SITUATION_MATRIMONIALE);
    }

    @Test
    @Transactional
    void patchNonExistingMandataireDelegateur() throws Exception {
        int databaseSizeBeforeUpdate = mandataireDelegateurRepository.findAll().size();
        mandataireDelegateur.setId(count.incrementAndGet());

        // Create the MandataireDelegateur
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMandataireDelegateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mandataireDelegateurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMandataireDelegateur() throws Exception {
        int databaseSizeBeforeUpdate = mandataireDelegateurRepository.findAll().size();
        mandataireDelegateur.setId(count.incrementAndGet());

        // Create the MandataireDelegateur
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandataireDelegateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMandataireDelegateur() throws Exception {
        int databaseSizeBeforeUpdate = mandataireDelegateurRepository.findAll().size();
        mandataireDelegateur.setId(count.incrementAndGet());

        // Create the MandataireDelegateur
        MandataireDelegateurDTO mandataireDelegateurDTO = mandataireDelegateurMapper.toDto(mandataireDelegateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMandataireDelegateurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mandataireDelegateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MandataireDelegateur in the database
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMandataireDelegateur() throws Exception {
        // Initialize the database
        mandataireDelegateurRepository.saveAndFlush(mandataireDelegateur);

        int databaseSizeBeforeDelete = mandataireDelegateurRepository.findAll().size();

        // Delete the mandataireDelegateur
        restMandataireDelegateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, mandataireDelegateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MandataireDelegateur> mandataireDelegateurList = mandataireDelegateurRepository.findAll();
        assertThat(mandataireDelegateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
