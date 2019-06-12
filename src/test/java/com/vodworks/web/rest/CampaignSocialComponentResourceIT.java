package com.vodworks.web.rest;

import com.vodworks.JhipsterBaseAppApp;
import com.vodworks.domain.CampaignSocialComponent;
import com.vodworks.repository.CampaignSocialComponentRepository;
import com.vodworks.service.CampaignSocialComponentService;
import com.vodworks.service.dto.CampaignSocialComponentDTO;
import com.vodworks.service.mapper.CampaignSocialComponentMapper;
import com.vodworks.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.vodworks.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CampaignSocialComponentResource} REST controller.
 */
@SpringBootTest(classes = JhipsterBaseAppApp.class)
public class CampaignSocialComponentResourceIT {

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_INFLUENCER = "AAAAAAAAAA";
    private static final String UPDATED_INFLUENCER = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_FILE_URL = "BBBBBBBBBB";

    @Autowired
    private CampaignSocialComponentRepository campaignSocialComponentRepository;

    @Autowired
    private CampaignSocialComponentMapper campaignSocialComponentMapper;

    @Autowired
    private CampaignSocialComponentService campaignSocialComponentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCampaignSocialComponentMockMvc;

    private CampaignSocialComponent campaignSocialComponent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampaignSocialComponentResource campaignSocialComponentResource = new CampaignSocialComponentResource(campaignSocialComponentService);
        this.restCampaignSocialComponentMockMvc = MockMvcBuilders.standaloneSetup(campaignSocialComponentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampaignSocialComponent createEntity(EntityManager em) {
        CampaignSocialComponent campaignSocialComponent = new CampaignSocialComponent()
            .caption(DEFAULT_CAPTION)
            .influencer(DEFAULT_INFLUENCER)
            .imageFileUrl(DEFAULT_IMAGE_FILE_URL);
        return campaignSocialComponent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampaignSocialComponent createUpdatedEntity(EntityManager em) {
        CampaignSocialComponent campaignSocialComponent = new CampaignSocialComponent()
            .caption(UPDATED_CAPTION)
            .influencer(UPDATED_INFLUENCER)
            .imageFileUrl(UPDATED_IMAGE_FILE_URL);
        return campaignSocialComponent;
    }

    @BeforeEach
    public void initTest() {
        campaignSocialComponent = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampaignSocialComponent() throws Exception {
        int databaseSizeBeforeCreate = campaignSocialComponentRepository.findAll().size();

        // Create the CampaignSocialComponent
        CampaignSocialComponentDTO campaignSocialComponentDTO = campaignSocialComponentMapper.toDto(campaignSocialComponent);
        restCampaignSocialComponentMockMvc.perform(post("/api/campaign-social-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignSocialComponentDTO)))
            .andExpect(status().isCreated());

        // Validate the CampaignSocialComponent in the database
        List<CampaignSocialComponent> campaignSocialComponentList = campaignSocialComponentRepository.findAll();
        assertThat(campaignSocialComponentList).hasSize(databaseSizeBeforeCreate + 1);
        CampaignSocialComponent testCampaignSocialComponent = campaignSocialComponentList.get(campaignSocialComponentList.size() - 1);
        assertThat(testCampaignSocialComponent.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testCampaignSocialComponent.getInfluencer()).isEqualTo(DEFAULT_INFLUENCER);
        assertThat(testCampaignSocialComponent.getImageFileUrl()).isEqualTo(DEFAULT_IMAGE_FILE_URL);
    }

    @Test
    @Transactional
    public void createCampaignSocialComponentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campaignSocialComponentRepository.findAll().size();

        // Create the CampaignSocialComponent with an existing ID
        campaignSocialComponent.setId(1L);
        CampaignSocialComponentDTO campaignSocialComponentDTO = campaignSocialComponentMapper.toDto(campaignSocialComponent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaignSocialComponentMockMvc.perform(post("/api/campaign-social-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignSocialComponentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignSocialComponent in the database
        List<CampaignSocialComponent> campaignSocialComponentList = campaignSocialComponentRepository.findAll();
        assertThat(campaignSocialComponentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCaptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignSocialComponentRepository.findAll().size();
        // set the field null
        campaignSocialComponent.setCaption(null);

        // Create the CampaignSocialComponent, which fails.
        CampaignSocialComponentDTO campaignSocialComponentDTO = campaignSocialComponentMapper.toDto(campaignSocialComponent);

        restCampaignSocialComponentMockMvc.perform(post("/api/campaign-social-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignSocialComponentDTO)))
            .andExpect(status().isBadRequest());

        List<CampaignSocialComponent> campaignSocialComponentList = campaignSocialComponentRepository.findAll();
        assertThat(campaignSocialComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInfluencerIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignSocialComponentRepository.findAll().size();
        // set the field null
        campaignSocialComponent.setInfluencer(null);

        // Create the CampaignSocialComponent, which fails.
        CampaignSocialComponentDTO campaignSocialComponentDTO = campaignSocialComponentMapper.toDto(campaignSocialComponent);

        restCampaignSocialComponentMockMvc.perform(post("/api/campaign-social-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignSocialComponentDTO)))
            .andExpect(status().isBadRequest());

        List<CampaignSocialComponent> campaignSocialComponentList = campaignSocialComponentRepository.findAll();
        assertThat(campaignSocialComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImageFileUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignSocialComponentRepository.findAll().size();
        // set the field null
        campaignSocialComponent.setImageFileUrl(null);

        // Create the CampaignSocialComponent, which fails.
        CampaignSocialComponentDTO campaignSocialComponentDTO = campaignSocialComponentMapper.toDto(campaignSocialComponent);

        restCampaignSocialComponentMockMvc.perform(post("/api/campaign-social-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignSocialComponentDTO)))
            .andExpect(status().isBadRequest());

        List<CampaignSocialComponent> campaignSocialComponentList = campaignSocialComponentRepository.findAll();
        assertThat(campaignSocialComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCampaignSocialComponents() throws Exception {
        // Initialize the database
        campaignSocialComponentRepository.saveAndFlush(campaignSocialComponent);

        // Get all the campaignSocialComponentList
        restCampaignSocialComponentMockMvc.perform(get("/api/campaign-social-components?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaignSocialComponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION.toString())))
            .andExpect(jsonPath("$.[*].influencer").value(hasItem(DEFAULT_INFLUENCER.toString())))
            .andExpect(jsonPath("$.[*].imageFileUrl").value(hasItem(DEFAULT_IMAGE_FILE_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getCampaignSocialComponent() throws Exception {
        // Initialize the database
        campaignSocialComponentRepository.saveAndFlush(campaignSocialComponent);

        // Get the campaignSocialComponent
        restCampaignSocialComponentMockMvc.perform(get("/api/campaign-social-components/{id}", campaignSocialComponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campaignSocialComponent.getId().intValue()))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION.toString()))
            .andExpect(jsonPath("$.influencer").value(DEFAULT_INFLUENCER.toString()))
            .andExpect(jsonPath("$.imageFileUrl").value(DEFAULT_IMAGE_FILE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCampaignSocialComponent() throws Exception {
        // Get the campaignSocialComponent
        restCampaignSocialComponentMockMvc.perform(get("/api/campaign-social-components/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampaignSocialComponent() throws Exception {
        // Initialize the database
        campaignSocialComponentRepository.saveAndFlush(campaignSocialComponent);

        int databaseSizeBeforeUpdate = campaignSocialComponentRepository.findAll().size();

        // Update the campaignSocialComponent
        CampaignSocialComponent updatedCampaignSocialComponent = campaignSocialComponentRepository.findById(campaignSocialComponent.getId()).get();
        // Disconnect from session so that the updates on updatedCampaignSocialComponent are not directly saved in db
        em.detach(updatedCampaignSocialComponent);
        updatedCampaignSocialComponent
            .caption(UPDATED_CAPTION)
            .influencer(UPDATED_INFLUENCER)
            .imageFileUrl(UPDATED_IMAGE_FILE_URL);
        CampaignSocialComponentDTO campaignSocialComponentDTO = campaignSocialComponentMapper.toDto(updatedCampaignSocialComponent);

        restCampaignSocialComponentMockMvc.perform(put("/api/campaign-social-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignSocialComponentDTO)))
            .andExpect(status().isOk());

        // Validate the CampaignSocialComponent in the database
        List<CampaignSocialComponent> campaignSocialComponentList = campaignSocialComponentRepository.findAll();
        assertThat(campaignSocialComponentList).hasSize(databaseSizeBeforeUpdate);
        CampaignSocialComponent testCampaignSocialComponent = campaignSocialComponentList.get(campaignSocialComponentList.size() - 1);
        assertThat(testCampaignSocialComponent.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testCampaignSocialComponent.getInfluencer()).isEqualTo(UPDATED_INFLUENCER);
        assertThat(testCampaignSocialComponent.getImageFileUrl()).isEqualTo(UPDATED_IMAGE_FILE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingCampaignSocialComponent() throws Exception {
        int databaseSizeBeforeUpdate = campaignSocialComponentRepository.findAll().size();

        // Create the CampaignSocialComponent
        CampaignSocialComponentDTO campaignSocialComponentDTO = campaignSocialComponentMapper.toDto(campaignSocialComponent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampaignSocialComponentMockMvc.perform(put("/api/campaign-social-components")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignSocialComponentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignSocialComponent in the database
        List<CampaignSocialComponent> campaignSocialComponentList = campaignSocialComponentRepository.findAll();
        assertThat(campaignSocialComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampaignSocialComponent() throws Exception {
        // Initialize the database
        campaignSocialComponentRepository.saveAndFlush(campaignSocialComponent);

        int databaseSizeBeforeDelete = campaignSocialComponentRepository.findAll().size();

        // Delete the campaignSocialComponent
        restCampaignSocialComponentMockMvc.perform(delete("/api/campaign-social-components/{id}", campaignSocialComponent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CampaignSocialComponent> campaignSocialComponentList = campaignSocialComponentRepository.findAll();
        assertThat(campaignSocialComponentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignSocialComponent.class);
        CampaignSocialComponent campaignSocialComponent1 = new CampaignSocialComponent();
        campaignSocialComponent1.setId(1L);
        CampaignSocialComponent campaignSocialComponent2 = new CampaignSocialComponent();
        campaignSocialComponent2.setId(campaignSocialComponent1.getId());
        assertThat(campaignSocialComponent1).isEqualTo(campaignSocialComponent2);
        campaignSocialComponent2.setId(2L);
        assertThat(campaignSocialComponent1).isNotEqualTo(campaignSocialComponent2);
        campaignSocialComponent1.setId(null);
        assertThat(campaignSocialComponent1).isNotEqualTo(campaignSocialComponent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignSocialComponentDTO.class);
        CampaignSocialComponentDTO campaignSocialComponentDTO1 = new CampaignSocialComponentDTO();
        campaignSocialComponentDTO1.setId(1L);
        CampaignSocialComponentDTO campaignSocialComponentDTO2 = new CampaignSocialComponentDTO();
        assertThat(campaignSocialComponentDTO1).isNotEqualTo(campaignSocialComponentDTO2);
        campaignSocialComponentDTO2.setId(campaignSocialComponentDTO1.getId());
        assertThat(campaignSocialComponentDTO1).isEqualTo(campaignSocialComponentDTO2);
        campaignSocialComponentDTO2.setId(2L);
        assertThat(campaignSocialComponentDTO1).isNotEqualTo(campaignSocialComponentDTO2);
        campaignSocialComponentDTO1.setId(null);
        assertThat(campaignSocialComponentDTO1).isNotEqualTo(campaignSocialComponentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(campaignSocialComponentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(campaignSocialComponentMapper.fromId(null)).isNull();
    }
}
