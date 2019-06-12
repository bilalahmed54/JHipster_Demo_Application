import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICampaignCompBrand, CampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';
import { CampaignCompBrandService } from './campaign-comp-brand.service';
import { ICampaign } from 'app/shared/model/campaign.model';
import { CampaignService } from 'app/entities/campaign';

@Component({
  selector: 'jhi-campaign-comp-brand-update',
  templateUrl: './campaign-comp-brand-update.component.html'
})
export class CampaignCompBrandUpdateComponent implements OnInit {
  campaignCompBrand: ICampaignCompBrand;
  isSaving: boolean;

  campaigns: ICampaign[];

  editForm = this.fb.group({
    id: [],
    compBrandName: [null, [Validators.required]],
    imageFileUrl: [null, [Validators.required]],
    campaignId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected campaignCompBrandService: CampaignCompBrandService,
    protected campaignService: CampaignService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ campaignCompBrand }) => {
      this.updateForm(campaignCompBrand);
      this.campaignCompBrand = campaignCompBrand;
    });
    this.campaignService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICampaign[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICampaign[]>) => response.body)
      )
      .subscribe((res: ICampaign[]) => (this.campaigns = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(campaignCompBrand: ICampaignCompBrand) {
    this.editForm.patchValue({
      id: campaignCompBrand.id,
      compBrandName: campaignCompBrand.compBrandName,
      imageFileUrl: campaignCompBrand.imageFileUrl,
      campaignId: campaignCompBrand.campaignId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const campaignCompBrand = this.createFromForm();
    if (campaignCompBrand.id !== undefined) {
      this.subscribeToSaveResponse(this.campaignCompBrandService.update(campaignCompBrand));
    } else {
      this.subscribeToSaveResponse(this.campaignCompBrandService.create(campaignCompBrand));
    }
  }

  private createFromForm(): ICampaignCompBrand {
    const entity = {
      ...new CampaignCompBrand(),
      id: this.editForm.get(['id']).value,
      compBrandName: this.editForm.get(['compBrandName']).value,
      imageFileUrl: this.editForm.get(['imageFileUrl']).value,
      campaignId: this.editForm.get(['campaignId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICampaignCompBrand>>) {
    result.subscribe((res: HttpResponse<ICampaignCompBrand>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
