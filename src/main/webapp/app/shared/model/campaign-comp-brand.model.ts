export interface ICampaignCompBrand {
  id?: number;
  compBrandName?: string;
  imageFileUrl?: string;
  campaignId?: number;
}

export class CampaignCompBrand implements ICampaignCompBrand {
  constructor(public id?: number, public compBrandName?: string, public imageFileUrl?: string, public campaignId?: number) {}
}
