import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRespondent } from 'app/shared/model/respondent.model';
import { AccountService } from 'app/core';
import { RespondentService } from './respondent.service';

@Component({
  selector: 'jhi-respondent',
  templateUrl: './respondent.component.html'
})
export class RespondentComponent implements OnInit, OnDestroy {
  respondents: IRespondent[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected respondentService: RespondentService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.respondentService
      .query()
      .pipe(
        filter((res: HttpResponse<IRespondent[]>) => res.ok),
        map((res: HttpResponse<IRespondent[]>) => res.body)
      )
      .subscribe(
        (res: IRespondent[]) => {
          this.respondents = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRespondents();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRespondent) {
    return item.id;
  }

  registerChangeInRespondents() {
    this.eventSubscriber = this.eventManager.subscribe('respondentListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
