import { IAnswer } from 'app/shared/model/answer.model';

export const enum UserTypes {
  TEST = 'TEST',
  CONTROL = 'CONTROL'
}

export interface IRespondent {
  id?: number;
  uuid?: string;
  userType?: UserTypes;
  isVideoRecorded?: boolean;
  campaignAnswers?: IAnswer[];
  campaignId?: number;
}

export class Respondent implements IRespondent {
  constructor(
    public id?: number,
    public uuid?: string,
    public userType?: UserTypes,
    public isVideoRecorded?: boolean,
    public campaignAnswers?: IAnswer[],
    public campaignId?: number
  ) {
    this.isVideoRecorded = this.isVideoRecorded || false;
  }
}
