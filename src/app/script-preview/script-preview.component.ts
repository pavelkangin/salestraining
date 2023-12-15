import {Component, Input,Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NzButtonModule} from "ng-zorro-antd/button";
import { NzRateModule } from 'ng-zorro-antd/rate';
import { NzIconModule } from 'ng-zorro-antd/icon';
import {FormsModule} from "@angular/forms";
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';

@Component({
  selector: 'app-script-preview',
  standalone: true,
  imports: [CommonModule, NzButtonModule, NzRateModule, NzIconModule, FormsModule,NzDropDownModule],
  templateUrl: './script-preview.component.html',
  styleUrl: './script-preview.component.css'
})
export class ScriptPreviewComponent {

  public script:any={name:'',rate:2};
  @Input('currentScript')
  set currentScript(currentScript: any) {
    //do whatever you want with your data here, this data will be passed from parent component
    this.script=currentScript;
    console.log(this.script);
  }

  @Output() startEvent = new EventEmitter<any>();



  startScript(){
    this.startEvent.emit(this.script);
  }

}
