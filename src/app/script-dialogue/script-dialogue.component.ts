import {
  AfterViewInit,
  Component,
  ElementRef, EventEmitter,
  HostListener,
  Input,
  NgZone, OnDestroy,
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
import {NzRateModule} from "ng-zorro-antd/rate";
import {FormsModule} from "@angular/forms";
import { AudioRecordingService } from "../audio-recording.service";
import {DomSanitizer} from "@angular/platform-browser";


interface RecordedAudioOutput {
  blob: Blob;
  title: string;
}

@Component({
  selector: 'app-script-dialogue',
  standalone: true,
  imports: [CommonModule, NzGridModule, NzIconModule, NgScrollbarModule, NzRateModule, FormsModule],
  templateUrl: './script-dialogue.component.html',
  styleUrl: './script-dialogue.component.css'
})
export class ScriptDialogueComponent implements OnInit,AfterViewInit,OnDestroy {

  public script:any={name:''};
  private document: Document;
  @Input('currentScript')
  set currentScript(currentScript: any) {
    this.script=currentScript;
  }

  public showSentence=true;
  public paused=false;
  public currentSentence={id:1,sentence:'Хорошо, вы для себя? Здравствуйте! Меня зовут Лана, чем могу вам помочь?',client:false}
  public dialogue=[
    {id:1,sentence:'Алло!',client:true},
    {id:2,sentence:'Здравствуйте! Меня зовут Лана, чем могу вам помочь?',client:false},
    {id:3,sentence:'Звоню по поводу аренды квартиры в Сормово!',client:true}
  ]


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
    this.stopRecording();
    this.backEvent.emit(this.script);
  }


  constructor(@Inject(DOCUMENT) document: Document,
              private zone: NgZone,
              private audioRecordingService: AudioRecordingService,
              private sanitizer: DomSanitizer) {
    this.document=document;

    this.audioRecordingService
        .recordingFailed()
        .subscribe(() => (this.isRecording = false));
    this.audioRecordingService
        .getRecordedTime()
        .subscribe(time => (this.recordedTime = time));
    this.audioRecordingService.getRecordedBlob().subscribe(data => {
      this.recordedBlobs.push(data);
       let blobUrl = this.sanitizer.bypassSecurityTrustUrl(
          URL.createObjectURL(data.blob)
      );
      this.recordedChunks.push(blobUrl);
      /*for (let i=0;i<this.recordedBlobs.length;i++){
        console.log(this.recordedBlobs[i]);
      }*/
    });


  }


  recordedChunks=Array<any>();
  recordedBlobs=Array<RecordedAudioOutput>();

  toggleShowSent() {
    this.showSentence=!this.showSentence;
  }

  getEyeType() {
    return this.showSentence?'eye-invisible':'eye';
  }

  getPauseType() {
    return this.paused? 'play-circle' : 'pause';
  }

  togglePause() {
    this.paused=!this.paused;
  }

  isRecording = false;
  recordedTime='00:00';
  //blobUrl:any;



  startRecording() {
    if (!this.isRecording) {
      this.isRecording = true;
      this.audioRecordingService.startRecording();
    }
    else {
      this.audioRecordingService.stopRecording();
      this.isRecording=false;
      if (this.audioRecordingService.soundDetected) {
        console.log('sound detected');
      }
      else {
        console.log('sound not detected');
      }
    }
  }

  abortRecording() {
    if (this.isRecording) {
      this.isRecording = false;
      this.audioRecordingService.abortRecording();
    }
  }

  stopRecording() {
    if (this.isRecording) {
      this.audioRecordingService.stopRecording();
      this.isRecording = false;

    }
  }



  ngOnDestroy(): void {
    this.abortRecording();
  }






}
