import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';
import { CampaignSocialComponentService } from './campaign-social-component.service';
import { CampaignSocialComponentComponent } from './campaign-social-component.component';
import { CampaignSocialComponentDetailComponent } from './campaign-social-component-detail.component';
import { CampaignSocialComponentUpdateComponent } from './campaign-social-component-update.component';
import { CampaignSocialComponentDeletePopupComponent } from './campaign-social-component-delete-dialog.component';
import { ICampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';

@Injectable({ providedIn: 'root' })
export class CampaignSocialComponentResolve implements Resolve<ICampaignSocialComponent> {
  constructor(private service: CampaignSocialComponentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICampaignSocialComponent> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CampaignSocialComponent>) => response.ok),
        map((campaignSocialComponent: HttpResponse<CampaignSocialComponent>) => campaignSocialComponent.body)
      );
    }
    return of(new CampaignSocialComponent());
  }
}

export const campaignSocialComponentRoute: Routes = [
  {
    path: '',
    component: CampaignSocialComponentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CampaignSocialComponents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CampaignSocialComponentDetailComponent,
    resolve: {
      campaignSocialComponent: CampaignSocialComponentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CampaignSocialComponents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CampaignSocialComponentUpdateComponent,
    resolve: {
      campaignSocialComponent: CampaignSocialComponentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CampaignSocialComponents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CampaignSocialComponentUpdateComponent,
    resolve: {
      campaignSocialComponent: CampaignSocialComponentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CampaignSocialComponents'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const campaignSocialComponentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CampaignSocialComponentDeletePopupComponent,
    resolve: {
      campaignSocialComponent: CampaignSocialComponentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CampaignSocialComponents'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
