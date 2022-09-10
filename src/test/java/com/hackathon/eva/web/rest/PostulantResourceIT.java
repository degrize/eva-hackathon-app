package com.hackathon.eva.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hackathon.eva.IntegrationTest;
import com.hackathon.eva.domain.Postulant;
import com.hackathon.eva.repository.PostulantRepository;
import com.hackathon.eva.service.PostulantService;
import com.hackathon.eva.service.dto.PostulantDTO;
import com.hackathon.eva.service.mapper.PostulantMapper;
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
 * Integration tests for the {@link PostulantResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PostulantResourceIT {

    private static final String DEFAULT_NUMERO_MOMO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_MOMO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/postulants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostulantRepository postulantRepository;

    @Mock
    private PostulantRepository postulantRepositoryMock;

    @Autowired
    private PostulantMapper postulantMapper;

    @Mock
    private PostulantService postulantServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostulantMockMvc;

    private Postulant postulant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Postulant createEntity(EntityManager em) {
        Postulant postulant = new Postulant().numeroMomo(DEFAULT_NUMERO_MOMO).observation(DEFAULT_OBSERVATION);
        return postulant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Postulant createUpdatedEntity(EntityManager em) {
        Postulant postulant = new Postulant().numeroMomo(UPDATED_NUMERO_MOMO).observation(UPDATED_OBSERVATION);
        return postulant;
    }

    @BeforeEach
    public void initTest() {
        postulant = createEntity(em);
    }

    @Test
    @Transactional
    void createPostulant() throws Exception {
        int databaseSizeBeforeCreate = postulantRepository.findAll().size();
        // Create the Postulant
        PostulantDTO postulantDTO = postulantMapper.toDto(postulant);
        restPostulantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postulantDTO)))
            .andExpect(status().isCreated());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeCreate + 1);
        Postulant testPostulant = postulantList.get(postulantList.size() - 1);
        assertThat(testPostulant.getNumeroMomo()).isEqualTo(DEFAULT_NUMERO_MOMO);
        assertThat(testPostulant.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
    }

    @Test
    @Transactional
    void createPostulantWithExistingId() throws Exception {
        // Create the Postulant with an existing ID
        postulant.setId(1L);
        PostulantDTO postulantDTO = postulantMapper.toDto(postulant);

        int databaseSizeBeforeCreate = postulantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostulantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postulantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroMomoIsRequired() throws Exception {
        int databaseSizeBeforeTest = postulantRepository.findAll().size();
        // set the field null
        postulant.setNumeroMomo(null);

        // Create the Postulant, which fails.
        PostulantDTO postulantDTO = postulantMapper.toDto(postulant);

        restPostulantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postulantDTO)))
            .andExpect(status().isBadRequest());

        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPostulants() throws Exception {
        // Initialize the database
        postulantRepository.saveAndFlush(postulant);

        // Get all the postulantList
        restPostulantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postulant.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroMomo").value(hasItem(DEFAULT_NUMERO_MOMO)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostulantsWithEagerRelationshipsIsEnabled() throws Exception {
        when(postulantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostulantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(postulantServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostulantsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(postulantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostulantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(postulantRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPostulant() throws Exception {
        // Initialize the database
        postulantRepository.saveAndFlush(postulant);

        // Get the postulant
        restPostulantMockMvc
            .perform(get(ENTITY_API_URL_ID, postulant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postulant.getId().intValue()))
            .andExpect(jsonPath("$.numeroMomo").value(DEFAULT_NUMERO_MOMO))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION));
    }

    @Test
    @Transactional
    void getNonExistingPostulant() throws Exception {
        // Get the postulant
        restPostulantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPostulant() throws Exception {
        // Initialize the database
        postulantRepository.saveAndFlush(postulant);

        int databaseSizeBeforeUpdate = postulantRepository.findAll().size();

        // Update the postulant
        Postulant updatedPostulant = postulantRepository.findById(postulant.getId()).get();
        // Disconnect from session so that the updates on updatedPostulant are not directly saved in db
        em.detach(updatedPostulant);
        updatedPostulant.numeroMomo(UPDATED_NUMERO_MOMO).observation(UPDATED_OBSERVATION);
        PostulantDTO postulantDTO = postulantMapper.toDto(updatedPostulant);

        restPostulantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postulantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postulantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeUpdate);
        Postulant testPostulant = postulantList.get(postulantList.size() - 1);
        assertThat(testPostulant.getNumeroMomo()).isEqualTo(UPDATED_NUMERO_MOMO);
        assertThat(testPostulant.getObservation()).isEqualTo(UPDATED_OBSERVATION);
    }

    @Test
    @Transactional
    void putNonExistingPostulant() throws Exception {
        int databaseSizeBeforeUpdate = postulantRepository.findAll().size();
        postulant.setId(count.incrementAndGet());

        // Create the Postulant
        PostulantDTO postulantDTO = postulantMapper.toDto(postulant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostulantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postulantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postulantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPostulant() throws Exception {
        int databaseSizeBeforeUpdate = postulantRepository.findAll().size();
        postulant.setId(count.incrementAndGet());

        // Create the Postulant
        PostulantDTO postulantDTO = postulantMapper.toDto(postulant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostulantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postulantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPostulant() throws Exception {
        int databaseSizeBeforeUpdate = postulantRepository.findAll().size();
        postulant.setId(count.incrementAndGet());

        // Create the Postulant
        PostulantDTO postulantDTO = postulantMapper.toDto(postulant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostulantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postulantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePostulantWithPatch() throws Exception {
        // Initialize the database
        postulantRepository.saveAndFlush(postulant);

        int databaseSizeBeforeUpdate = postulantRepository.findAll().size();

        // Update the postulant using partial update
        Postulant partialUpdatedPostulant = new Postulant();
        partialUpdatedPostulant.setId(postulant.getId());

        restPostulantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostulant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostulant))
            )
            .andExpect(status().isOk());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeUpdate);
        Postulant testPostulant = postulantList.get(postulantList.size() - 1);
        assertThat(testPostulant.getNumeroMomo()).isEqualTo(DEFAULT_NUMERO_MOMO);
        assertThat(testPostulant.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
    }

    @Test
    @Transactional
    void fullUpdatePostulantWithPatch() throws Exception {
        // Initialize the database
        postulantRepository.saveAndFlush(postulant);

        int databaseSizeBeforeUpdate = postulantRepository.findAll().size();

        // Update the postulant using partial update
        Postulant partialUpdatedPostulant = new Postulant();
        partialUpdatedPostulant.setId(postulant.getId());

        partialUpdatedPostulant.numeroMomo(UPDATED_NUMERO_MOMO).observation(UPDATED_OBSERVATION);

        restPostulantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostulant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostulant))
            )
            .andExpect(status().isOk());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeUpdate);
        Postulant testPostulant = postulantList.get(postulantList.size() - 1);
        assertThat(testPostulant.getNumeroMomo()).isEqualTo(UPDATED_NUMERO_MOMO);
        assertThat(testPostulant.getObservation()).isEqualTo(UPDATED_OBSERVATION);
    }

    @Test
    @Transactional
    void patchNonExistingPostulant() throws Exception {
        int databaseSizeBeforeUpdate = postulantRepository.findAll().size();
        postulant.setId(count.incrementAndGet());

        // Create the Postulant
        PostulantDTO postulantDTO = postulantMapper.toDto(postulant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostulantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postulantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postulantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPostulant() throws Exception {
        int databaseSizeBeforeUpdate = postulantRepository.findAll().size();
        postulant.setId(count.incrementAndGet());

        // Create the Postulant
        PostulantDTO postulantDTO = postulantMapper.toDto(postulant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostulantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postulantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPostulant() throws Exception {
        int databaseSizeBeforeUpdate = postulantRepository.findAll().size();
        postulant.setId(count.incrementAndGet());

        // Create the Postulant
        PostulantDTO postulantDTO = postulantMapper.toDto(postulant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostulantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(postulantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Postulant in the database
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePostulant() throws Exception {
        // Initialize the database
        postulantRepository.saveAndFlush(postulant);

        int databaseSizeBeforeDelete = postulantRepository.findAll().size();

        // Delete the postulant
        restPostulantMockMvc
            .perform(delete(ENTITY_API_URL_ID, postulant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Postulant> postulantList = postulantRepository.findAll();
        assertThat(postulantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
