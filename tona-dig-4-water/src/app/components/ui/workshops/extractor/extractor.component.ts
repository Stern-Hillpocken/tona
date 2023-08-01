import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { AlertService } from 'src/app/shared/alert.service';

@Component({
  selector: 'app-extractor',
  templateUrl: './extractor.component.html',
  styleUrls: ['./extractor.component.scss', '../workshops.scss']
})
export class ExtractorComponent {

  @Input()
  expedition!: Expedition;

  @Output()
  dragEnterEmitter: EventEmitter<string> = new EventEmitter();

  private _slideNumber: number = 2;

  slideDisplayed: number = 0;

  constructor(private alertService: AlertService){}

  onDragEnter(zoneName: DragEvent): void {
    let realClassName = (zoneName.target as HTMLDivElement).className.substring("single-dice-storage-zone ".length);
    this.dragEnterEmitter.emit(realClassName);
  }

  slide(direction: string): void {
    if(direction === "left"){
      if(this.slideDisplayed === 0) this.slideDisplayed = this._slideNumber-1;
      else this.slideDisplayed --;
    }else{
      if(this.slideDisplayed >= this._slideNumber-1) this.slideDisplayed = 0;
      else this.slideDisplayed ++;
    }
  }

  alert(workshopName: string): void {
    let title: string = "";
    let text: string = "";
    if(workshopName === "auger"){
      title = "Foreuse";
      text = "Somme des valeurs déposées pour faire avancer la foreuse. Dois être entre X et X valeurs pour prosuire."
    }
    this.alertService.udpate(title, text);
  }

}
