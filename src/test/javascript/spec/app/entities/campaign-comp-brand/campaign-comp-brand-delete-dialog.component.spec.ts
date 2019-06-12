/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterBaseAppTestModule } from '../../../test.module';
import { CampaignCompBrandDeleteDialogComponent } from 'app/entities/campaign-comp-brand/campaign-comp-brand-delete-dialog.component';
import { CampaignCompBrandService } from 'app/entities/campaign-comp-brand/campaign-comp-brand.service';

describe('Component Tests', () => {
  describe('CampaignCompBrand Management Delete Component', () => {
    let comp: CampaignCompBrandDeleteDialogComponent;
    let fixture: ComponentFixture<CampaignCompBrandDeleteDialogComponent>;
    let service: CampaignCompBrandService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterBaseAppTestModule],
        declarations: [CampaignCompBrandDeleteDialogComponent]
      })
        .overrideTemplate(CampaignCompBrandDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CampaignCompBrandDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampaignCompBrandService);
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
