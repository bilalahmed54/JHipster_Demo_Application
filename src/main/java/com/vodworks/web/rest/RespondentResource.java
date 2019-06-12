package com.vodworks.web.rest;

import com.vodworks.service.RespondentService;
import com.vodworks.web.rest.errors.BadRequestAlertException;
import com.vodworks.service.dto.RespondentDTO;

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
 * REST controller for managing {@link com.vodworks.domain.Respondent}.
 */
@RestController
@RequestMapping("/api")
public class RespondentResource {

    private final Logger log = LoggerFactory.getLogger(RespondentResource.class);

    private static final String ENTITY_NAME = "respondent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RespondentService respondentService;

    public RespondentResource(RespondentService respondentService) {
        this.respondentService = respondentService;
    }

    /**
     * {@code POST  /respondents} : Create a new respondent.
     *
     * @param respondentDTO the respondentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new respondentDTO, or with status {@code 400 (Bad Request)} if the respondent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/respondents")
    public ResponseEntity<RespondentDTO> createRespondent(@Valid @RequestBody RespondentDTO respondentDTO) throws URISyntaxException {
        log.debug("REST request to save Respondent : {}", respondentDTO);
        if (respondentDTO.getId() != null) {
            throw new BadRequestAlertException("A new respondent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RespondentDTO result = respondentService.save(respondentDTO);
        return ResponseEntity.created(new URI("/api/respondents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /respondents} : Updates an existing respondent.
     *
     * @param respondentDTO the respondentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated respondentDTO,
     * or with status {@code 400 (Bad Request)} if the respondentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the respondentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/respondents")
    public ResponseEntity<RespondentDTO> updateRespondent(@Valid @RequestBody RespondentDTO respondentDTO) throws URISyntaxException {
        log.debug("REST request to update Respondent : {}", respondentDTO);
        if (respondentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RespondentDTO result = respondentService.save(respondentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, respondentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /respondents} : get all the respondents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of respondents in body.
     */
    @GetMapping("/respondents")
    public List<RespondentDTO> getAllRespondents() {
        log.debug("REST request to get all Respondents");
        return respondentService.findAll();
    }

    /**
     * {@code GET  /respondents/:id} : get the "id" respondent.
     *
     * @param id the id of the respondentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the respondentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/respondents/{id}")
    public ResponseEntity<RespondentDTO> getRespondent(@PathVariable Long id) {
        log.debug("REST request to get Respondent : {}", id);
        Optional<RespondentDTO> respondentDTO = respondentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(respondentDTO);
    }

    /**
     * {@code DELETE  /respondents/:id} : delete the "id" respondent.
     *
     * @param id the id of the respondentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/respondents/{id}")
    public ResponseEntity<Void> deleteRespondent(@PathVariable Long id) {
        log.debug("REST request to delete Respondent : {}", id);
        respondentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
