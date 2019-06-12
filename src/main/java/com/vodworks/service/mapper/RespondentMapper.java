package com.vodworks.service.mapper;

import com.vodworks.domain.*;
import com.vodworks.service.dto.RespondentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Respondent} and its DTO {@link RespondentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CampaignMapper.class})
public interface RespondentMapper extends EntityMapper<RespondentDTO, Respondent> {

    @Mapping(source = "campaign.id", target = "campaignId")
    RespondentDTO toDto(Respondent respondent);

    @Mapping(target = "campaignAnswers", ignore = true)
    @Mapping(target = "removeCampaignAnswers", ignore = true)
    @Mapping(source = "campaignId", target = "campaign")
    Respondent toEntity(RespondentDTO respondentDTO);

    default Respondent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Respondent respondent = new Respondent();
        respondent.setId(id);
        return respondent;
    }
}
