package com.vodworks.service.impl;

import com.vodworks.service.CampaignCompBrandService;
import com.vodworks.domain.CampaignCompBrand;
import com.vodworks.repository.CampaignCompBrandRepository;
import com.vodworks.service.dto.CampaignCompBrandDTO;
import com.vodworks.service.mapper.CampaignCompBrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CampaignCompBrand}.
 */
@Service
@Transactional
public class CampaignCompBrandServiceImpl implements CampaignCompBrandService {

    private final Logger log = LoggerFactory.getLogger(CampaignCompBrandServiceImpl.class);

    private final CampaignCompBrandRepository campaignCompBrandRepository;

    private final CampaignCompBrandMapper campaignCompBrandMapper;

    public CampaignCompBrandServiceImpl(CampaignCompBrandRepository campaignCompBrandRepository, CampaignCompBrandMapper campaignCompBrandMapper) {
        this.campaignCompBrandRepository = campaignCompBrandRepository;
        this.campaignCompBrandMapper = campaignCompBrandMapper;
    }

    /**
     * Save a campaignCompBrand.
     *
     * @param campaignCompBrandDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CampaignCompBrandDTO save(CampaignCompBrandDTO campaignCompBrandDTO) {
        log.debug("Request to save CampaignCompBrand : {}", campaignCompBrandDTO);
        CampaignCompBrand campaignCompBrand = campaignCompBrandMapper.toEntity(campaignCompBrandDTO);
        campaignCompBrand = campaignCompBrandRepository.save(campaignCompBrand);
        return campaignCompBrandMapper.toDto(campaignCompBrand);
    }

    /**
     * Get all the campaignCompBrands.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CampaignCompBrandDTO> findAll() {
        log.debug("Request to get all CampaignCompBrands");
        return campaignCompBrandRepository.findAll().stream()
            .map(campaignCompBrandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one campaignCompBrand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CampaignCompBrandDTO> findOne(Long id) {
        log.debug("Request to get CampaignCompBrand : {}", id);
        return campaignCompBrandRepository.findById(id)
            .map(campaignCompBrandMapper::toDto);
    }

    /**
     * Delete the campaignCompBrand by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CampaignCompBrand : {}", id);
        campaignCompBrandRepository.deleteById(id);
    }
}
