package com.vodworks.service.dto;

import com.vodworks.domain.CampaignSocialComponent;
import com.vodworks.domain.enumeration.CampaignSocialComponentType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.vodworks.domain.CampaignSocialComponent} entity.
 */
public class CampaignSocialComponentDTO implements Serializable {

    private Long id;

    @NotNull
    private String caption;

    @NotNull
    private String influencer;

    @NotNull
    private Boolean isPrimary;

    private String imageFileUrl;

    @NotNull
    private CampaignSocialComponentType type;

    @NotNull
    private Long campaignId;

    public CampaignSocialComponentDTO() {
    }

    public CampaignSocialComponentDTO(Long id, @NotNull String caption, @NotNull String influencer, @NotNull Boolean isPrimary, String imageFileUrl, @NotNull CampaignSocialComponentType type, @NotNull Long campaignId) {
        this.id = id;
        this.caption = caption;
        this.influencer = influencer;
        this.isPrimary = isPrimary;
        this.imageFileUrl = imageFileUrl;
        this.type = type;
        this.campaignId = campaignId;
    }

    public CampaignSocialComponentDTO(CampaignSocialComponent campaignSocialComponent) {
        this.id = campaignSocialComponent.getId();
        this.caption = campaignSocialComponent.getCaption();
        this.influencer = campaignSocialComponent.getInfluencer();
        this.isPrimary = campaignSocialComponent.isIsPrimary();
        this.imageFileUrl = campaignSocialComponent.getImageFileUrl();
        this.type = campaignSocialComponent.getType();
        this.campaignId = campaignSocialComponent.getCampaign().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getInfluencer() {
        return influencer;
    }

    public void setInfluencer(String influencer) {
        this.influencer = influencer;
    }

    public Boolean isIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getImageFileUrl() {
        return imageFileUrl;
    }

    public void setImageFileUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
    }

    public CampaignSocialComponentType getType() {
        return type;
    }

    public void setType(CampaignSocialComponentType type) {
        this.type = type;
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

        CampaignSocialComponentDTO campaignSocialComponentDTO = (CampaignSocialComponentDTO) o;
        if (campaignSocialComponentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campaignSocialComponentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CampaignSocialComponentDTO{" +
            "id=" + getId() +
            ", caption='" + getCaption() + "'" +
            ", influencer='" + getInfluencer() + "'" +
            ", isPrimary='" + isIsPrimary() + "'" +
            ", imageFileUrl='" + getImageFileUrl() + "'" +
            ", type='" + getType() + "'" +
            ", campaign=" + getCampaignId() +
            "}";
    }
}
