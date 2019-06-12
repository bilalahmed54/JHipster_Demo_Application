/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterBaseAppTestModule } from '../../../test.module';
import { CampaignCompBrandComponent } from 'app/entities/campaign-comp-brand/campaign-comp-brand.component';
import { CampaignCompBrandService } from 'app/entities/campaign-comp-brand/campaign-comp-brand.service';
import { CampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';

describe('Component Tests', () => {
  describe('CampaignCompBrand Management Component', () => {
    let comp: CampaignCompBrandComponent;
    let fixture: ComponentFixture<CampaignCompBrandComponent>;
    let service: CampaignCompBrandService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterBaseAppTestModule],
        declarations: [CampaignCompBrandComponent],
        providers: []
      })
        .overrideTemplate(CampaignCompBrandComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CampaignCompBrandComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampaignCompBrandService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CampaignCompBrand(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.campaignCompBrands[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
