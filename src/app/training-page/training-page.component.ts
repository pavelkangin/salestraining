import {Component, EventEmitter, HostListener, OnInit, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzIconModule } from 'ng-zorro-antd/icon';
import {ScriptPreviewComponent} from "../script-preview/script-preview.component";
import {ScriptDialogueComponent} from "../script-dialogue/script-dialogue.component";

@Component({
  selector: 'app-training-page',
  standalone: true,
  imports: [CommonModule, NzLayoutModule, NzGridModule, NzButtonModule, NzDropDownModule, NzIconModule, ScriptPreviewComponent, ScriptDialogueComponent],
  templateUrl: './training-page.component.html',
  styleUrl: './training-page.component.css'
})
export class TrainingPageComponent implements OnInit{

  public screenWidth: any;
  public screenHeight: any;



  public showDesc= false;
  public showTrain=false;
  public scripts=[
    {id:1,selected:true,rate:3,name:'Входящие звонки',description:'Входящий звонок потенциального покупателю. Цель диалога - пригласить покупателя в офис для обсуждения задачи и заключения договора.'},
    {id:2,selected:false,rate:3,name:'Встречи',description: ''},
    {id:3,selected:false,rate:3,name:'Злобные переговоры',description: ''}
  ]

  public currentScript:any;

  showDescView(script:any){
    if (this.showTrain) return;
    this.showDesc=true;
    this.currentScript=script;
    this.selectScript();
  }

  selectScript(){
    for(let i=0;i<this.scripts.length;i++){
      if (this.scripts[i].id===this.currentScript.id){
        this.scripts[i].selected=true
      }
      else {
        this.scripts[i].selected=false;
      }
    }
  }

  doStartScript(script: any) {
    this.showDesc=false;
    this.showTrain=true;
  }


  doBack(script: any) {
    this.showDesc=true;
    this.showTrain=false;
  }


  ngOnInit() {
    this.screenWidth = window.innerWidth;
    this.screenHeight = window.innerHeight-80;
    if (this.scripts.length>0){
      this.scripts[0].selected=true;
      this.currentScript=this.scripts[0];
      this.showDescView(this.currentScript);
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event:any) {
    this.screenWidth = window.innerWidth;
    this.screenHeight = window.innerHeight-80;
  }

}
