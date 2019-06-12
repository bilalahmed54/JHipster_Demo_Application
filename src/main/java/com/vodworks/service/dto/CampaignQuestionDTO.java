package com.vodworks.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vodworks.domain.Campaign;
import com.vodworks.domain.Question;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CampaignQuestionDTO {

    private CampaignDTO campaign;
    private List<QuestionDTO> questions;

    public CampaignQuestionDTO() {
        this.questions = new ArrayList<>();
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public void populateQuestions(List<Question> questionList) {

        if (questionList == null || questionList.size() == 0) return;
        if (this.questions == null) this.questions = new ArrayList<>();

        for (Question question : questionList) {
            this.questions.add(new QuestionDTO(question));
        }
    }

    public CampaignDTO getCampaign() {
        return campaign;
    }

    public void setCampaign(CampaignDTO campaign) {
        this.campaign = campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = new CampaignDTO(campaign);
    }

}
