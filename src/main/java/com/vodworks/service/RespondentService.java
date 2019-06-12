package com.vodworks.service;

import com.vodworks.service.dto.RespondentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vodworks.domain.Respondent}.
 */
public interface RespondentService {

    /**
     * Save a respondent.
     *
     * @param respondentDTO the entity to save.
     * @return the persisted entity.
     */
    RespondentDTO save(RespondentDTO respondentDTO);

    /**
     * Get all the respondents.
     *
     * @return the list of entities.
     */
    List<RespondentDTO> findAll();


    /**
     * Get the "id" respondent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RespondentDTO> findOne(Long id);

    /**
     * Delete the "id" respondent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
