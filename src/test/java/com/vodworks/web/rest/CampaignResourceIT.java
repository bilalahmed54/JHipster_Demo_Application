package com.vodworks.web.rest;

import com.vodworks.JhipsterDemoAppApp;
import com.vodworks.domain.Campaign;
import com.vodworks.repository.CampaignRepository;
import com.vodworks.service.CampaignService;
import com.vodworks.service.dto.CampaignDTO;
import com.vodworks.service.mapper.CampaignMapper;
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
 * Integration tests for the {@Link CampaignResource} REST controller.
 */
@SpringBootTest(classes = JhipsterDemoAppApp.class)
public class CampaignResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_FIRST_TRAIT = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_FIRST_TRAIT = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_SECOND_TRAIT = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_SECOND_TRAIT = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_THIRD_TRAIT = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_THIRD_TRAIT = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_GOALS = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_GOALS = "BBBBBBBBBB";

    private static final String DEFAULT_SAMPLE = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM_AUDIENCE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM_AUDIENCE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private CampaignService campaignService;

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

    private MockMvc restCampaignMockMvc;

    private Campaign campaign;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampaignResource campaignResource = new CampaignResource(campaignService);
        this.restCampaignMockMvc = MockMvcBuilders.standaloneSetup(campaignResource)
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
    public static Campaign createEntity(EntityManager em) {
        Campaign campaign = new Campaign()
            .uuid(DEFAULT_UUID)
            .category(DEFAULT_CATEGORY)
            .activity(DEFAULT_ACTIVITY)
            .projectName(DEFAULT_PROJECT_NAME)
            .projectDetails(DEFAULT_PROJECT_DETAILS)
            .brandFirstTrait(DEFAULT_BRAND_FIRST_TRAIT)
            .brandSecondTrait(DEFAULT_BRAND_SECOND_TRAIT)
            .brandThirdTrait(DEFAULT_BRAND_THIRD_TRAIT)
            .brandGoals(DEFAULT_BRAND_GOALS)
            .sample(DEFAULT_SAMPLE)
            .instagramAudienceDescription(DEFAULT_INSTAGRAM_AUDIENCE_DESCRIPTION);
        return campaign;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campaign createUpdatedEntity(EntityManager em) {
        Campaign campaign = new Campaign()
            .uuid(UPDATED_UUID)
            .category(UPDATED_CATEGORY)
            .activity(UPDATED_ACTIVITY)
            .projectName(UPDATED_PROJECT_NAME)
            .projectDetails(UPDATED_PROJECT_DETAILS)
            .brandFirstTrait(UPDATED_BRAND_FIRST_TRAIT)
            .brandSecondTrait(UPDATED_BRAND_SECOND_TRAIT)
            .brandThirdTrait(UPDATED_BRAND_THIRD_TRAIT)
            .brandGoals(UPDATED_BRAND_GOALS)
            .sample(UPDATED_SAMPLE)
            .instagramAudienceDescription(UPDATED_INSTAGRAM_AUDIENCE_DESCRIPTION);
        return campaign;
    }

    @BeforeEach
    public void initTest() {
        campaign = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampaign() throws Exception {
        int databaseSizeBeforeCreate = campaignRepository.findAll().size();

        // Create the Campaign
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);
        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isCreated());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeCreate + 1);
        Campaign testCampaign = campaignList.get(campaignList.size() - 1);
        assertThat(testCampaign.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testCampaign.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testCampaign.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testCampaign.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testCampaign.getProjectDetails()).isEqualTo(DEFAULT_PROJECT_DETAILS);
        assertThat(testCampaign.getBrandFirstTrait()).isEqualTo(DEFAULT_BRAND_FIRST_TRAIT);
        assertThat(testCampaign.getBrandSecondTrait()).isEqualTo(DEFAULT_BRAND_SECOND_TRAIT);
        assertThat(testCampaign.getBrandThirdTrait()).isEqualTo(DEFAULT_BRAND_THIRD_TRAIT);
        assertThat(testCampaign.getBrandGoals()).isEqualTo(DEFAULT_BRAND_GOALS);
        assertThat(testCampaign.getSample()).isEqualTo(DEFAULT_SAMPLE);
        assertThat(testCampaign.getInstagramAudienceDescription()).isEqualTo(DEFAULT_INSTAGRAM_AUDIENCE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCampaignWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campaignRepository.findAll().size();

        // Create the Campaign with an existing ID
        campaign.setId(1L);
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setUuid(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setCategory(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setActivity(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setProjectName(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setProjectDetails(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrandFirstTraitIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setBrandFirstTrait(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrandSecondTraitIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setBrandSecondTrait(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrandThirdTraitIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setBrandThirdTrait(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrandGoalsIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setBrandGoals(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSampleIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setSample(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstagramAudienceDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setInstagramAudienceDescription(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCampaigns() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaignList
        restCampaignMockMvc.perform(get("/api/campaigns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaign.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].projectDetails").value(hasItem(DEFAULT_PROJECT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].brandFirstTrait").value(hasItem(DEFAULT_BRAND_FIRST_TRAIT.toString())))
            .andExpect(jsonPath("$.[*].brandSecondTrait").value(hasItem(DEFAULT_BRAND_SECOND_TRAIT.toString())))
            .andExpect(jsonPath("$.[*].brandThirdTrait").value(hasItem(DEFAULT_BRAND_THIRD_TRAIT.toString())))
            .andExpect(jsonPath("$.[*].brandGoals").value(hasItem(DEFAULT_BRAND_GOALS.toString())))
            .andExpect(jsonPath("$.[*].sample").value(hasItem(DEFAULT_SAMPLE.toString())))
            .andExpect(jsonPath("$.[*].instagramAudienceDescription").value(hasItem(DEFAULT_INSTAGRAM_AUDIENCE_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get the campaign
        restCampaignMockMvc.perform(get("/api/campaigns/{id}", campaign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campaign.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY.toString()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.projectDetails").value(DEFAULT_PROJECT_DETAILS.toString()))
            .andExpect(jsonPath("$.brandFirstTrait").value(DEFAULT_BRAND_FIRST_TRAIT.toString()))
            .andExpect(jsonPath("$.brandSecondTrait").value(DEFAULT_BRAND_SECOND_TRAIT.toString()))
            .andExpect(jsonPath("$.brandThirdTrait").value(DEFAULT_BRAND_THIRD_TRAIT.toString()))
            .andExpect(jsonPath("$.brandGoals").value(DEFAULT_BRAND_GOALS.toString()))
            .andExpect(jsonPath("$.sample").value(DEFAULT_SAMPLE.toString()))
            .andExpect(jsonPath("$.instagramAudienceDescription").value(DEFAULT_INSTAGRAM_AUDIENCE_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCampaign() throws Exception {
        // Get the campaign
        restCampaignMockMvc.perform(get("/api/campaigns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        int databaseSizeBeforeUpdate = campaignRepository.findAll().size();

        // Update the campaign
        Campaign updatedCampaign = campaignRepository.findById(campaign.getId()).get();
        // Disconnect from session so that the updates on updatedCampaign are not directly saved in db
        em.detach(updatedCampaign);
        updatedCampaign
            .uuid(UPDATED_UUID)
            .category(UPDATED_CATEGORY)
            .activity(UPDATED_ACTIVITY)
            .projectName(UPDATED_PROJECT_NAME)
            .projectDetails(UPDATED_PROJECT_DETAILS)
            .brandFirstTrait(UPDATED_BRAND_FIRST_TRAIT)
            .brandSecondTrait(UPDATED_BRAND_SECOND_TRAIT)
            .brandThirdTrait(UPDATED_BRAND_THIRD_TRAIT)
            .brandGoals(UPDATED_BRAND_GOALS)
            .sample(UPDATED_SAMPLE)
            .instagramAudienceDescription(UPDATED_INSTAGRAM_AUDIENCE_DESCRIPTION);
        CampaignDTO campaignDTO = campaignMapper.toDto(updatedCampaign);

        restCampaignMockMvc.perform(put("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isOk());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeUpdate);
        Campaign testCampaign = campaignList.get(campaignList.size() - 1);
        assertThat(testCampaign.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testCampaign.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCampaign.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testCampaign.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testCampaign.getProjectDetails()).isEqualTo(UPDATED_PROJECT_DETAILS);
        assertThat(testCampaign.getBrandFirstTrait()).isEqualTo(UPDATED_BRAND_FIRST_TRAIT);
        assertThat(testCampaign.getBrandSecondTrait()).isEqualTo(UPDATED_BRAND_SECOND_TRAIT);
        assertThat(testCampaign.getBrandThirdTrait()).isEqualTo(UPDATED_BRAND_THIRD_TRAIT);
        assertThat(testCampaign.getBrandGoals()).isEqualTo(UPDATED_BRAND_GOALS);
        assertThat(testCampaign.getSample()).isEqualTo(UPDATED_SAMPLE);
        assertThat(testCampaign.getInstagramAudienceDescription()).isEqualTo(UPDATED_INSTAGRAM_AUDIENCE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCampaign() throws Exception {
        int databaseSizeBeforeUpdate = campaignRepository.findAll().size();

        // Create the Campaign
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampaignMockMvc.perform(put("/api/campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Campaign in the database
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        int databaseSizeBeforeDelete = campaignRepository.findAll().size();

        // Delete the campaign
        restCampaignMockMvc.perform(delete("/api/campaigns/{id}", campaign.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Campaign.class);
        Campaign campaign1 = new Campaign();
        campaign1.setId(1L);
        Campaign campaign2 = new Campaign();
        campaign2.setId(campaign1.getId());
        assertThat(campaign1).isEqualTo(campaign2);
        campaign2.setId(2L);
        assertThat(campaign1).isNotEqualTo(campaign2);
        campaign1.setId(null);
        assertThat(campaign1).isNotEqualTo(campaign2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignDTO.class);
        CampaignDTO campaignDTO1 = new CampaignDTO();
        campaignDTO1.setId(1L);
        CampaignDTO campaignDTO2 = new CampaignDTO();
        assertThat(campaignDTO1).isNotEqualTo(campaignDTO2);
        campaignDTO2.setId(campaignDTO1.getId());
        assertThat(campaignDTO1).isEqualTo(campaignDTO2);
        campaignDTO2.setId(2L);
        assertThat(campaignDTO1).isNotEqualTo(campaignDTO2);
        campaignDTO1.setId(null);
        assertThat(campaignDTO1).isNotEqualTo(campaignDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(campaignMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(campaignMapper.fromId(null)).isNull();
    }
}
