import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';
import { AccountService } from 'app/core';
import { CampaignCompBrandService } from './campaign-comp-brand.service';

@Component({
  selector: 'jhi-campaign-comp-brand',
  templateUrl: './campaign-comp-brand.component.html'
})
export class CampaignCompBrandComponent implements OnInit, OnDestroy {
  campaignCompBrands: ICampaignCompBrand[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected campaignCompBrandService: CampaignCompBrandService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.campaignCompBrandService
      .query()
      .pipe(
        filter((res: HttpResponse<ICampaignCompBrand[]>) => res.ok),
        map((res: HttpResponse<ICampaignCompBrand[]>) => res.body)
      )
      .subscribe(
        (res: ICampaignCompBrand[]) => {
          this.campaignCompBrands = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCampaignCompBrands();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICampaignCompBrand) {
    return item.id;
  }

  registerChangeInCampaignCompBrands() {
    this.eventSubscriber = this.eventManager.subscribe('campaignCompBrandListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
