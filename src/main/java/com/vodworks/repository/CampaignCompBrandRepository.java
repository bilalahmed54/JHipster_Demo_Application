package com.vodworks.repository;

import com.vodworks.domain.CampaignCompBrand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CampaignCompBrand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignCompBrandRepository extends JpaRepository<CampaignCompBrand, Long> {

}
