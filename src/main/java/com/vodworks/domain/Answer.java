package com.vodworks.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Answer.
 */
@Entity
@Table(name = "answer")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 1000)
    @Column(name = "answer", length = 1000, nullable = false)
    private String answer;

    @Column(name = "sub_question_id")
    private Integer subQuestionId;

    @ManyToOne
    @JsonIgnoreProperties("answers")
    private Respondent respondent;

    @OneToOne
    @JoinColumn(unique = true)
    private Campaign campaign;

    @OneToOne
    @JoinColumn(unique = true)
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public Answer answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getSubQuestionId() {
        return subQuestionId;
    }

    public Answer subQuestionId(Integer subQuestionId) {
        this.subQuestionId = subQuestionId;
        return this;
    }

    public void setSubQuestionId(Integer subQuestionId) {
        this.subQuestionId = subQuestionId;
    }

    public Respondent getRespondent() {
        return respondent;
    }

    public Answer respondent(Respondent respondent) {
        this.respondent = respondent;
        return this;
    }

    public void setRespondent(Respondent respondent) {
        this.respondent = respondent;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public Answer campaign(Campaign campaign) {
        this.campaign = campaign;
        return this;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        return id != null && id.equals(((Answer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", subQuestionId=" + getSubQuestionId() +
            "}";
    }
}
