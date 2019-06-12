import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';

type EntityResponseType = HttpResponse<ICampaignSocialComponent>;
type EntityArrayResponseType = HttpResponse<ICampaignSocialComponent[]>;

@Injectable({ providedIn: 'root' })
export class CampaignSocialComponentService {
  public resourceUrl = SERVER_API_URL + 'api/campaign-social-components';

  constructor(protected http: HttpClient) {}

  create(campaignSocialComponent: ICampaignSocialComponent): Observable<EntityResponseType> {
    return this.http.post<ICampaignSocialComponent>(this.resourceUrl, campaignSocialComponent, { observe: 'response' });
  }

  update(campaignSocialComponent: ICampaignSocialComponent): Observable<EntityResponseType> {
    return this.http.put<ICampaignSocialComponent>(this.resourceUrl, campaignSocialComponent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICampaignSocialComponent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICampaignSocialComponent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
