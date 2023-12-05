import { Routes } from '@angular/router';
import {LoginFormComponent} from "./login-form/login-form.component";
import {RegisterFormComponent} from "./register-form/register-form.component";
import {RequestResetFormComponent} from "./request-reset-form/request-reset-form.component";
import {ApplyResetFormComponent} from "./apply-reset-form/apply-reset-form.component";
import {TrainingPageComponent} from "./training-page/training-page.component";

export const routes: Routes = [
  {path: 'login',component: LoginFormComponent},
  {path: 'register',component: RegisterFormComponent},
  {path: 'reset',component: RequestResetFormComponent},
  {path: 'apply',component: ApplyResetFormComponent},
  {path: 'training', component:TrainingPageComponent }
];

