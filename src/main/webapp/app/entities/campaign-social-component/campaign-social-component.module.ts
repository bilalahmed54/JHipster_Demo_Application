import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterBaseAppSharedModule } from 'app/shared';
import {
  CampaignSocialComponentComponent,
  CampaignSocialComponentDetailComponent,
  CampaignSocialComponentUpdateComponent,
  CampaignSocialComponentDeletePopupComponent,
  CampaignSocialComponentDeleteDialogComponent,
  campaignSocialComponentRoute,
  campaignSocialComponentPopupRoute
} from './';

const ENTITY_STATES = [...campaignSocialComponentRoute, ...campaignSocialComponentPopupRoute];

@NgModule({
  imports: [JhipsterBaseAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CampaignSocialComponentComponent,
    CampaignSocialComponentDetailComponent,
    CampaignSocialComponentUpdateComponent,
    CampaignSocialComponentDeleteDialogComponent,
    CampaignSocialComponentDeletePopupComponent
  ],
  entryComponents: [
    CampaignSocialComponentComponent,
    CampaignSocialComponentUpdateComponent,
    CampaignSocialComponentDeleteDialogComponent,
    CampaignSocialComponentDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterBaseAppCampaignSocialComponentModule {}
