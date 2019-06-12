import { IRespondent } from 'app/shared/model/respondent.model';
import { ICampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';
import { ICampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';

export interface ICampaign {
  id?: number;
  uuid?: string;
  category?: string;
  activity?: string;
  projectName?: string;
  projectDetails?: string;
  brandFirstTrait?: string;
  brandSecondTrait?: string;
  brandThirdTrait?: string;
  brandGoals?: string;
  sample?: string;
  instagramAudienceDescription?: string;
  respondents?: IRespondent[];
  competitorLogos?: ICampaignCompBrand[];
  instagramPhotos?: ICampaignSocialComponent[];
}

export class Campaign implements ICampaign {
  constructor(
    public id?: number,
    public uuid?: string,
    public category?: string,
    public activity?: string,
    public projectName?: string,
    public projectDetails?: string,
    public brandFirstTrait?: string,
    public brandSecondTrait?: string,
    public brandThirdTrait?: string,
    public brandGoals?: string,
    public sample?: string,
    public instagramAudienceDescription?: string,
    public respondents?: IRespondent[],
    public competitorLogos?: ICampaignCompBrand[],
    public instagramPhotos?: ICampaignSocialComponent[]
  ) {}
}
