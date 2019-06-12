import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';

@Component({
  selector: 'jhi-campaign-comp-brand-detail',
  templateUrl: './campaign-comp-brand-detail.component.html'
})
export class CampaignCompBrandDetailComponent implements OnInit {
  campaignCompBrand: ICampaignCompBrand;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ campaignCompBrand }) => {
      this.campaignCompBrand = campaignCompBrand;
    });
  }

  previousState() {
    window.history.back();
  }
}
