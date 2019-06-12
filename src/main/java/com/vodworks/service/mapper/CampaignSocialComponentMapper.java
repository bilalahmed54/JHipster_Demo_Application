package com.vodworks.service.mapper;

import com.vodworks.domain.*;
import com.vodworks.service.dto.CampaignSocialComponentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampaignSocialComponent} and its DTO {@link CampaignSocialComponentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CampaignMapper.class})
public interface CampaignSocialComponentMapper extends EntityMapper<CampaignSocialComponentDTO, CampaignSocialComponent> {

    @Mapping(source = "campaign.id", target = "campaignId")
    CampaignSocialComponentDTO toDto(CampaignSocialComponent campaignSocialComponent);

    @Mapping(source = "campaignId", target = "campaign")
    CampaignSocialComponent toEntity(CampaignSocialComponentDTO campaignSocialComponentDTO);

    default CampaignSocialComponent fromId(Long id) {
        if (id == null) {
            return null;
        }
        CampaignSocialComponent campaignSocialComponent = new CampaignSocialComponent();
        campaignSocialComponent.setId(id);
        return campaignSocialComponent;
    }
}
