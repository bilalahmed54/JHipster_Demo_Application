package com.vodworks.service.impl;

import com.vodworks.domain.Campaign;
import com.vodworks.repository.CampaignRepository;
import com.vodworks.service.CampaignCompBrandService;
import com.vodworks.domain.CampaignCompBrand;
import com.vodworks.repository.CampaignCompBrandRepository;
import com.vodworks.service.dto.CampaignCompBrandDTO;
import com.vodworks.service.mapper.CampaignCompBrandMapper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private String campaignImagesUploadDir = System.getProperty("user.home") + File.separator + "files" + File.separator;

    private final CampaignCompBrandRepository campaignCompBrandRepository;

    private final CampaignRepository campaignRepository;

    private final CampaignCompBrandMapper campaignCompBrandMapper;

    public CampaignCompBrandServiceImpl(CampaignCompBrandRepository campaignCompBrandRepository, CampaignRepository campaignRepository, CampaignCompBrandMapper campaignCompBrandMapper) {
        this.campaignCompBrandRepository = campaignCompBrandRepository;
        this.campaignRepository = campaignRepository;
        this.campaignCompBrandMapper = campaignCompBrandMapper;
    }

    /**
     * Save a campaignCompBrand.
     *
     * @param campaignCompBrandDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CampaignCompBrandDTO save(CampaignCompBrandDTO campaignCompBrandDTO) throws IOException {
        log.debug("Request to save CampaignCompBrand : {}", campaignCompBrandDTO);

        CampaignCompBrand campaignCompBrand = campaignCompBrandMapper.toEntity(campaignCompBrandDTO);

        Campaign campaign = this.campaignRepository.getOne(campaignCompBrandDTO.getCampaignId());
        campaignCompBrand.setCampaign(campaign);

        campaignCompBrand = campaignCompBrandRepository.save(campaignCompBrand);

        String imagesDir = campaignImagesUploadDir + campaign.getUuid();

        Path directoryPath = Paths.get(imagesDir);

        Files.createDirectories(directoryPath);

        String contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(campaignCompBrand.getImageFile()));

        String fileType = contentType.replace("image/", "");

        String modifiedFileName = System.currentTimeMillis() + "." + fileType;

        String destinationFilePathStr = imagesDir + File.separator + modifiedFileName;
        //Path destinationFilePath = Paths.get(destinationFilePathStr);

        FileUtils.writeByteArrayToFile(new File(destinationFilePathStr), campaignCompBrand.getImageFile());

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
