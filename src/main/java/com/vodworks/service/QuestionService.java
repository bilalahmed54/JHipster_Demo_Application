package com.vodworks.service;

import com.vodworks.domain.Campaign;
import com.vodworks.service.dto.QuestionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vodworks.domain.Question}.
 */
public interface QuestionService {

    public void createCampaignQuestions(Campaign campaign);
}
