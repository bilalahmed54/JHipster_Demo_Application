package com.vodworks.service.dto;

import com.vodworks.domain.enumeration.UserTypes;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vodworks.domain.Respondent} entity.
 */
public class RespondentDTO implements Serializable {

    private Long id;

    @NotNull
    private String uuid;

    @NotNull
    private UserTypes userType;

    @NotNull
    private Boolean isVideoRecorded;


    private Long campaignId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }

    public Boolean isIsVideoRecorded() {
        return isVideoRecorded;
    }

    public void setIsVideoRecorded(Boolean isVideoRecorded) {
        this.isVideoRecorded = isVideoRecorded;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RespondentDTO respondentDTO = (RespondentDTO) o;
        if (respondentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), respondentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RespondentDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", userType='" + getUserType() + "'" +
            ", isVideoRecorded='" + isIsVideoRecorded() + "'" +
            ", campaign=" + getCampaignId() +
            "}";
    }
}
