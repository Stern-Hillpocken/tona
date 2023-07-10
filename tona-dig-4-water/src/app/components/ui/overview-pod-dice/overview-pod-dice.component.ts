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

  @Output()
  dragDieEmitter: EventEmitter<{value:number, className:string}> = new EventEmitter();

  dieValueDragged: number = 0;
  startDragPosition: string = "";
  lastDragEnterPosition: string = "";

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
    this.dieValueDragged = parseInt((img.target as HTMLImageElement).alt);
    this.startDragPosition = ((img.target as HTMLImageElement).parentNode as HTMLDivElement).className;
    (img.target as HTMLImageElement).style.opacity = "0.2";
  }

  onDragEnter(zone: DragEvent): void {
    this.lastDragEnterPosition = (zone.target as HTMLDivElement).className;
  }

  onDragEnd(img: DragEvent): void {
    if(this.lastDragEnterPosition && this.lastDragEnterPosition !== this.startDragPosition){
      this.dragDieEmitter.emit({value:this.dieValueDragged, className:this.lastDragEnterPosition});
    }
    (img.target as HTMLImageElement).style.opacity = "1";
    this.dieValueDragged = 0;
    this.startDragPosition = "";
    this.lastDragEnterPosition = "";
  }

}
