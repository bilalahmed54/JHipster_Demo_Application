export interface ICampaignSocialComponent {
  id?: number;
  caption?: string;
  influencer?: string;
  imageFileUrl?: string;
  campaignId?: number;
}

export class CampaignSocialComponent implements ICampaignSocialComponent {
  constructor(
    public id?: number,
    public caption?: string,
    public influencer?: string,
    public imageFileUrl?: string,
    public campaignId?: number
  ) {}
}
