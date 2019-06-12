/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterBaseAppTestModule } from '../../../test.module';
import { CampaignSocialComponentDeleteDialogComponent } from 'app/entities/campaign-social-component/campaign-social-component-delete-dialog.component';
import { CampaignSocialComponentService } from 'app/entities/campaign-social-component/campaign-social-component.service';

describe('Component Tests', () => {
  describe('CampaignSocialComponent Management Delete Component', () => {
    let comp: CampaignSocialComponentDeleteDialogComponent;
    let fixture: ComponentFixture<CampaignSocialComponentDeleteDialogComponent>;
    let service: CampaignSocialComponentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterBaseAppTestModule],
        declarations: [CampaignSocialComponentDeleteDialogComponent]
      })
        .overrideTemplate(CampaignSocialComponentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CampaignSocialComponentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampaignSocialComponentService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
