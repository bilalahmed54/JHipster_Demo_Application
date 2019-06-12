package com.vodworks.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CampaignCompBrand.
 */
@Entity
@Table(name = "campaign_comp_brand")
public class CampaignCompBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "comp_brand_name", nullable = false)
    private String compBrandName;

    @NotNull
    @Column(name = "image_file_url", nullable = false)
    private String imageFileUrl;

    @ManyToOne
    @JsonIgnoreProperties("campaignCompBrands")
    private Campaign campaign;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompBrandName() {
        return compBrandName;
    }

    public CampaignCompBrand compBrandName(String compBrandName) {
        this.compBrandName = compBrandName;
        return this;
    }

    public void setCompBrandName(String compBrandName) {
        this.compBrandName = compBrandName;
    }

    public String getImageFileUrl() {
        return imageFileUrl;
    }

    public CampaignCompBrand imageFileUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
        return this;
    }

    public void setImageFileUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public CampaignCompBrand campaign(Campaign campaign) {
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
        if (!(o instanceof CampaignCompBrand)) {
            return false;
        }
        return id != null && id.equals(((CampaignCompBrand) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CampaignCompBrand{" +
            "id=" + getId() +
            ", compBrandName='" + getCompBrandName() + "'" +
            ", imageFileUrl='" + getImageFileUrl() + "'" +
            "}";
    }
}
