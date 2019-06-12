package com.vodworks.web.rest;

import com.vodworks.service.CampaignService;
import com.vodworks.service.dto.CampaignQuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

/**
 * CampaignClientResource controller
 */
@RestController
@RequestMapping("/api/campaign")
public class CampaignClientResource {

    private final Logger log = LoggerFactory.getLogger(CampaignClientResource.class);

    private static final String ENTITY_NAME = "campaign";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignService campaignService;

    public CampaignClientResource(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    /**
     * GET get
     */
    @GetMapping("/get")
    public CampaignQuestionDTO get(@RequestParam(value = "campaignId", required = true) long campaignId) throws URISyntaxException {

        log.debug("REST request to save Campaign : {}", campaignId);

        CampaignQuestionDTO result = campaignService.get(campaignId);

        return result;
    }
}
