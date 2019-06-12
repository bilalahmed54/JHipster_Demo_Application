export interface IQuestion {
  id?: number;
  jsonBody?: string;
  campaignId?: number;
}

export class Question implements IQuestion {
  constructor(public id?: number, public jsonBody?: string, public campaignId?: number) {}
}
