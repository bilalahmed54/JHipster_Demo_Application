package com.vodworks.repository;

import com.vodworks.domain.Respondent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Respondent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RespondentRepository extends JpaRepository<Respondent, Long> {

}
