package com.vodworks.service.dto;

import java.util.List;

public class CampaignListDTO {

    private List<CampaignDTO> campaigns;

    public CampaignListDTO() {
    }

    public CampaignListDTO(List<CampaignDTO> campaigns) {
        this.campaigns = campaigns;
    }

    public List<CampaignDTO> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<CampaignDTO> campaigns) {
        this.campaigns = campaigns;
    }
}
