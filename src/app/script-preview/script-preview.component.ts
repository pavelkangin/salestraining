import {Component, Input,Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NzButtonModule} from "ng-zorro-antd/button";



@Component({
  selector: 'app-script-preview',
  standalone: true,
    imports: [CommonModule, NzButtonModule],
  templateUrl: './script-preview.component.html',
  styleUrl: './script-preview.component.css'
})
export class ScriptPreviewComponent {

  public script:any;
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
