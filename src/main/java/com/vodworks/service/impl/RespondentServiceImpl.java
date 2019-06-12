package com.vodworks.service.impl;

import com.vodworks.domain.Respondent;
import com.vodworks.repository.RespondentRepository;
import com.vodworks.service.RespondentService;
import com.vodworks.service.dto.RespondentDTO;
import com.vodworks.service.mapper.RespondentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Respondent}.
 */
@Service
@Transactional
public class RespondentServiceImpl implements RespondentService {

    private final Logger log = LoggerFactory.getLogger(RespondentServiceImpl.class);

    private final RespondentRepository respondentRepository;

    private final RespondentMapper respondentMapper;

    public RespondentServiceImpl(RespondentRepository respondentRepository, RespondentMapper respondentMapper) {
        this.respondentRepository = respondentRepository;
        this.respondentMapper = respondentMapper;
    }

    /**
     * Save a respondent.
     *
     * @param respondentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RespondentDTO save(RespondentDTO respondentDTO) {
        log.debug("Request to save Respondent : {}", respondentDTO);
        Respondent respondent = respondentMapper.toEntity(respondentDTO);
        respondent = respondentRepository.save(respondent);
        return respondentMapper.toDto(respondent);
    }

    /**
     * Get all the respondents.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RespondentDTO> findAll() {
        log.debug("Request to get all Respondents");
        return respondentRepository.findAll().stream()
            .map(respondentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one respondent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RespondentDTO> findOne(Long id) {
        log.debug("Request to get Respondent : {}", id);
        return respondentRepository.findById(id)
            .map(respondentMapper::toDto);
    }

    /**
     * Delete the respondent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Respondent : {}", id);
        respondentRepository.deleteById(id);
    }
}
