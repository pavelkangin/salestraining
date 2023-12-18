import { ApplicationConfig, ErrorHandler, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { en_US, provideNzI18n } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { NzIconModule } from 'ng-zorro-antd/icon';
import {
  UserOutline,
  LockOutline,
  MailOutline
} from '@ant-design/icons-angular/icons';
import {IconDefinition} from "@ant-design/icons-angular";





const icons: IconDefinition[] = [
  UserOutline,
  LockOutline,
  MailOutline
];



registerLocaleData(en);

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideNzI18n(en_US),
    importProvidersFrom(FormsModule),
    importProvidersFrom(HttpClientModule),
    importProvidersFrom(NzIconModule.forRoot(icons)),
    provideAnimations()
  ]
};
