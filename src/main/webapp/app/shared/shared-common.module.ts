import { NgModule } from '@angular/core';

import { JhipsterBaseAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [JhipsterBaseAppSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [JhipsterBaseAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhipsterBaseAppSharedCommonModule {}
