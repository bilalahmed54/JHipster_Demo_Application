import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterBaseAppSharedModule } from 'app/shared';
import {
  CampaignCompBrandComponent,
  CampaignCompBrandDetailComponent,
  CampaignCompBrandUpdateComponent,
  CampaignCompBrandDeletePopupComponent,
  CampaignCompBrandDeleteDialogComponent,
  campaignCompBrandRoute,
  campaignCompBrandPopupRoute
} from './';

const ENTITY_STATES = [...campaignCompBrandRoute, ...campaignCompBrandPopupRoute];

@NgModule({
  imports: [JhipsterBaseAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CampaignCompBrandComponent,
    CampaignCompBrandDetailComponent,
    CampaignCompBrandUpdateComponent,
    CampaignCompBrandDeleteDialogComponent,
    CampaignCompBrandDeletePopupComponent
  ],
  entryComponents: [
    CampaignCompBrandComponent,
    CampaignCompBrandUpdateComponent,
    CampaignCompBrandDeleteDialogComponent,
    CampaignCompBrandDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterBaseAppCampaignCompBrandModule {}
