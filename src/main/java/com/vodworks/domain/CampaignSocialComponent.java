package com.vodworks.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CampaignSocialComponent.
 */
@Entity
@Table(name = "campaign_social_component")
public class CampaignSocialComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "caption", nullable = false)
    private String caption;

    @NotNull
    @Column(name = "influencer", nullable = false)
    private String influencer;

    @NotNull
    @Column(name = "image_file_url", nullable = false)
    private String imageFileUrl;

    @ManyToOne
    @JsonIgnoreProperties("campaignSocialComponents")
    private Campaign campaign;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public CampaignSocialComponent caption(String caption) {
        this.caption = caption;
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getInfluencer() {
        return influencer;
    }

    public CampaignSocialComponent influencer(String influencer) {
        this.influencer = influencer;
        return this;
    }

    public void setInfluencer(String influencer) {
        this.influencer = influencer;
    }

    public String getImageFileUrl() {
        return imageFileUrl;
    }

    public CampaignSocialComponent imageFileUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
        return this;
    }

    public void setImageFileUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public CampaignSocialComponent campaign(Campaign campaign) {
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
        if (!(o instanceof CampaignSocialComponent)) {
            return false;
        }
        return id != null && id.equals(((CampaignSocialComponent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CampaignSocialComponent{" +
            "id=" + getId() +
            ", caption='" + getCaption() + "'" +
            ", influencer='" + getInfluencer() + "'" +
            ", imageFileUrl='" + getImageFileUrl() + "'" +
            "}";
    }
}
