package com.vodworks.repository;

import com.vodworks.domain.CampaignSocialComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CampaignSocialComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignSocialComponentRepository extends JpaRepository<CampaignSocialComponent, Long> {

}
