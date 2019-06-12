package com.vodworks.web.rest;

import com.vodworks.service.CampaignCompBrandService;
import com.vodworks.web.rest.errors.BadRequestAlertException;
import com.vodworks.service.dto.CampaignCompBrandDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vodworks.domain.CampaignCompBrand}.
 */
@RestController
@RequestMapping("/api")
public class CampaignCompBrandResource {

    private final Logger log = LoggerFactory.getLogger(CampaignCompBrandResource.class);

    private static final String ENTITY_NAME = "campaignCompBrand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignCompBrandService campaignCompBrandService;

    public CampaignCompBrandResource(CampaignCompBrandService campaignCompBrandService) {
        this.campaignCompBrandService = campaignCompBrandService;
    }

    /**
     * {@code POST  /campaign-comp-brands} : Create a new campaignCompBrand.
     *
     * @param campaignCompBrandDTO the campaignCompBrandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignCompBrandDTO, or with status {@code 400 (Bad Request)} if the campaignCompBrand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campaign-comp-brands")
    public ResponseEntity<CampaignCompBrandDTO> createCampaignCompBrand(@Valid @RequestBody CampaignCompBrandDTO campaignCompBrandDTO) throws URISyntaxException {
        log.debug("REST request to save CampaignCompBrand : {}", campaignCompBrandDTO);
        if (campaignCompBrandDTO.getId() != null) {
            throw new BadRequestAlertException("A new campaignCompBrand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignCompBrandDTO result = campaignCompBrandService.save(campaignCompBrandDTO);
        return ResponseEntity.created(new URI("/api/campaign-comp-brands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campaign-comp-brands} : Updates an existing campaignCompBrand.
     *
     * @param campaignCompBrandDTO the campaignCompBrandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignCompBrandDTO,
     * or with status {@code 400 (Bad Request)} if the campaignCompBrandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campaignCompBrandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campaign-comp-brands")
    public ResponseEntity<CampaignCompBrandDTO> updateCampaignCompBrand(@Valid @RequestBody CampaignCompBrandDTO campaignCompBrandDTO) throws URISyntaxException {
        log.debug("REST request to update CampaignCompBrand : {}", campaignCompBrandDTO);
        if (campaignCompBrandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CampaignCompBrandDTO result = campaignCompBrandService.save(campaignCompBrandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, campaignCompBrandDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campaign-comp-brands} : get all the campaignCompBrands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaignCompBrands in body.
     */
    @GetMapping("/campaign-comp-brands")
    public List<CampaignCompBrandDTO> getAllCampaignCompBrands() {
        log.debug("REST request to get all CampaignCompBrands");
        return campaignCompBrandService.findAll();
    }

    /**
     * {@code GET  /campaign-comp-brands/:id} : get the "id" campaignCompBrand.
     *
     * @param id the id of the campaignCompBrandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignCompBrandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campaign-comp-brands/{id}")
    public ResponseEntity<CampaignCompBrandDTO> getCampaignCompBrand(@PathVariable Long id) {
        log.debug("REST request to get CampaignCompBrand : {}", id);
        Optional<CampaignCompBrandDTO> campaignCompBrandDTO = campaignCompBrandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignCompBrandDTO);
    }

    /**
     * {@code DELETE  /campaign-comp-brands/:id} : delete the "id" campaignCompBrand.
     *
     * @param id the id of the campaignCompBrandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campaign-comp-brands/{id}")
    public ResponseEntity<Void> deleteCampaignCompBrand(@PathVariable Long id) {
        log.debug("REST request to delete CampaignCompBrand : {}", id);
        campaignCompBrandService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
