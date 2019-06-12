export interface IAnswer {
  id?: number;
  answer?: string;
  subQuestionId?: number;
  respondentId?: number;
  campaignId?: number;
  questionId?: number;
}

export class Answer implements IAnswer {
  constructor(
    public id?: number,
    public answer?: string,
    public subQuestionId?: number,
    public respondentId?: number,
    public campaignId?: number,
    public questionId?: number
  ) {}
}
