import { Component, Input, Output, EventEmitter  } from '@angular/core';
import { NzAlertComponent, NzAlertModule } from 'ng-zorro-antd/alert';
import { CommonModule } from '@angular/common';
import { NzModalCloseComponent, NzModalTitleComponent } from 'ng-zorro-antd/modal';
import { error } from '@ant-design/icons-angular';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { RegisterFormComponent } from '../register-form/register-form.component';


@Component({
  selector: 'app-alert',
  standalone: true,
  templateUrl: './alert.component.html',
  styleUrl: './alert.component.css',
  imports: [
    NzAlertModule,
    CommonModule,
    RegisterFormComponent
  ],

})

export class AlertComponent {
  public alertError=String;
  public show = false;
  public title='';
  @Input('showComponent')
  set showComponent(showComponent: any) {
    this.show = showComponent;
    setTimeout(()=>{
      this.show=false;
      this.title='';
      console.log('closing popup')
    },10000)
  }

  @Input('errorTitle')
  set errorTitle(errorTitle: any) {
    this.title = errorTitle;
  }


  constructor() {}

  closeAlert() {
    this.show=false;
  }
}

