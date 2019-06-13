package com.vodworks.service;

import com.vodworks.service.dto.CampaignDTO;
import com.vodworks.service.dto.CampaignListDTO;
import com.vodworks.service.dto.CampaignQuestionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vodworks.domain.Campaign}.
 */
public interface CampaignService {

    /**
     * Save a campaign.
     *
     * @param campaignDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignDTO save(CampaignDTO campaignDTO);

    /**
     * get the "id" campaign.
     *
     * @param id the id of the entity.
     */
    CampaignQuestionDTO get(Long id);

    /**
     * Get all the campaigns.
     *
     * @return the list of entities.
     */
    CampaignListDTO findAll();

    /**
     * Get the "id" campaign.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignDTO> findOne(Long id);

    /**
     * Delete the "id" campaign.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
