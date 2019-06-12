package com.vodworks.service;

import com.vodworks.service.dto.CampaignCompBrandDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vodworks.domain.CampaignCompBrand}.
 */
public interface CampaignCompBrandService {

    /**
     * Save a campaignCompBrand.
     *
     * @param campaignCompBrandDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignCompBrandDTO save(CampaignCompBrandDTO campaignCompBrandDTO, MultipartFile imageFile) throws IOException;

    /**
     * Get all the campaignCompBrands.
     *
     * @return the list of entities.
     */
    List<CampaignCompBrandDTO> findAll();


    /**
     * Get the "id" campaignCompBrand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignCompBrandDTO> findOne(Long id);

    /**
     * Delete the "id" campaignCompBrand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
