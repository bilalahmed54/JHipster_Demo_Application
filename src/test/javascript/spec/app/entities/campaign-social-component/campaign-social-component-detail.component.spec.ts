/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterBaseAppTestModule } from '../../../test.module';
import { CampaignSocialComponentDetailComponent } from 'app/entities/campaign-social-component/campaign-social-component-detail.component';
import { CampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';

describe('Component Tests', () => {
  describe('CampaignSocialComponent Management Detail Component', () => {
    let comp: CampaignSocialComponentDetailComponent;
    let fixture: ComponentFixture<CampaignSocialComponentDetailComponent>;
    const route = ({ data: of({ campaignSocialComponent: new CampaignSocialComponent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterBaseAppTestModule],
        declarations: [CampaignSocialComponentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CampaignSocialComponentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CampaignSocialComponentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.campaignSocialComponent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
