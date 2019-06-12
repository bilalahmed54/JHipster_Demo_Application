package com.vodworks.web.rest;

import com.vodworks.service.CampaignSocialComponentService;
import com.vodworks.service.dto.CampaignSocialComponentDTO;
import com.vodworks.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vodworks.domain.CampaignSocialComponent}.
 */
@RestController
@RequestMapping("/api/admin/creatinsta")
public class CampaignSocialComponentResource {

    private final Logger log = LoggerFactory.getLogger(CampaignSocialComponentResource.class);

    private static final String ENTITY_NAME = "campaignSocialComponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignSocialComponentService campaignSocialComponentService;

    public CampaignSocialComponentResource(CampaignSocialComponentService campaignSocialComponentService) {
        this.campaignSocialComponentService = campaignSocialComponentService;
    }

    /**
     * {@code POST  /campaign-social-components} : Create a new campaignSocialComponent.
     *
     * @param campaignSocialComponentDTO the campaignSocialComponentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignSocialComponentDTO, or with status {@code 400 (Bad Request)} if the campaignSocialComponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/save")
    public ResponseEntity<CampaignSocialComponentDTO> createCampaignSocialComponent(
        @Valid @RequestPart(value = "meta_data", required = true) CampaignSocialComponentDTO campaignSocialComponentDTO,
        @RequestParam(value = "imageFile", required = true) MultipartFile imageFile) throws URISyntaxException, IOException {

        log.debug("REST request to save CampaignSocialComponent : {}", campaignSocialComponentDTO);
        if (campaignSocialComponentDTO.getId() != null) {
            throw new BadRequestAlertException("A new campaignSocialComponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignSocialComponentDTO result = campaignSocialComponentService.save(campaignSocialComponentDTO, imageFile);
        return ResponseEntity.created(new URI("/api/campaign-social-components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campaign-social-components} : get all the campaignSocialComponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaignSocialComponents in body.
     */
    @GetMapping("/campaign-social-components")
    public List<CampaignSocialComponentDTO> getAllCampaignSocialComponents() {
        log.debug("REST request to get all CampaignSocialComponents");
        return campaignSocialComponentService.findAll();
    }

    /**
     * {@code GET  /campaign-social-components/:id} : get the "id" campaignSocialComponent.
     *
     * @param id the id of the campaignSocialComponentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignSocialComponentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campaign-social-components/{id}")
    public ResponseEntity<CampaignSocialComponentDTO> getCampaignSocialComponent(@PathVariable Long id) {
        log.debug("REST request to get CampaignSocialComponent : {}", id);
        Optional<CampaignSocialComponentDTO> campaignSocialComponentDTO = campaignSocialComponentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignSocialComponentDTO);
    }
}
