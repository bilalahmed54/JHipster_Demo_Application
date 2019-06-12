package com.vodworks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull
    @Column(name = "activity", nullable = false)
    private String activity;

    @NotNull
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @NotNull
    @Column(name = "project_details", nullable = false)
    private String projectDetails;

    @NotNull
    @Column(name = "brand_first_trait", nullable = false)
    private String brandFirstTrait;

    @NotNull
    @Column(name = "brand_second_trait", nullable = false)
    private String brandSecondTrait;

    @NotNull
    @Column(name = "brand_third_trait", nullable = false)
    private String brandThirdTrait;

    @NotNull
    @Column(name = "brand_goals", nullable = false)
    private String brandGoals;

    @NotNull
    @Column(name = "sample", nullable = false)
    private String sample;

    @NotNull
    @Column(name = "instagram_audience_description", nullable = false)
    private String instagramAudienceDescription;

    @OneToMany(mappedBy = "campaign")
    private Set<Respondent> respondents = new HashSet<>();

    @OneToMany(mappedBy = "campaign")
    private Set<CampaignCompBrand> competitorLogos = new HashSet<>();

    @OneToMany(mappedBy = "campaign")
    private Set<CampaignSocialComponent> instagramPhotos = new HashSet<>();

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

    public Campaign uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategory() {
        return category;
    }

    public Campaign category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getActivity() {
        return activity;
    }

    public Campaign activity(String activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getProjectName() {
        return projectName;
    }

    public Campaign projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDetails() {
        return projectDetails;
    }

    public Campaign projectDetails(String projectDetails) {
        this.projectDetails = projectDetails;
        return this;
    }

    public void setProjectDetails(String projectDetails) {
        this.projectDetails = projectDetails;
    }

    public String getBrandFirstTrait() {
        return brandFirstTrait;
    }

    public Campaign brandFirstTrait(String brandFirstTrait) {
        this.brandFirstTrait = brandFirstTrait;
        return this;
    }

    public void setBrandFirstTrait(String brandFirstTrait) {
        this.brandFirstTrait = brandFirstTrait;
    }

    public String getBrandSecondTrait() {
        return brandSecondTrait;
    }

    public Campaign brandSecondTrait(String brandSecondTrait) {
        this.brandSecondTrait = brandSecondTrait;
        return this;
    }

    public void setBrandSecondTrait(String brandSecondTrait) {
        this.brandSecondTrait = brandSecondTrait;
    }

    public String getBrandThirdTrait() {
        return brandThirdTrait;
    }

    public Campaign brandThirdTrait(String brandThirdTrait) {
        this.brandThirdTrait = brandThirdTrait;
        return this;
    }

    public void setBrandThirdTrait(String brandThirdTrait) {
        this.brandThirdTrait = brandThirdTrait;
    }

    public String getBrandGoals() {
        return brandGoals;
    }

    public Campaign brandGoals(String brandGoals) {
        this.brandGoals = brandGoals;
        return this;
    }

    public void setBrandGoals(String brandGoals) {
        this.brandGoals = brandGoals;
    }

    public String getSample() {
        return sample;
    }

    public Campaign sample(String sample) {
        this.sample = sample;
        return this;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getInstagramAudienceDescription() {
        return instagramAudienceDescription;
    }

    public Campaign instagramAudienceDescription(String instagramAudienceDescription) {
        this.instagramAudienceDescription = instagramAudienceDescription;
        return this;
    }

    public void setInstagramAudienceDescription(String instagramAudienceDescription) {
        this.instagramAudienceDescription = instagramAudienceDescription;
    }

    public Set<Respondent> getRespondents() {
        return respondents;
    }

    public Campaign respondents(Set<Respondent> respondents) {
        this.respondents = respondents;
        return this;
    }

    public Campaign addRespondents(Respondent respondent) {
        this.respondents.add(respondent);
        respondent.setCampaign(this);
        return this;
    }

    public Campaign removeRespondents(Respondent respondent) {
        this.respondents.remove(respondent);
        respondent.setCampaign(null);
        return this;
    }

    public void setRespondents(Set<Respondent> respondents) {
        this.respondents = respondents;
    }

    public Set<CampaignCompBrand> getCompetitorLogos() {
        return competitorLogos;
    }

    public Campaign competitorLogos(Set<CampaignCompBrand> campaignCompBrands) {
        this.competitorLogos = campaignCompBrands;
        return this;
    }

    public Campaign addCompetitorLogos(CampaignCompBrand campaignCompBrand) {
        this.competitorLogos.add(campaignCompBrand);
        campaignCompBrand.setCampaign(this);
        return this;
    }

    public Campaign removeCompetitorLogos(CampaignCompBrand campaignCompBrand) {
        this.competitorLogos.remove(campaignCompBrand);
        campaignCompBrand.setCampaign(null);
        return this;
    }

    public void setCompetitorLogos(Set<CampaignCompBrand> campaignCompBrands) {
        this.competitorLogos = campaignCompBrands;
    }

    public Set<CampaignSocialComponent> getInstagramPhotos() {
        return instagramPhotos;
    }

    public Campaign instagramPhotos(Set<CampaignSocialComponent> campaignSocialComponents) {
        this.instagramPhotos = campaignSocialComponents;
        return this;
    }

    public Campaign addInstagramPhotos(CampaignSocialComponent campaignSocialComponent) {
        this.instagramPhotos.add(campaignSocialComponent);
        campaignSocialComponent.setCampaign(this);
        return this;
    }

    public Campaign removeInstagramPhotos(CampaignSocialComponent campaignSocialComponent) {
        this.instagramPhotos.remove(campaignSocialComponent);
        campaignSocialComponent.setCampaign(null);
        return this;
    }

    public void setInstagramPhotos(Set<CampaignSocialComponent> campaignSocialComponents) {
        this.instagramPhotos = campaignSocialComponents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campaign)) {
            return false;
        }
        return id != null && id.equals(((Campaign) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", category='" + getCategory() + "'" +
            ", activity='" + getActivity() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", projectDetails='" + getProjectDetails() + "'" +
            ", brandFirstTrait='" + getBrandFirstTrait() + "'" +
            ", brandSecondTrait='" + getBrandSecondTrait() + "'" +
            ", brandThirdTrait='" + getBrandThirdTrait() + "'" +
            ", brandGoals='" + getBrandGoals() + "'" +
            ", sample='" + getSample() + "'" +
            ", instagramAudienceDescription='" + getInstagramAudienceDescription() + "'" +
            "}";
    }
}
