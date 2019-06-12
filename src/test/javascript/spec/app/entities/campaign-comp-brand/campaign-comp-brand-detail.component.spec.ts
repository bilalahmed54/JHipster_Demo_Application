/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterBaseAppTestModule } from '../../../test.module';
import { CampaignCompBrandDetailComponent } from 'app/entities/campaign-comp-brand/campaign-comp-brand-detail.component';
import { CampaignCompBrand } from 'app/shared/model/campaign-comp-brand.model';

describe('Component Tests', () => {
  describe('CampaignCompBrand Management Detail Component', () => {
    let comp: CampaignCompBrandDetailComponent;
    let fixture: ComponentFixture<CampaignCompBrandDetailComponent>;
    const route = ({ data: of({ campaignCompBrand: new CampaignCompBrand(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterBaseAppTestModule],
        declarations: [CampaignCompBrandDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CampaignCompBrandDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CampaignCompBrandDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.campaignCompBrand).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
