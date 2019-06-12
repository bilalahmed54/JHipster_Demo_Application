package com.vodworks.service.mapper;

import com.vodworks.domain.*;
import com.vodworks.service.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {CampaignMapper.class})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {

    @Mapping(source = "campaign.id", target = "campaignId")
    QuestionDTO toDto(Question question);

    @Mapping(source = "campaignId", target = "campaign")
    Question toEntity(QuestionDTO questionDTO);

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
