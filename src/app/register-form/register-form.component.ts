import { Component, Injectable, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzFormModule } from 'ng-zorro-antd/form';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NzInputModule } from 'ng-zorro-antd/input';
import { FormControl, FormGroup, NonNullableFormBuilder, Validators } from '@angular/forms';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzAlertModule } from 'ng-zorro-antd/alert';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NgRecaptcha3Service } from 'ng-recaptcha3';
import  {HttpClient } from "@angular/common/http";
import { AlertComponent } from '../alert/alert.component';
import { error } from '@ant-design/icons-angular';
import { RestOutline } from '@ant-design/icons-angular/icons';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [
    CommonModule,
    NzFormModule,
    FormsModule,
    ReactiveFormsModule,
    NzIconModule,
    NzAlertModule,
    NzInputModule,
    NzButtonModule,
    NzCheckboxModule,
    AlertComponent,

  ],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css',
 
})


export class RegisterFormComponent implements OnInit  {


  errorTitle:any;

  validateForm: FormGroup<{
    name: FormControl<string>;
    email: FormControl<string>;
    password: FormControl<string>;
    token:FormControl<string>;
  }> = this.fb.group({
    name: ['', [Validators.required]],
    email: ['', [Validators.required]],
    password: ['', [Validators.required]],
    token:['']
  });
    isDisabled = false;
    closeAlert=false;
    
  //окно ошибки, повторное появление 
  public showErrorPopUp() {
    this.isDisabled=false;
    this.closeAlert=false;
    setTimeout(() => {
    // set all the values..
    this.isDisabled=false;
    this.closeAlert=true;
    }, 10);
  };



  submitForm(): void {
    if (this.validateForm.valid) {
      this.isDisabled=true;
      console.log('submit', this.validateForm.value);
      this.recaptcha3.getToken({ action: 'registerAction' }).then(token => {
        console.log(token)
        this.http.post('http://st.iptp.net/api/register',this.validateForm.value).subscribe({
          next:data => {
            let resp=data as RegisterInfo
            if (resp.status>=0){
              localStorage.setItem('UserInfo', JSON.stringify(data));
              window.location.hash='#/training';
            }
            else{
              console.error('server unavailable', error);
              this.errorTitle ='Сервер недоступен'
              this.showErrorPopUp();
            }
          },
          error:error => {
            console.error('server error', error);
            this.errorTitle="Ошибка Сервера";
            this.showErrorPopUp();
          }
        })
      }, error => {
        console.log('captcha error: '+error);
        this.errorTitle="Ошибка captcha";
        this.showErrorPopUp();
      })
    } else {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

  constructor(private fb: NonNullableFormBuilder,
              private recaptcha3: NgRecaptcha3Service,
              private http: HttpClient) {}

  ngOnInit() {
    this.recaptcha3.init('6LfQyiQpAAAAABDplMm3u5azziBi5z2OdhBj9ZNf').then(status => {
      // status: success/error
      // success - script is loaded and greaptcha is ready
      // error - script is not loaded
      console.log(status)
      
    })
  }

  public ngOnDestroy() {
    this.recaptcha3.destroy();
  }
}

export interface RegisterInfo{
  status:number;
  message:string;
}

