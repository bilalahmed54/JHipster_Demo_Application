package com.vodworks.web.rest;

import com.vodworks.JhipsterDemoAppApp;
import com.vodworks.domain.CampaignCompBrand;
import com.vodworks.repository.CampaignCompBrandRepository;
import com.vodworks.service.CampaignCompBrandService;
import com.vodworks.service.dto.CampaignCompBrandDTO;
import com.vodworks.service.mapper.CampaignCompBrandMapper;
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
 * Integration tests for the {@Link CampaignCompBrandResource} REST controller.
 */
@SpringBootTest(classes = JhipsterDemoAppApp.class)
public class CampaignCompBrandResourceIT {

    private static final String DEFAULT_IMAGE_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_FILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_COMP_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMP_BRAND_NAME = "BBBBBBBBBB";

    @Autowired
    private CampaignCompBrandRepository campaignCompBrandRepository;

    @Autowired
    private CampaignCompBrandMapper campaignCompBrandMapper;

    @Autowired
    private CampaignCompBrandService campaignCompBrandService;

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

    private MockMvc restCampaignCompBrandMockMvc;

    private CampaignCompBrand campaignCompBrand;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CampaignCompBrandResource campaignCompBrandResource = new CampaignCompBrandResource(campaignCompBrandService);
        this.restCampaignCompBrandMockMvc = MockMvcBuilders.standaloneSetup(campaignCompBrandResource)
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
    public static CampaignCompBrand createEntity(EntityManager em) {
        CampaignCompBrand campaignCompBrand = new CampaignCompBrand()
            .imageFileUrl(DEFAULT_IMAGE_FILE_URL)
            .compBrandName(DEFAULT_COMP_BRAND_NAME);
        return campaignCompBrand;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampaignCompBrand createUpdatedEntity(EntityManager em) {
        CampaignCompBrand campaignCompBrand = new CampaignCompBrand()
            .imageFileUrl(UPDATED_IMAGE_FILE_URL)
            .compBrandName(UPDATED_COMP_BRAND_NAME);
        return campaignCompBrand;
    }

    @BeforeEach
    public void initTest() {
        campaignCompBrand = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampaignCompBrand() throws Exception {
        int databaseSizeBeforeCreate = campaignCompBrandRepository.findAll().size();

        // Create the CampaignCompBrand
        CampaignCompBrandDTO campaignCompBrandDTO = campaignCompBrandMapper.toDto(campaignCompBrand);
        restCampaignCompBrandMockMvc.perform(post("/api/campaign-comp-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignCompBrandDTO)))
            .andExpect(status().isCreated());

        // Validate the CampaignCompBrand in the database
        List<CampaignCompBrand> campaignCompBrandList = campaignCompBrandRepository.findAll();
        assertThat(campaignCompBrandList).hasSize(databaseSizeBeforeCreate + 1);
        CampaignCompBrand testCampaignCompBrand = campaignCompBrandList.get(campaignCompBrandList.size() - 1);
        assertThat(testCampaignCompBrand.getImageFileUrl()).isEqualTo(DEFAULT_IMAGE_FILE_URL);
        assertThat(testCampaignCompBrand.getCompBrandName()).isEqualTo(DEFAULT_COMP_BRAND_NAME);
    }

    @Test
    @Transactional
    public void createCampaignCompBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campaignCompBrandRepository.findAll().size();

        // Create the CampaignCompBrand with an existing ID
        campaignCompBrand.setId(1L);
        CampaignCompBrandDTO campaignCompBrandDTO = campaignCompBrandMapper.toDto(campaignCompBrand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaignCompBrandMockMvc.perform(post("/api/campaign-comp-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignCompBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignCompBrand in the database
        List<CampaignCompBrand> campaignCompBrandList = campaignCompBrandRepository.findAll();
        assertThat(campaignCompBrandList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkImageFileUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignCompBrandRepository.findAll().size();
        // set the field null
        campaignCompBrand.setImageFileUrl(null);

        // Create the CampaignCompBrand, which fails.
        CampaignCompBrandDTO campaignCompBrandDTO = campaignCompBrandMapper.toDto(campaignCompBrand);

        restCampaignCompBrandMockMvc.perform(post("/api/campaign-comp-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignCompBrandDTO)))
            .andExpect(status().isBadRequest());

        List<CampaignCompBrand> campaignCompBrandList = campaignCompBrandRepository.findAll();
        assertThat(campaignCompBrandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompBrandNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignCompBrandRepository.findAll().size();
        // set the field null
        campaignCompBrand.setCompBrandName(null);

        // Create the CampaignCompBrand, which fails.
        CampaignCompBrandDTO campaignCompBrandDTO = campaignCompBrandMapper.toDto(campaignCompBrand);

        restCampaignCompBrandMockMvc.perform(post("/api/campaign-comp-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignCompBrandDTO)))
            .andExpect(status().isBadRequest());

        List<CampaignCompBrand> campaignCompBrandList = campaignCompBrandRepository.findAll();
        assertThat(campaignCompBrandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCampaignCompBrands() throws Exception {
        // Initialize the database
        campaignCompBrandRepository.saveAndFlush(campaignCompBrand);

        // Get all the campaignCompBrandList
        restCampaignCompBrandMockMvc.perform(get("/api/campaign-comp-brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaignCompBrand.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageFileUrl").value(hasItem(DEFAULT_IMAGE_FILE_URL.toString())))
            .andExpect(jsonPath("$.[*].compBrandName").value(hasItem(DEFAULT_COMP_BRAND_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getCampaignCompBrand() throws Exception {
        // Initialize the database
        campaignCompBrandRepository.saveAndFlush(campaignCompBrand);

        // Get the campaignCompBrand
        restCampaignCompBrandMockMvc.perform(get("/api/campaign-comp-brands/{id}", campaignCompBrand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(campaignCompBrand.getId().intValue()))
            .andExpect(jsonPath("$.imageFileUrl").value(DEFAULT_IMAGE_FILE_URL.toString()))
            .andExpect(jsonPath("$.compBrandName").value(DEFAULT_COMP_BRAND_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCampaignCompBrand() throws Exception {
        // Get the campaignCompBrand
        restCampaignCompBrandMockMvc.perform(get("/api/campaign-comp-brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampaignCompBrand() throws Exception {
        // Initialize the database
        campaignCompBrandRepository.saveAndFlush(campaignCompBrand);

        int databaseSizeBeforeUpdate = campaignCompBrandRepository.findAll().size();

        // Update the campaignCompBrand
        CampaignCompBrand updatedCampaignCompBrand = campaignCompBrandRepository.findById(campaignCompBrand.getId()).get();
        // Disconnect from session so that the updates on updatedCampaignCompBrand are not directly saved in db
        em.detach(updatedCampaignCompBrand);
        updatedCampaignCompBrand
            .imageFileUrl(UPDATED_IMAGE_FILE_URL)
            .compBrandName(UPDATED_COMP_BRAND_NAME);
        CampaignCompBrandDTO campaignCompBrandDTO = campaignCompBrandMapper.toDto(updatedCampaignCompBrand);

        restCampaignCompBrandMockMvc.perform(put("/api/campaign-comp-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignCompBrandDTO)))
            .andExpect(status().isOk());

        // Validate the CampaignCompBrand in the database
        List<CampaignCompBrand> campaignCompBrandList = campaignCompBrandRepository.findAll();
        assertThat(campaignCompBrandList).hasSize(databaseSizeBeforeUpdate);
        CampaignCompBrand testCampaignCompBrand = campaignCompBrandList.get(campaignCompBrandList.size() - 1);
        assertThat(testCampaignCompBrand.getImageFileUrl()).isEqualTo(UPDATED_IMAGE_FILE_URL);
        assertThat(testCampaignCompBrand.getCompBrandName()).isEqualTo(UPDATED_COMP_BRAND_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCampaignCompBrand() throws Exception {
        int databaseSizeBeforeUpdate = campaignCompBrandRepository.findAll().size();

        // Create the CampaignCompBrand
        CampaignCompBrandDTO campaignCompBrandDTO = campaignCompBrandMapper.toDto(campaignCompBrand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampaignCompBrandMockMvc.perform(put("/api/campaign-comp-brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(campaignCompBrandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignCompBrand in the database
        List<CampaignCompBrand> campaignCompBrandList = campaignCompBrandRepository.findAll();
        assertThat(campaignCompBrandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampaignCompBrand() throws Exception {
        // Initialize the database
        campaignCompBrandRepository.saveAndFlush(campaignCompBrand);

        int databaseSizeBeforeDelete = campaignCompBrandRepository.findAll().size();

        // Delete the campaignCompBrand
        restCampaignCompBrandMockMvc.perform(delete("/api/campaign-comp-brands/{id}", campaignCompBrand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CampaignCompBrand> campaignCompBrandList = campaignCompBrandRepository.findAll();
        assertThat(campaignCompBrandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignCompBrand.class);
        CampaignCompBrand campaignCompBrand1 = new CampaignCompBrand();
        campaignCompBrand1.setId(1L);
        CampaignCompBrand campaignCompBrand2 = new CampaignCompBrand();
        campaignCompBrand2.setId(campaignCompBrand1.getId());
        assertThat(campaignCompBrand1).isEqualTo(campaignCompBrand2);
        campaignCompBrand2.setId(2L);
        assertThat(campaignCompBrand1).isNotEqualTo(campaignCompBrand2);
        campaignCompBrand1.setId(null);
        assertThat(campaignCompBrand1).isNotEqualTo(campaignCompBrand2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampaignCompBrandDTO.class);
        CampaignCompBrandDTO campaignCompBrandDTO1 = new CampaignCompBrandDTO();
        campaignCompBrandDTO1.setId(1L);
        CampaignCompBrandDTO campaignCompBrandDTO2 = new CampaignCompBrandDTO();
        assertThat(campaignCompBrandDTO1).isNotEqualTo(campaignCompBrandDTO2);
        campaignCompBrandDTO2.setId(campaignCompBrandDTO1.getId());
        assertThat(campaignCompBrandDTO1).isEqualTo(campaignCompBrandDTO2);
        campaignCompBrandDTO2.setId(2L);
        assertThat(campaignCompBrandDTO1).isNotEqualTo(campaignCompBrandDTO2);
        campaignCompBrandDTO1.setId(null);
        assertThat(campaignCompBrandDTO1).isNotEqualTo(campaignCompBrandDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(campaignCompBrandMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(campaignCompBrandMapper.fromId(null)).isNull();
    }
}
