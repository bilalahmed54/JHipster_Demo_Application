import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRespondent } from 'app/shared/model/respondent.model';

type EntityResponseType = HttpResponse<IRespondent>;
type EntityArrayResponseType = HttpResponse<IRespondent[]>;

@Injectable({ providedIn: 'root' })
export class RespondentService {
  public resourceUrl = SERVER_API_URL + 'api/respondents';

  constructor(protected http: HttpClient) {}

  create(respondent: IRespondent): Observable<EntityResponseType> {
    return this.http.post<IRespondent>(this.resourceUrl, respondent, { observe: 'response' });
  }

  update(respondent: IRespondent): Observable<EntityResponseType> {
    return this.http.put<IRespondent>(this.resourceUrl, respondent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRespondent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRespondent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
