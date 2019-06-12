import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';

type EntityResponseType = HttpResponse<ICampaignCompBrand>;
type EntityArrayResponseType = HttpResponse<ICampaignCompBrand[]>;

@Injectable({ providedIn: 'root' })
export class CampaignCompBrandService {
  public resourceUrl = SERVER_API_URL + 'api/campaign-comp-brands';

  constructor(protected http: HttpClient) {}

  create(campaignCompBrand: ICampaignCompBrand): Observable<EntityResponseType> {
    return this.http.post<ICampaignCompBrand>(this.resourceUrl, campaignCompBrand, { observe: 'response' });
  }

  update(campaignCompBrand: ICampaignCompBrand): Observable<EntityResponseType> {
    return this.http.put<ICampaignCompBrand>(this.resourceUrl, campaignCompBrand, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICampaignCompBrand>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICampaignCompBrand[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
