package com.vodworks.service.impl;

import com.vodworks.domain.Campaign;
import com.vodworks.domain.CampaignSocialComponent;
import com.vodworks.repository.CampaignRepository;
import com.vodworks.repository.CampaignSocialComponentRepository;
import com.vodworks.service.CampaignSocialComponentService;
import com.vodworks.service.dto.CampaignSocialComponentDTO;
import com.vodworks.service.mapper.CampaignSocialComponentMapper;
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
 * Service Implementation for managing {@link CampaignSocialComponent}.
 */
@Service
@Transactional
public class CampaignSocialComponentServiceImpl implements CampaignSocialComponentService {

    private final Logger log = LoggerFactory.getLogger(CampaignSocialComponentServiceImpl.class);

    private String campaignImagesUploadDir = System.getProperty("user.home") + File.separator + "files" + File.separator;

    private final CampaignRepository campaignRepository;

    private final CampaignSocialComponentRepository campaignSocialComponentRepository;

    private final CampaignSocialComponentMapper campaignSocialComponentMapper;

    public CampaignSocialComponentServiceImpl(CampaignRepository campaignRepository, CampaignSocialComponentRepository campaignSocialComponentRepository, CampaignSocialComponentMapper campaignSocialComponentMapper) {
        this.campaignRepository = campaignRepository;
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
    public CampaignSocialComponentDTO save(CampaignSocialComponentDTO campaignSocialComponentDTO, MultipartFile imageFile) throws IOException {
        log.debug("Request to save CampaignSocialComponent : {}", campaignSocialComponentDTO);
        CampaignSocialComponent campaignSocialComponent = campaignSocialComponentMapper.toEntity(campaignSocialComponentDTO);

        Campaign campaign = this.campaignRepository.getOne(campaignSocialComponentDTO.getCampaignId());
        campaignSocialComponent.setCampaign(campaign);

        // Creating directory t place campaign files on the server
        String imagesDir = campaignImagesUploadDir + campaign.getUuid();

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

        campaignSocialComponent.setImageFileUrl(imageFileUrl);

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
