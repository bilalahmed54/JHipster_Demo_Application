/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterBaseAppTestModule } from '../../../test.module';
import { CampaignSocialComponentComponent } from 'app/entities/campaign-social-component/campaign-social-component.component';
import { CampaignSocialComponentService } from 'app/entities/campaign-social-component/campaign-social-component.service';
import { CampaignSocialComponent } from 'app/shared/model/campaign-social-component.model';

describe('Component Tests', () => {
  describe('CampaignSocialComponent Management Component', () => {
    let comp: CampaignSocialComponentComponent;
    let fixture: ComponentFixture<CampaignSocialComponentComponent>;
    let service: CampaignSocialComponentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterBaseAppTestModule],
        declarations: [CampaignSocialComponentComponent],
        providers: []
      })
        .overrideTemplate(CampaignSocialComponentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CampaignSocialComponentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampaignSocialComponentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CampaignSocialComponent(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.campaignSocialComponents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
