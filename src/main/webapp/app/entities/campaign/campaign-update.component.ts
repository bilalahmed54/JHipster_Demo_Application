import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICampaign, Campaign } from 'app/shared/model/campaign.model';
import { CampaignService } from './campaign.service';

@Component({
  selector: 'jhi-campaign-update',
  templateUrl: './campaign-update.component.html'
})
export class CampaignUpdateComponent implements OnInit {
  campaign: ICampaign;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    category: [null, [Validators.required]],
    activity: [null, [Validators.required]],
    projectName: [null, [Validators.required]],
    projectDetails: [null, [Validators.required]],
    brandFirstTrait: [null, [Validators.required]],
    brandSecondTrait: [null, [Validators.required]],
    brandThirdTrait: [null, [Validators.required]],
    brandGoals: [null, [Validators.required]],
    sample: [null, [Validators.required]],
    instagramAudienceDescription: [null, [Validators.required]]
  });

  constructor(protected campaignService: CampaignService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ campaign }) => {
      this.updateForm(campaign);
      this.campaign = campaign;
    });
  }

  updateForm(campaign: ICampaign) {
    this.editForm.patchValue({
      id: campaign.id,
      uuid: campaign.uuid,
      category: campaign.category,
      activity: campaign.activity,
      projectName: campaign.projectName,
      projectDetails: campaign.projectDetails,
      brandFirstTrait: campaign.brandFirstTrait,
      brandSecondTrait: campaign.brandSecondTrait,
      brandThirdTrait: campaign.brandThirdTrait,
      brandGoals: campaign.brandGoals,
      sample: campaign.sample,
      instagramAudienceDescription: campaign.instagramAudienceDescription
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const campaign = this.createFromForm();
    if (campaign.id !== undefined) {
      this.subscribeToSaveResponse(this.campaignService.update(campaign));
    } else {
      this.subscribeToSaveResponse(this.campaignService.create(campaign));
    }
  }

  private createFromForm(): ICampaign {
    const entity = {
      ...new Campaign(),
      id: this.editForm.get(['id']).value,
      uuid: this.editForm.get(['uuid']).value,
      category: this.editForm.get(['category']).value,
      activity: this.editForm.get(['activity']).value,
      projectName: this.editForm.get(['projectName']).value,
      projectDetails: this.editForm.get(['projectDetails']).value,
      brandFirstTrait: this.editForm.get(['brandFirstTrait']).value,
      brandSecondTrait: this.editForm.get(['brandSecondTrait']).value,
      brandThirdTrait: this.editForm.get(['brandThirdTrait']).value,
      brandGoals: this.editForm.get(['brandGoals']).value,
      sample: this.editForm.get(['sample']).value,
      instagramAudienceDescription: this.editForm.get(['instagramAudienceDescription']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICampaign>>) {
    result.subscribe((res: HttpResponse<ICampaign>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
