import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { ICampaign } from 'app/shared/model/campaign.model';
import { CampaignService } from 'app/entities/campaign';

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
  question: IQuestion;
  isSaving: boolean;

  campaigns: ICampaign[];

  editForm = this.fb.group({
    id: [],
    jsonBody: [null, [Validators.required, Validators.maxLength(8000)]],
    campaignId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected questionService: QuestionService,
    protected campaignService: CampaignService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ question }) => {
      this.updateForm(question);
      this.question = question;
    });
    this.campaignService
      .query({ filter: 'question-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICampaign[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICampaign[]>) => response.body)
      )
      .subscribe(
        (res: ICampaign[]) => {
          if (!this.question.campaignId) {
            this.campaigns = res;
          } else {
            this.campaignService
              .find(this.question.campaignId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICampaign>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICampaign>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICampaign) => (this.campaigns = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(question: IQuestion) {
    this.editForm.patchValue({
      id: question.id,
      jsonBody: question.jsonBody,
      campaignId: question.campaignId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const question = this.createFromForm();
    if (question.id !== undefined) {
      this.subscribeToSaveResponse(this.questionService.update(question));
    } else {
      this.subscribeToSaveResponse(this.questionService.create(question));
    }
  }

  private createFromForm(): IQuestion {
    const entity = {
      ...new Question(),
      id: this.editForm.get(['id']).value,
      jsonBody: this.editForm.get(['jsonBody']).value,
      campaignId: this.editForm.get(['campaignId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>) {
    result.subscribe((res: HttpResponse<IQuestion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
