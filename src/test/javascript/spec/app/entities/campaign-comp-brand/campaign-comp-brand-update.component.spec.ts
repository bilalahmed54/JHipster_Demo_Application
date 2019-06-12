/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterBaseAppTestModule } from '../../../test.module';
import { CampaignCompBrandUpdateComponent } from 'app/entities/campaign-comp-brand/campaign-comp-brand-update.component';
import { CampaignCompBrandService } from 'app/entities/campaign-comp-brand/campaign-comp-brand.service';
import { CampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';

describe('Component Tests', () => {
  describe('CampaignCompBrand Management Update Component', () => {
    let comp: CampaignCompBrandUpdateComponent;
    let fixture: ComponentFixture<CampaignCompBrandUpdateComponent>;
    let service: CampaignCompBrandService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterBaseAppTestModule],
        declarations: [CampaignCompBrandUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CampaignCompBrandUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CampaignCompBrandUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampaignCompBrandService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CampaignCompBrand(123);
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
        const entity = new CampaignCompBrand();
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
