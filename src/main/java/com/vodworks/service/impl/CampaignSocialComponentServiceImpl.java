package com.vodworks.service.impl;

import com.vodworks.service.CampaignSocialComponentService;
import com.vodworks.domain.CampaignSocialComponent;
import com.vodworks.repository.CampaignSocialComponentRepository;
import com.vodworks.service.dto.CampaignSocialComponentDTO;
import com.vodworks.service.mapper.CampaignSocialComponentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CampaignSocialComponent}.
 */
@Service
@Transactional
public class CampaignSocialComponentServiceImpl implements CampaignSocialComponentService {

    private final Logger log = LoggerFactory.getLogger(CampaignSocialComponentServiceImpl.class);

    private final CampaignSocialComponentRepository campaignSocialComponentRepository;

    private final CampaignSocialComponentMapper campaignSocialComponentMapper;

    public CampaignSocialComponentServiceImpl(CampaignSocialComponentRepository campaignSocialComponentRepository, CampaignSocialComponentMapper campaignSocialComponentMapper) {
        this.campaignSocialComponentRepository = campaignSocialComponentRepository;
        this.campaignSocialComponentMapper = campaignSocialComponentMapper;
    }

    /**
     * Save a campaignSocialComponent.
     *
     * @param campaignSocialComponentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CampaignSocialComponentDTO save(CampaignSocialComponentDTO campaignSocialComponentDTO) {
        log.debug("Request to save CampaignSocialComponent : {}", campaignSocialComponentDTO);
        CampaignSocialComponent campaignSocialComponent = campaignSocialComponentMapper.toEntity(campaignSocialComponentDTO);
        campaignSocialComponent = campaignSocialComponentRepository.save(campaignSocialComponent);
        return campaignSocialComponentMapper.toDto(campaignSocialComponent);
    }

    /**
     * Get all the campaignSocialComponents.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CampaignSocialComponentDTO> findAll() {
        log.debug("Request to get all CampaignSocialComponents");
        return campaignSocialComponentRepository.findAll().stream()
            .map(campaignSocialComponentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one campaignSocialComponent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignSocialComponentDTO> findOne(Long id) {
        log.debug("Request to get CampaignSocialComponent : {}", id);
        return campaignSocialComponentRepository.findById(id)
            .map(campaignSocialComponentMapper::toDto);
    }

    /**
     * Delete the campaignSocialComponent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CampaignSocialComponent : {}", id);
        campaignSocialComponentRepository.deleteById(id);
    }
}
