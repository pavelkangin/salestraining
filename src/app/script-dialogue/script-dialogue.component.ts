import {
  AfterViewInit,
  Component,
  ElementRef, EventEmitter,
  HostListener,
  Input,
  NgZone,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzGridModule } from 'ng-zorro-antd/grid';
import {NzIconModule} from "ng-zorro-antd/icon";
import { Inject }  from '@angular/core';
import { DOCUMENT } from '@angular/common';
import {NgScrollbar, NgScrollbarModule} from 'ngx-scrollbar';


@Component({
  selector: 'app-script-dialogue',
  standalone: true,
  imports: [CommonModule, NzGridModule, NzIconModule,NgScrollbarModule],
  templateUrl: './script-dialogue.component.html',
  styleUrl: './script-dialogue.component.css'
})
export class ScriptDialogueComponent implements OnInit,AfterViewInit {

  public script:any={name:''};
  private document: Document;
  @Input('currentScript')
  set currentScript(currentScript: any) {
    this.script=currentScript;
    console.log(this.script);
  }

  public screenHeight: any;

  @ViewChild(NgScrollbar) scrollbarRef!: NgScrollbar;

  ngAfterViewInit() {
    this.scrollbarRef.scrollTo({bottom:0})
  }


  ngOnInit() {
    this.screenHeight = window.innerHeight-210-this.getCCHeight();

  }
  @HostListener('window:resize', ['$event'])
  onResize(event:any) {
    this.screenHeight = window.innerHeight-210-this.getCCHeight();
  }

  public getCCHeight(){
    let cc=this.document.getElementById('control-container');
    let h=0;
    if (cc!=null){
      h=cc.getBoundingClientRect().height;
      console.log(h);
    }
    return h;
  }

  @Output() backEvent = new EventEmitter<any>();
  public showDesc(){
    this.backEvent.emit(this.script);
  }


  constructor(@Inject(DOCUMENT) document: Document,private zone: NgZone) {
    this.document=document;

  }


}
