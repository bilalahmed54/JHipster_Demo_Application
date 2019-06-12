package com.vodworks.service.impl;

import com.vodworks.domain.Campaign;
import com.vodworks.domain.CampaignCompBrand;
import com.vodworks.repository.CampaignCompBrandRepository;
import com.vodworks.repository.CampaignRepository;
import com.vodworks.service.CampaignCompBrandService;
import com.vodworks.service.dto.CampaignCompBrandDTO;
import com.vodworks.service.mapper.CampaignCompBrandMapper;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    private final CampaignRepository campaignRepository;

    private final CampaignCompBrandRepository campaignCompBrandRepository;

    private final CampaignCompBrandMapper campaignCompBrandMapper;

    public CampaignCompBrandServiceImpl(CampaignRepository campaignRepository, CampaignCompBrandRepository campaignCompBrandRepository, CampaignCompBrandMapper campaignCompBrandMapper) {
        this.campaignRepository = campaignRepository;
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
    public CampaignCompBrandDTO save(CampaignCompBrandDTO campaignCompBrandDTO, MultipartFile imageFile) throws IOException {

        log.debug("Request to save CampaignCompBrand : {}", campaignCompBrandDTO);
        CampaignCompBrand campaignCompBrand = campaignCompBrandMapper.toEntity(campaignCompBrandDTO);

        Campaign campaign = this.campaignRepository.getOne(campaignCompBrandDTO.getCampaignId());
        campaignCompBrand.setCampaign(campaign);

        // Creating directory t place campaign files on the server
        String imagesDir = campaignImagesUploadDir + campaign.getUuid();

        Path directoryPath = Paths.get(imagesDir);

        Files.createDirectories(directoryPath);

        String imageFileUrl = "";

        try (InputStream imageFileInputStream = imageFile.getInputStream()) {

            String fileName = imageFile.getOriginalFilename();
            String modifiedFileName = System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);

            String destinationFilePathStr = imagesDir + File.separator + modifiedFileName;
            Path destinationFilePath = Paths.get(destinationFilePathStr);

            Files.copy(imageFileInputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            imageFileUrl = campaign.getUuid() + File.separator + modifiedFileName;

        } catch (Exception ex) {

            log.info("Some Error Occurred while Saving Image File. See Error Logs for More Details.");
            log.error("Exception Occurred while Saving Image File: ", ex);

            throw ex;
        }

        campaignCompBrand.setImageFileUrl(imageFileUrl);

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
