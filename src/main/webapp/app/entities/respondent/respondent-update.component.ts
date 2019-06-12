import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRespondent, Respondent } from 'app/shared/model/respondent.model';
import { RespondentService } from './respondent.service';
import { ICampaign } from 'app/shared/model/campaign.model';
import { CampaignService } from 'app/entities/campaign';

@Component({
  selector: 'jhi-respondent-update',
  templateUrl: './respondent-update.component.html'
})
export class RespondentUpdateComponent implements OnInit {
  respondent: IRespondent;
  isSaving: boolean;

  campaigns: ICampaign[];

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    userType: [null, [Validators.required]],
    isVideoRecorded: [null, [Validators.required]],
    campaignId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected respondentService: RespondentService,
    protected campaignService: CampaignService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ respondent }) => {
      this.updateForm(respondent);
      this.respondent = respondent;
    });
    this.campaignService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICampaign[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICampaign[]>) => response.body)
      )
      .subscribe((res: ICampaign[]) => (this.campaigns = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(respondent: IRespondent) {
    this.editForm.patchValue({
      id: respondent.id,
      uuid: respondent.uuid,
      userType: respondent.userType,
      isVideoRecorded: respondent.isVideoRecorded,
      campaignId: respondent.campaignId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const respondent = this.createFromForm();
    if (respondent.id !== undefined) {
      this.subscribeToSaveResponse(this.respondentService.update(respondent));
    } else {
      this.subscribeToSaveResponse(this.respondentService.create(respondent));
    }
  }

  private createFromForm(): IRespondent {
    const entity = {
      ...new Respondent(),
      id: this.editForm.get(['id']).value,
      uuid: this.editForm.get(['uuid']).value,
      userType: this.editForm.get(['userType']).value,
      isVideoRecorded: this.editForm.get(['isVideoRecorded']).value,
      campaignId: this.editForm.get(['campaignId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRespondent>>) {
    result.subscribe((res: HttpResponse<IRespondent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCampaignById(index: number, item: ICampaign) {
    return item.id;
  }
}
