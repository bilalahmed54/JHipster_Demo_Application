import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';
import { AccountService } from 'app/core';
import { CampaignSocialComponentService } from './campaign-social-component.service';

@Component({
  selector: 'jhi-campaign-social-component',
  templateUrl: './campaign-social-component.component.html'
})
export class CampaignSocialComponentComponent implements OnInit, OnDestroy {
  campaignSocialComponents: ICampaignSocialComponent[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected campaignSocialComponentService: CampaignSocialComponentService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.campaignSocialComponentService
      .query()
      .pipe(
        filter((res: HttpResponse<ICampaignSocialComponent[]>) => res.ok),
        map((res: HttpResponse<ICampaignSocialComponent[]>) => res.body)
      )
      .subscribe(
        (res: ICampaignSocialComponent[]) => {
          this.campaignSocialComponents = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCampaignSocialComponents();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICampaignSocialComponent) {
    return item.id;
  }

  registerChangeInCampaignSocialComponents() {
    this.eventSubscriber = this.eventManager.subscribe('campaignSocialComponentListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
