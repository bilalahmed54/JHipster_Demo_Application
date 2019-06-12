import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAnswer, Answer } from 'app/shared/model/answer.model';
import { AnswerService } from './answer.service';
import { IRespondent } from 'app/shared/model/respondent.model';
import { RespondentService } from 'app/entities/respondent';
import { ICampaign } from 'app/shared/model/campaign.model';
import { CampaignService } from 'app/entities/campaign';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';

@Component({
  selector: 'jhi-answer-update',
  templateUrl: './answer-update.component.html'
})
export class AnswerUpdateComponent implements OnInit {
  answer: IAnswer;
  isSaving: boolean;

  respondents: IRespondent[];

  campaigns: ICampaign[];

  questions: IQuestion[];

  editForm = this.fb.group({
    id: [],
    answer: [null, [Validators.required, Validators.maxLength(1000)]],
    subQuestionId: [],
    respondentId: [],
    campaignId: [],
    questionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected answerService: AnswerService,
    protected respondentService: RespondentService,
    protected campaignService: CampaignService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ answer }) => {
      this.updateForm(answer);
      this.answer = answer;
    });
    this.respondentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRespondent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRespondent[]>) => response.body)
      )
      .subscribe((res: IRespondent[]) => (this.respondents = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.campaignService
      .query({ filter: 'answer-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICampaign[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICampaign[]>) => response.body)
      )
      .subscribe(
        (res: ICampaign[]) => {
          if (!this.answer.campaignId) {
            this.campaigns = res;
          } else {
            this.campaignService
              .find(this.answer.campaignId)
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
    this.questionService
      .query({ filter: 'answer-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestion[]>) => response.body)
      )
      .subscribe(
        (res: IQuestion[]) => {
          if (!this.answer.questionId) {
            this.questions = res;
          } else {
            this.questionService
              .find(this.answer.questionId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IQuestion>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IQuestion>) => subResponse.body)
              )
              .subscribe(
                (subRes: IQuestion) => (this.questions = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(answer: IAnswer) {
    this.editForm.patchValue({
      id: answer.id,
      answer: answer.answer,
      subQuestionId: answer.subQuestionId,
      respondentId: answer.respondentId,
      campaignId: answer.campaignId,
      questionId: answer.questionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const answer = this.createFromForm();
    if (answer.id !== undefined) {
      this.subscribeToSaveResponse(this.answerService.update(answer));
    } else {
      this.subscribeToSaveResponse(this.answerService.create(answer));
    }
  }

  private createFromForm(): IAnswer {
    const entity = {
      ...new Answer(),
      id: this.editForm.get(['id']).value,
      answer: this.editForm.get(['answer']).value,
      subQuestionId: this.editForm.get(['subQuestionId']).value,
      respondentId: this.editForm.get(['respondentId']).value,
      campaignId: this.editForm.get(['campaignId']).value,
      questionId: this.editForm.get(['questionId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswer>>) {
    result.subscribe((res: HttpResponse<IAnswer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackRespondentById(index: number, item: IRespondent) {
    return item.id;
  }

  trackCampaignById(index: number, item: ICampaign) {
    return item.id;
  }

  trackQuestionById(index: number, item: IQuestion) {
    return item.id;
  }
}
