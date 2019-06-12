import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';
import { CampaignSocialComponentService } from './campaign-social-component.service';

@Component({
  selector: 'jhi-campaign-social-component-delete-dialog',
  templateUrl: './campaign-social-component-delete-dialog.component.html'
})
export class CampaignSocialComponentDeleteDialogComponent {
  campaignSocialComponent: ICampaignSocialComponent;

  constructor(
    protected campaignSocialComponentService: CampaignSocialComponentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.campaignSocialComponentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'campaignSocialComponentListModification',
        content: 'Deleted an campaignSocialComponent'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-campaign-social-component-delete-popup',
  template: ''
})
export class CampaignSocialComponentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ campaignSocialComponent }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CampaignSocialComponentDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.campaignSocialComponent = campaignSocialComponent;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/campaign-social-component', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/campaign-social-component', { outlets: { popup: null } }]);
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
