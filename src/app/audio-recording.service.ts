import { Injectable } from '@angular/core';
import * as RecordRTC from "recordrtc";
import moment from "moment";
import { Observable, Subject } from "rxjs";

interface RecordedAudioOutput {
  blob: Blob;
  title: string;
}

@Injectable({
  providedIn: 'root'
})
export class AudioRecordingService {

  private stream:any;
  private recorder:any;
  private interval:any;
  private startTime:any;
  private currentTime:any;
  private _recorded = new Subject<RecordedAudioOutput>();
  private _recordingTime = new Subject<string>();
  private _recordingFailed = new Subject<string>();

  getRecordedBlob(): Observable<RecordedAudioOutput> {
    return this._recorded.asObservable();
  }

  getRecordedTime(): Observable<string> {
    return this._recordingTime.asObservable();
  }

  recordingFailed(): Observable<string> {
    return this._recordingFailed.asObservable();
  }

  startRecording() {
    this.soundDetected=false;
    if (this.recorder) {
      // It means recording is already started or it is already recording something
      return;
    }

    //this._recordingTime.next('00:00');
    navigator.mediaDevices
      .getUserMedia({ audio: true })
      .then(s => {
        this.stream = s;
        this.record();
      })
      .catch(error => {
        this._recordingFailed.next(error);
      });
  }

  abortRecording() {
    this.stopMedia();
  }

  animId:any;
  soundDetected = false;

  private record() {
    const audioContext = new AudioContext();
    const audioStreamSource = audioContext.createMediaStreamSource(this.stream);
    const analyser = audioContext.createAnalyser();
    analyser.minDecibels = -45;
    audioStreamSource.connect(analyser);
    const bufferLength = analyser.frequencyBinCount;
    const domainData = new Uint8Array(bufferLength);

    this.recorder = new RecordRTC.StereoAudioRecorder(this.stream, {
      type: "audio",
      mimeType: "audio/webm"
    });





    const detectSound = () => {
      if (this.soundDetected) {
        return
      }

      //soundDetected=false;
      analyser.getByteFrequencyData(domainData);

      for (let i = 0; i < bufferLength; i++) {
        const value = domainData[i];
        if (domainData[i] > 0) {
          this.soundDetected = true
        }
      }
      //console.log(this.soundDetected);
    };

    this.animId=setInterval(detectSound,50);

    //window.requestAnimationFrame(detectSound);

    this.recorder.record();
    if (!this.startTime) {
      this.startTime = moment();
      this.currentTime=moment();
    }
    this.interval = setInterval(() => {
      this.currentTime = this.currentTime.add(1,'seconds');
      const diffTime = moment.duration(this.currentTime.diff(this.startTime));
      const time =
        this.toString(diffTime.minutes()) +
        ":" +
        this.toString(diffTime.seconds());
      this._recordingTime.next(time);
    }, 1000);

  }

  private toString(value:any) {
    let val = value;
    if (!value) val = "00";
    if (value < 10) val = "0" + value;
    return val;
  }

  stopRecording() {
    if (this.recorder) {
      this.recorder.stop(
          (blob:any) => {
          if (this.startTime) {
            const mp3Name = encodeURIComponent(
              "audio_" + new Date().getTime() + ".mp3"
            );
            this.stopMedia();
            this._recorded.next({ blob: blob, title: mp3Name });
          }
        },
        () => {
          this.stopMedia();
          this._recordingFailed.next('');
        }
      );
    }
  }

  private stopMedia() {
    if (this.recorder) {
      this.recorder = null;
      clearInterval(this.interval);
      window.cancelAnimationFrame(this.animId);
      if (this.stream) {
        this.stream.getAudioTracks().forEach((track:any) => {
          track.stop();
        });
        this.stream = null;
      }
    }
  }




}
