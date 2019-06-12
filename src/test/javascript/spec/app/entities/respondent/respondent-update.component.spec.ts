/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterBaseAppTestModule } from '../../../test.module';
import { RespondentUpdateComponent } from 'app/entities/respondent/respondent-update.component';
import { RespondentService } from 'app/entities/respondent/respondent.service';
import { Respondent } from 'app/shared/model/respondent.model';

describe('Component Tests', () => {
  describe('Respondent Management Update Component', () => {
    let comp: RespondentUpdateComponent;
    let fixture: ComponentFixture<RespondentUpdateComponent>;
    let service: RespondentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterBaseAppTestModule],
        declarations: [RespondentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RespondentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RespondentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RespondentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Respondent(123);
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
        const entity = new Respondent();
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
