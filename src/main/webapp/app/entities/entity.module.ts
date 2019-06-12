import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'respondent',
        loadChildren: './respondent/respondent.module#JhipsterBaseAppRespondentModule'
      },
      {
        path: 'campaign',
        loadChildren: './campaign/campaign.module#JhipsterBaseAppCampaignModule'
      },
      {
        path: 'answer',
        loadChildren: './answer/answer.module#JhipsterBaseAppAnswerModule'
      },
      {
        path: 'question',
        loadChildren: './question/question.module#JhipsterBaseAppQuestionModule'
      },
      {
        path: 'campaign-comp-brand',
        loadChildren: './campaign-comp-brand/campaign-comp-brand.module#JhipsterBaseAppCampaignCompBrandModule'
      },
      {
        path: 'campaign-social-component',
        loadChildren: './campaign-social-component/campaign-social-component.module#JhipsterBaseAppCampaignSocialComponentModule'
      },
      {
        path: 'respondent',
        loadChildren: './respondent/respondent.module#JhipsterBaseAppRespondentModule'
      },
      {
        path: 'campaign',
        loadChildren: './campaign/campaign.module#JhipsterBaseAppCampaignModule'
      },
      {
        path: 'answer',
        loadChildren: './answer/answer.module#JhipsterBaseAppAnswerModule'
      },
      {
        path: 'question',
        loadChildren: './question/question.module#JhipsterBaseAppQuestionModule'
      },
      {
        path: 'campaign-comp-brand',
        loadChildren: './campaign-comp-brand/campaign-comp-brand.module#JhipsterBaseAppCampaignCompBrandModule'
      },
      {
        path: 'campaign-social-component',
        loadChildren: './campaign-social-component/campaign-social-component.module#JhipsterBaseAppCampaignSocialComponentModule'
      },
      {
        path: 'respondent',
        loadChildren: './respondent/respondent.module#JhipsterBaseAppRespondentModule'
      },
      {
        path: 'campaign',
        loadChildren: './campaign/campaign.module#JhipsterBaseAppCampaignModule'
      },
      {
        path: 'answer',
        loadChildren: './answer/answer.module#JhipsterBaseAppAnswerModule'
      },
      {
        path: 'question',
        loadChildren: './question/question.module#JhipsterBaseAppQuestionModule'
      },
      {
        path: 'campaign-comp-brand',
        loadChildren: './campaign-comp-brand/campaign-comp-brand.module#JhipsterBaseAppCampaignCompBrandModule'
      },
      {
        path: 'campaign-social-component',
        loadChildren: './campaign-social-component/campaign-social-component.module#JhipsterBaseAppCampaignSocialComponentModule'
      },
      {
        path: 'respondent',
        loadChildren: './respondent/respondent.module#JhipsterBaseAppRespondentModule'
      },
      {
        path: 'campaign',
        loadChildren: './campaign/campaign.module#JhipsterBaseAppCampaignModule'
      },
      {
        path: 'answer',
        loadChildren: './answer/answer.module#JhipsterBaseAppAnswerModule'
      },
      {
        path: 'question',
        loadChildren: './question/question.module#JhipsterBaseAppQuestionModule'
      },
      {
        path: 'campaign-comp-brand',
        loadChildren: './campaign-comp-brand/campaign-comp-brand.module#JhipsterBaseAppCampaignCompBrandModule'
      },
      {
        path: 'campaign-social-component',
        loadChildren: './campaign-social-component/campaign-social-component.module#JhipsterBaseAppCampaignSocialComponentModule'
      },
      {
        path: 'campaign',
        loadChildren: './campaign/campaign.module#JhipsterBaseAppCampaignModule'
      },
      {
        path: 'campaign-social-component',
        loadChildren: './campaign-social-component/campaign-social-component.module#JhipsterBaseAppCampaignSocialComponentModule'
      },
      {
        path: 'campaign-social-component',
        loadChildren: './campaign-social-component/campaign-social-component.module#JhipsterBaseAppCampaignSocialComponentModule'
      },
      {
        path: 'campaign-social-component',
        loadChildren: './campaign-social-component/campaign-social-component.module#JhipsterBaseAppCampaignSocialComponentModule'
      },
      {
        path: 'campaign-comp-brand',
        loadChildren: './campaign-comp-brand/campaign-comp-brand.module#JhipsterBaseAppCampaignCompBrandModule'
      },
      {
        path: 'campaign-comp-brand',
        loadChildren: './campaign-comp-brand/campaign-comp-brand.module#JhipsterBaseAppCampaignCompBrandModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterBaseAppEntityModule {}
