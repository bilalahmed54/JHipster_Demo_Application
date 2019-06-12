package com.vodworks.service.dto;
import javax.validation.constraints.*;
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
    private String imageFileUrl;


    private Long campaignId;

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

    public String getImageFileUrl() {
        return imageFileUrl;
    }

    public void setImageFileUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
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
            ", imageFileUrl='" + getImageFileUrl() + "'" +
            ", campaign=" + getCampaignId() +
            "}";
    }
}
