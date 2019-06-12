package com.vodworks.service.mapper;

import com.vodworks.domain.*;
import com.vodworks.service.dto.CampaignCompBrandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CampaignCompBrand} and its DTO {@link CampaignCompBrandDTO}.
 */
@Mapper(componentModel = "spring", uses = {CampaignMapper.class})
public interface CampaignCompBrandMapper extends EntityMapper<CampaignCompBrandDTO, CampaignCompBrand> {

    @Mapping(source = "campaign.id", target = "campaignId")
    CampaignCompBrandDTO toDto(CampaignCompBrand campaignCompBrand);

    @Mapping(source = "campaignId", target = "campaign")
    CampaignCompBrand toEntity(CampaignCompBrandDTO campaignCompBrandDTO);

    default CampaignCompBrand fromId(Long id) {
        if (id == null) {
            return null;
        }
        CampaignCompBrand campaignCompBrand = new CampaignCompBrand();
        campaignCompBrand.setId(id);
        return campaignCompBrand;
    }
}
