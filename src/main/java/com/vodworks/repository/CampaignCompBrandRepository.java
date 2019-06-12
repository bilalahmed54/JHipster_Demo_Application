package com.vodworks.repository;

import java.util.List;
import com.vodworks.domain.Campaign;
import com.vodworks.domain.CampaignCompBrand;
import com.vodworks.domain.enumeration.CampaignBrandCompType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CampaignCompBrand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignCompBrandRepository extends JpaRepository<CampaignCompBrand, Long> {

    public List<CampaignCompBrand> findAllByCampaignAndAndType(Campaign campaign, CampaignBrandCompType type);
}
