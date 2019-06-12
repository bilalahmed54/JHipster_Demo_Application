/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterBaseAppTestModule } from '../../../test.module';
import { CampaignSocialComponentUpdateComponent } from 'app/entities/campaign-social-component/campaign-social-component-update.component';
import { CampaignSocialComponentService } from 'app/entities/campaign-social-component/campaign-social-component.service';
import { CampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';

describe('Component Tests', () => {
  describe('CampaignSocialComponent Management Update Component', () => {
    let comp: CampaignSocialComponentUpdateComponent;
    let fixture: ComponentFixture<CampaignSocialComponentUpdateComponent>;
    let service: CampaignSocialComponentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterBaseAppTestModule],
        declarations: [CampaignSocialComponentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CampaignSocialComponentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CampaignSocialComponentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampaignSocialComponentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CampaignSocialComponent(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CampaignSocialComponent();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
