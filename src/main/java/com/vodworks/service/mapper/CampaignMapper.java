package com.vodworks.service.mapper;

import com.vodworks.domain.Campaign;
import com.vodworks.service.dto.CampaignDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Campaign} and its DTO {@link CampaignDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CampaignMapper extends EntityMapper<CampaignDTO, Campaign> {


    @Mapping(target = "respondents", ignore = true)
    @Mapping(target = "removeRespondents", ignore = true)
    @Mapping(target = "competitorLogos", ignore = true)
    @Mapping(target = "removeCompetitorLogos", ignore = true)
    @Mapping(target = "instagramPhotos", ignore = true)
    @Mapping(target = "removeInstagramPhotos", ignore = true)
    Campaign toEntity(CampaignDTO campaignDTO);

    default Campaign fromId(Long id) {
        if (id == null) {
            return null;
        }
        Campaign campaign = new Campaign();
        campaign.setId(id);
        return campaign;
    }
}
