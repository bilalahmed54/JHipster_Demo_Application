package com.vodworks.service.mapper;

import com.vodworks.domain.Answer;
import com.vodworks.service.dto.AnswerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Answer} and its DTO {@link AnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = {RespondentMapper.class, CampaignMapper.class, QuestionMapper.class})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {

    @Mapping(source = "respondent.id", target = "respondentId")
    @Mapping(source = "campaign.id", target = "campaignId")
    @Mapping(source = "question.id", target = "questionId")
    AnswerDTO toDto(Answer answer);

    @Mapping(source = "respondentId", target = "respondent")
    @Mapping(source = "campaignId", target = "campaign")
    @Mapping(source = "questionId", target = "question")
    Answer toEntity(AnswerDTO answerDTO);

    default Answer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(id);
        return answer;
    }
}
