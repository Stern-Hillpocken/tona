import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { Popup } from 'src/app/models/popup.model';
import { PopupService } from 'src/app/shared/popup.service';

@Component({
  selector: 'app-overview-pod-dice',
  templateUrl: './overview-pod-dice.component.html',
  styleUrls: ['./overview-pod-dice.component.scss']
})
export class OverviewPodDiceComponent {

  @Input()
  expedition!: Expedition

  @Output()
  rerollEmitter: EventEmitter<boolean> = new EventEmitter();

  constructor(
    private popup: PopupService
  ){}

  onReroll(): void {
    if(this.expedition.crew[0].majagaba.rerollLeft > 0){
      this.rerollEmitter.emit(true);
    }else{
      this.popup.add(new Popup("Plus assez de relance.", "error")); // TODO emojipedia
    }
  }

  onDragStart(img: DragEvent): void{
    console.log("drag-start: "+(img.target as HTMLImageElement).alt);
    console.log(img.target);
    (img.target as HTMLImageElement).style.opacity = "0.2";
  }

  onDragEnter(zone: DragEvent): void {
    console.log("drag-enter");
    console.log(zone);
  }

  onDragEnd(img: DragEvent): void {
    console.log("drag-end");
    (img.target as HTMLImageElement).style.opacity = "1";
  }

}
