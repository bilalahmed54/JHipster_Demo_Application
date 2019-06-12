import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';

@Component({
  selector: 'jhi-campaign-social-component-detail',
  templateUrl: './campaign-social-component-detail.component.html'
})
export class CampaignSocialComponentDetailComponent implements OnInit {
  campaignSocialComponent: ICampaignSocialComponent;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ campaignSocialComponent }) => {
      this.campaignSocialComponent = campaignSocialComponent;
    });
  }

  previousState() {
    window.history.back();
  }
}
