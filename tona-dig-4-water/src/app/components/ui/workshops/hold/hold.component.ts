import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { User } from 'src/app/models/user.model';
import { AlertService } from 'src/app/shared/alert.service';

@Component({
  selector: 'app-hold',
  templateUrl: './hold.component.html',
  styleUrls: ['./hold.component.scss', '../workshops.scss']
})
export class HoldComponent {

  @Input()
  expedition!: Expedition;

  @Input()
  user!: User;

  @Output()
  dragEnterEmitter: EventEmitter<string> = new EventEmitter();

  @Output()
  takeObjectEmitter: EventEmitter <string> = new EventEmitter();

  private _slideNumber: number = 3;

  slideDisplayed: number = 0;

  constructor(private alertService: AlertService){}

  onDragEnter(zoneName: DragEvent, zoneNumber: number): void {
    let realClassName = (zoneName.target as HTMLDivElement).className.substring("single-dice-storage-zone ".length);
    this.dragEnterEmitter.emit(realClassName+" "+zoneNumber);
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
    if(workshopName === "steam-regulator"){
      title = "Steam Regulator";
      text = "Pour avoir de quoi +1/-1, faire une suite de 3 dés"
    }
    this.alertService.udpate(title, text);
  }

  takeObject(objectName: string): void {
    this.takeObjectEmitter.emit(objectName);
  }

}
