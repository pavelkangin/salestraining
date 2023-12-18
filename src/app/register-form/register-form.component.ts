import { Component, ErrorHandler, Input, Output, EventEmitter, OnInit } from '@angular/core';
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
  styleUrl: './register-form.component.css'
})

    
export class RegisterFormComponent implements OnInit  {

  public alert=console.error(error);
  @Input('alertError')
  set alertError(alertError: any) {
    this.alert = alertError;
  } 

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

   isDisables: boolean = false;
   // http://st.iptp.net/api/register
  
  submitForm(): void {
    if (this.validateForm.valid) {
      this.isDisables=false;
      console.log('submit', this.validateForm.value);
      this.recaptcha3.getToken({ action: 'registerAction' }).then(token => {
        console.log(token)
        this.http.post('000http://st.iptp.net/api/register',this.validateForm.value).subscribe({
          next:data => {
            let resp=data as RegisterInfo
            if (resp.status>=0){
              localStorage.setItem('UserInfo', JSON.stringify(data));
              window.location.hash='#/training'; 
            }
            else{
              console.error('Ой, что-то сломалось', error);
              this.isDisables= true;
                   
            }
            
          },
          error:error => {
            console.error('Ошибка Сервера', error);
            this.isDisables= true;
           
          }
        })
      }, error => {
        // get error, e.g. if key is invalid

        console.log('captcha error: '+error);
        
        this.isDisables= true;
        
      })

    } else {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
          this.isDisables = false;
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




