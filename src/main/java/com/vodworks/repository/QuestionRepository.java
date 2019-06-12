package com.vodworks.repository;

import com.vodworks.domain.Campaign;
import com.vodworks.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    public List<Question> findAllByCampaign(Campaign campaign);
}
