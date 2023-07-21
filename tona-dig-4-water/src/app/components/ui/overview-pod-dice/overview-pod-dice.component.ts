import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { Popup } from 'src/app/models/popup.model';
import { User } from 'src/app/models/user.model';
import { PopupService } from 'src/app/shared/popup.service';

@Component({
  selector: 'app-overview-pod-dice',
  templateUrl: './overview-pod-dice.component.html',
  styleUrls: ['./overview-pod-dice.component.scss']
})
export class OverviewPodDiceComponent {

  @Input()
  expedition!: Expedition;

  @Input()
  user!: User;

  @Output()
  rerollEmitter: EventEmitter<void> = new EventEmitter();

  @Output()
  dragStartEmitter: EventEmitter<{value: number, startZone: string}> = new EventEmitter();

  @Output()
  dragEnterEmitter: EventEmitter<string> = new EventEmitter();

  @Output()
  dragEndEmitter: EventEmitter<void> = new EventEmitter();

  constructor(
    private popup: PopupService
  ){}

  onReroll(): void {
    if(this.expedition.crew[0].majagaba.rerollLeft > 0){
      this.rerollEmitter.emit();
    }else{
      this.popup.add(new Popup("🎲 Plus assez de relance.", "error"));
    }
  }

  onDragStart(img: DragEvent): void{
    (img.target as HTMLImageElement).style.opacity = "0.2";
    this.dragStartEmitter.emit({
      value: parseInt((img.target as HTMLImageElement).alt),
      startZone: ((img.target as HTMLImageElement).parentNode as HTMLDivElement).className
    });
  }

  onDragEnter(zone: DragEvent): void {
    this.dragEnterEmitter.emit((zone.target as HTMLDivElement).className);
  }

  onDragEnd(img: DragEvent): void {
    (img.target as HTMLImageElement).style.opacity = "1";
    this.dragEndEmitter.emit();
  }

}
