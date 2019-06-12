import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICampaignSocialComponent, CampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';
import { CampaignSocialComponentService } from './campaign-social-component.service';
import { ICampaign } from 'app/shared/model/campaign.model';
import { CampaignService } from 'app/entities/campaign';

@Component({
  selector: 'jhi-campaign-social-component-update',
  templateUrl: './campaign-social-component-update.component.html'
})
export class CampaignSocialComponentUpdateComponent implements OnInit {
  campaignSocialComponent: ICampaignSocialComponent;
  isSaving: boolean;

  campaigns: ICampaign[];

  editForm = this.fb.group({
    id: [],
    caption: [null, [Validators.required]],
    influencer: [null, [Validators.required]],
    imageFileUrl: [null, [Validators.required]],
    campaignId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected campaignSocialComponentService: CampaignSocialComponentService,
    protected campaignService: CampaignService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ campaignSocialComponent }) => {
      this.updateForm(campaignSocialComponent);
      this.campaignSocialComponent = campaignSocialComponent;
    });
    this.campaignService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICampaign[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICampaign[]>) => response.body)
      )
      .subscribe((res: ICampaign[]) => (this.campaigns = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(campaignSocialComponent: ICampaignSocialComponent) {
    this.editForm.patchValue({
      id: campaignSocialComponent.id,
      caption: campaignSocialComponent.caption,
      influencer: campaignSocialComponent.influencer,
      imageFileUrl: campaignSocialComponent.imageFileUrl,
      campaignId: campaignSocialComponent.campaignId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const campaignSocialComponent = this.createFromForm();
    if (campaignSocialComponent.id !== undefined) {
      this.subscribeToSaveResponse(this.campaignSocialComponentService.update(campaignSocialComponent));
    } else {
      this.subscribeToSaveResponse(this.campaignSocialComponentService.create(campaignSocialComponent));
    }
  }

  private createFromForm(): ICampaignSocialComponent {
    const entity = {
      ...new CampaignSocialComponent(),
      id: this.editForm.get(['id']).value,
      caption: this.editForm.get(['caption']).value,
      influencer: this.editForm.get(['influencer']).value,
      imageFileUrl: this.editForm.get(['imageFileUrl']).value,
      campaignId: this.editForm.get(['campaignId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICampaignSocialComponent>>) {
    result.subscribe((res: HttpResponse<ICampaignSocialComponent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
