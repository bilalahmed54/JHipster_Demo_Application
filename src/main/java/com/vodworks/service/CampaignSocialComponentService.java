package com.vodworks.service;

import com.vodworks.service.dto.CampaignSocialComponentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vodworks.domain.CampaignSocialComponent}.
 */
public interface CampaignSocialComponentService {

    /**
     * Save a campaignSocialComponent.
     *
     * @param campaignSocialComponentDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignSocialComponentDTO save(CampaignSocialComponentDTO campaignSocialComponentDTO);

    /**
     * Get all the campaignSocialComponents.
     *
     * @return the list of entities.
     */
    List<CampaignSocialComponentDTO> findAll();


    /**
     * Get the "id" campaignSocialComponent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignSocialComponentDTO> findOne(Long id);

    /**
     * Delete the "id" campaignSocialComponent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
