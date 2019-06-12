package com.vodworks.web.rest;

import com.vodworks.JhipsterBaseAppApp;
import com.vodworks.domain.Respondent;
import com.vodworks.repository.RespondentRepository;
import com.vodworks.service.RespondentService;
import com.vodworks.service.dto.RespondentDTO;
import com.vodworks.service.mapper.RespondentMapper;
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

import com.vodworks.domain.enumeration.UserTypes;
/**
 * Integration tests for the {@Link RespondentResource} REST controller.
 */
@SpringBootTest(classes = JhipsterBaseAppApp.class)
public class RespondentResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final UserTypes DEFAULT_USER_TYPE = UserTypes.TEST;
    private static final UserTypes UPDATED_USER_TYPE = UserTypes.CONTROL;

    private static final Boolean DEFAULT_IS_VIDEO_RECORDED = false;
    private static final Boolean UPDATED_IS_VIDEO_RECORDED = true;

    @Autowired
    private RespondentRepository respondentRepository;

    @Autowired
    private RespondentMapper respondentMapper;

    @Autowired
    private RespondentService respondentService;

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

    private MockMvc restRespondentMockMvc;

    private Respondent respondent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RespondentResource respondentResource = new RespondentResource(respondentService);
        this.restRespondentMockMvc = MockMvcBuilders.standaloneSetup(respondentResource)
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
    public static Respondent createEntity(EntityManager em) {
        Respondent respondent = new Respondent()
            .uuid(DEFAULT_UUID)
            .userType(DEFAULT_USER_TYPE)
            .isVideoRecorded(DEFAULT_IS_VIDEO_RECORDED);
        return respondent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Respondent createUpdatedEntity(EntityManager em) {
        Respondent respondent = new Respondent()
            .uuid(UPDATED_UUID)
            .userType(UPDATED_USER_TYPE)
            .isVideoRecorded(UPDATED_IS_VIDEO_RECORDED);
        return respondent;
    }

    @BeforeEach
    public void initTest() {
        respondent = createEntity(em);
    }

    @Test
    @Transactional
    public void createRespondent() throws Exception {
        int databaseSizeBeforeCreate = respondentRepository.findAll().size();

        // Create the Respondent
        RespondentDTO respondentDTO = respondentMapper.toDto(respondent);
        restRespondentMockMvc.perform(post("/api/respondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respondentDTO)))
            .andExpect(status().isCreated());

        // Validate the Respondent in the database
        List<Respondent> respondentList = respondentRepository.findAll();
        assertThat(respondentList).hasSize(databaseSizeBeforeCreate + 1);
        Respondent testRespondent = respondentList.get(respondentList.size() - 1);
        assertThat(testRespondent.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testRespondent.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testRespondent.isIsVideoRecorded()).isEqualTo(DEFAULT_IS_VIDEO_RECORDED);
    }

    @Test
    @Transactional
    public void createRespondentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = respondentRepository.findAll().size();

        // Create the Respondent with an existing ID
        respondent.setId(1L);
        RespondentDTO respondentDTO = respondentMapper.toDto(respondent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRespondentMockMvc.perform(post("/api/respondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respondentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Respondent in the database
        List<Respondent> respondentList = respondentRepository.findAll();
        assertThat(respondentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = respondentRepository.findAll().size();
        // set the field null
        respondent.setUuid(null);

        // Create the Respondent, which fails.
        RespondentDTO respondentDTO = respondentMapper.toDto(respondent);

        restRespondentMockMvc.perform(post("/api/respondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respondentDTO)))
            .andExpect(status().isBadRequest());

        List<Respondent> respondentList = respondentRepository.findAll();
        assertThat(respondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = respondentRepository.findAll().size();
        // set the field null
        respondent.setUserType(null);

        // Create the Respondent, which fails.
        RespondentDTO respondentDTO = respondentMapper.toDto(respondent);

        restRespondentMockMvc.perform(post("/api/respondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respondentDTO)))
            .andExpect(status().isBadRequest());

        List<Respondent> respondentList = respondentRepository.findAll();
        assertThat(respondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsVideoRecordedIsRequired() throws Exception {
        int databaseSizeBeforeTest = respondentRepository.findAll().size();
        // set the field null
        respondent.setIsVideoRecorded(null);

        // Create the Respondent, which fails.
        RespondentDTO respondentDTO = respondentMapper.toDto(respondent);

        restRespondentMockMvc.perform(post("/api/respondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respondentDTO)))
            .andExpect(status().isBadRequest());

        List<Respondent> respondentList = respondentRepository.findAll();
        assertThat(respondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRespondents() throws Exception {
        // Initialize the database
        respondentRepository.saveAndFlush(respondent);

        // Get all the respondentList
        restRespondentMockMvc.perform(get("/api/respondents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(respondent.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isVideoRecorded").value(hasItem(DEFAULT_IS_VIDEO_RECORDED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRespondent() throws Exception {
        // Initialize the database
        respondentRepository.saveAndFlush(respondent);

        // Get the respondent
        restRespondentMockMvc.perform(get("/api/respondents/{id}", respondent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(respondent.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.isVideoRecorded").value(DEFAULT_IS_VIDEO_RECORDED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRespondent() throws Exception {
        // Get the respondent
        restRespondentMockMvc.perform(get("/api/respondents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRespondent() throws Exception {
        // Initialize the database
        respondentRepository.saveAndFlush(respondent);

        int databaseSizeBeforeUpdate = respondentRepository.findAll().size();

        // Update the respondent
        Respondent updatedRespondent = respondentRepository.findById(respondent.getId()).get();
        // Disconnect from session so that the updates on updatedRespondent are not directly saved in db
        em.detach(updatedRespondent);
        updatedRespondent
            .uuid(UPDATED_UUID)
            .userType(UPDATED_USER_TYPE)
            .isVideoRecorded(UPDATED_IS_VIDEO_RECORDED);
        RespondentDTO respondentDTO = respondentMapper.toDto(updatedRespondent);

        restRespondentMockMvc.perform(put("/api/respondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respondentDTO)))
            .andExpect(status().isOk());

        // Validate the Respondent in the database
        List<Respondent> respondentList = respondentRepository.findAll();
        assertThat(respondentList).hasSize(databaseSizeBeforeUpdate);
        Respondent testRespondent = respondentList.get(respondentList.size() - 1);
        assertThat(testRespondent.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testRespondent.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testRespondent.isIsVideoRecorded()).isEqualTo(UPDATED_IS_VIDEO_RECORDED);
    }

    @Test
    @Transactional
    public void updateNonExistingRespondent() throws Exception {
        int databaseSizeBeforeUpdate = respondentRepository.findAll().size();

        // Create the Respondent
        RespondentDTO respondentDTO = respondentMapper.toDto(respondent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRespondentMockMvc.perform(put("/api/respondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respondentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Respondent in the database
        List<Respondent> respondentList = respondentRepository.findAll();
        assertThat(respondentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRespondent() throws Exception {
        // Initialize the database
        respondentRepository.saveAndFlush(respondent);

        int databaseSizeBeforeDelete = respondentRepository.findAll().size();

        // Delete the respondent
        restRespondentMockMvc.perform(delete("/api/respondents/{id}", respondent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Respondent> respondentList = respondentRepository.findAll();
        assertThat(respondentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Respondent.class);
        Respondent respondent1 = new Respondent();
        respondent1.setId(1L);
        Respondent respondent2 = new Respondent();
        respondent2.setId(respondent1.getId());
        assertThat(respondent1).isEqualTo(respondent2);
        respondent2.setId(2L);
        assertThat(respondent1).isNotEqualTo(respondent2);
        respondent1.setId(null);
        assertThat(respondent1).isNotEqualTo(respondent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RespondentDTO.class);
        RespondentDTO respondentDTO1 = new RespondentDTO();
        respondentDTO1.setId(1L);
        RespondentDTO respondentDTO2 = new RespondentDTO();
        assertThat(respondentDTO1).isNotEqualTo(respondentDTO2);
        respondentDTO2.setId(respondentDTO1.getId());
        assertThat(respondentDTO1).isEqualTo(respondentDTO2);
        respondentDTO2.setId(2L);
        assertThat(respondentDTO1).isNotEqualTo(respondentDTO2);
        respondentDTO1.setId(null);
        assertThat(respondentDTO1).isNotEqualTo(respondentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(respondentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(respondentMapper.fromId(null)).isNull();
    }
}
