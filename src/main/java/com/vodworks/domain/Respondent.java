package com.vodworks.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.vodworks.domain.enumeration.UserTypes;

/**
 * A Respondent.
 */
@Entity
@Table(name = "respondent")
public class Respondent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserTypes userType;

    @NotNull
    @Column(name = "is_video_recorded", nullable = false)
    private Boolean isVideoRecorded;

    @OneToMany(mappedBy = "respondent")
    private Set<Answer> campaignAnswers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("respondents")
    private Campaign campaign;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public Respondent uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public Respondent userType(UserTypes userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }

    public Boolean isIsVideoRecorded() {
        return isVideoRecorded;
    }

    public Respondent isVideoRecorded(Boolean isVideoRecorded) {
        this.isVideoRecorded = isVideoRecorded;
        return this;
    }

    public void setIsVideoRecorded(Boolean isVideoRecorded) {
        this.isVideoRecorded = isVideoRecorded;
    }

    public Set<Answer> getCampaignAnswers() {
        return campaignAnswers;
    }

    public Respondent campaignAnswers(Set<Answer> answers) {
        this.campaignAnswers = answers;
        return this;
    }

    public Respondent addCampaignAnswers(Answer answer) {
        this.campaignAnswers.add(answer);
        answer.setRespondent(this);
        return this;
    }

    public Respondent removeCampaignAnswers(Answer answer) {
        this.campaignAnswers.remove(answer);
        answer.setRespondent(null);
        return this;
    }

    public void setCampaignAnswers(Set<Answer> answers) {
        this.campaignAnswers = answers;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public Respondent campaign(Campaign campaign) {
        this.campaign = campaign;
        return this;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Respondent)) {
            return false;
        }
        return id != null && id.equals(((Respondent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Respondent{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", userType='" + getUserType() + "'" +
            ", isVideoRecorded='" + isIsVideoRecorded() + "'" +
            "}";
    }
}
