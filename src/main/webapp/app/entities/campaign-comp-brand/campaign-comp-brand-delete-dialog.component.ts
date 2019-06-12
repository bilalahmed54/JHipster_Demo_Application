import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';
import { CampaignCompBrandService } from './campaign-comp-brand.service';

@Component({
  selector: 'jhi-campaign-comp-brand-delete-dialog',
  templateUrl: './campaign-comp-brand-delete-dialog.component.html'
})
export class CampaignCompBrandDeleteDialogComponent {
  campaignCompBrand: ICampaignCompBrand;

  constructor(
    protected campaignCompBrandService: CampaignCompBrandService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.campaignCompBrandService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'campaignCompBrandListModification',
        content: 'Deleted an campaignCompBrand'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-campaign-comp-brand-delete-popup',
  template: ''
})
export class CampaignCompBrandDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ campaignCompBrand }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CampaignCompBrandDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.campaignCompBrand = campaignCompBrand;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/campaign-comp-brand', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/campaign-comp-brand', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
