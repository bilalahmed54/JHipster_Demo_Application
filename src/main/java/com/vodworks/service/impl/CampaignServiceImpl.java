package com.vodworks.service.impl;

import com.vodworks.domain.Campaign;
import com.vodworks.domain.Question;
import com.vodworks.repository.CampaignRepository;
import com.vodworks.repository.QuestionRepository;
import com.vodworks.service.CampaignService;
import com.vodworks.service.QuestionService;
import com.vodworks.service.dto.CampaignDTO;
import com.vodworks.service.dto.CampaignQuestionDTO;
import com.vodworks.service.mapper.CampaignMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Campaign}.
 */
@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {

    private final Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

    private final CampaignRepository campaignRepository;

    private final QuestionRepository questionRepository;

    private final QuestionService questionService;

    private final CampaignMapper campaignMapper;

    public CampaignServiceImpl(CampaignRepository campaignRepository, QuestionRepository questionRepository, QuestionService questionService, CampaignMapper campaignMapper) {
        this.campaignRepository = campaignRepository;
        this.questionRepository = questionRepository;
        this.questionService = questionService;
        this.campaignMapper = campaignMapper;
    }

    /**
     * Save a campaign.
     *
     * @param campaignDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CampaignDTO save(CampaignDTO campaignDTO) {
        log.debug("Request to save Campaign : {}", campaignDTO);
        Campaign campaign = campaignMapper.toEntity(campaignDTO);
        campaign.setUuid(UUID.randomUUID().toString());
        campaign = campaignRepository.save(campaign);
        return campaignMapper.toDto(campaign);
    }

    @Override
    public CampaignQuestionDTO get(Long campaignId) {

        log.debug("Request to Get Campaign : {}", campaignId);
        Campaign campaign = campaignRepository.getOne(campaignId);

        CampaignQuestionDTO campaignQuestionDTO = new CampaignQuestionDTO();
        campaignQuestionDTO.setCampaign(campaign);

        List<Question> questionList = this.questionRepository.findAllByCampaign(campaign);

        if(questionList == null || questionList.size() == 0) {
            this.questionService.createCampaignQuestions(campaign);
            questionList = this.questionRepository.findAllByCampaign(campaign);
        }

        campaignQuestionDTO.populateQuestions(questionList);

        return campaignQuestionDTO;
    }

    /**
     * Get all the campaigns.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CampaignDTO> findAll() {
        log.debug("Request to get all Campaigns");
        return campaignRepository.findAll().stream()
            .map(campaignMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one campaign by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignDTO> findOne(Long id) {
        log.debug("Request to get Campaign : {}", id);
        return campaignRepository.findById(id)
            .map(campaignMapper::toDto);
    }

    /**
     * Delete the campaign by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Campaign : {}", id);
        campaignRepository.deleteById(id);
    }
}
