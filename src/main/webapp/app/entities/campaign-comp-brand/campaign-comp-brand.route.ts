import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';
import { CampaignCompBrandService } from './campaign-comp-brand.service';
import { CampaignCompBrandComponent } from './campaign-comp-brand.component';
import { CampaignCompBrandDetailComponent } from './campaign-comp-brand-detail.component';
import { CampaignCompBrandUpdateComponent } from './campaign-comp-brand-update.component';
import { CampaignCompBrandDeletePopupComponent } from './campaign-comp-brand-delete-dialog.component';
import { ICampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';

@Injectable({ providedIn: 'root' })
export class CampaignCompBrandResolve implements Resolve<ICampaignCompBrand> {
  constructor(private service: CampaignCompBrandService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICampaignCompBrand> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CampaignCompBrand>) => response.ok),
        map((campaignCompBrand: HttpResponse<CampaignCompBrand>) => campaignCompBrand.body)
      );
    }
    return of(new CampaignCompBrand());
  }
}

export const campaignCompBrandRoute: Routes = [
  {
    path: '',
    component: CampaignCompBrandComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CampaignCompBrands'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CampaignCompBrandDetailComponent,
    resolve: {
      campaignCompBrand: CampaignCompBrandResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CampaignCompBrands'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CampaignCompBrandUpdateComponent,
    resolve: {
      campaignCompBrand: CampaignCompBrandResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CampaignCompBrands'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CampaignCompBrandUpdateComponent,
    resolve: {
      campaignCompBrand: CampaignCompBrandResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CampaignCompBrands'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const campaignCompBrandPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CampaignCompBrandDeletePopupComponent,
    resolve: {
      campaignCompBrand: CampaignCompBrandResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CampaignCompBrands'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
