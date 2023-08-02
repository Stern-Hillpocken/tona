import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { AlertService } from 'src/app/shared/alert.service';

@Component({
  selector: 'app-hold',
  templateUrl: './hold.component.html',
  styleUrls: ['./hold.component.scss', '../workshops.scss']
})
export class HoldComponent {

  @Input()
  expedition!: Expedition;

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
    if(workshopName === "steam-blast"){
      title = "Soufflerie";
      text = "Mettre des dés de même valeurs. L'objet obtenu permet de donner 4 dés à l'entièreté du groupe pour le prochain tour."
    } else if(workshopName === "steam-switcher"){
      title = "Aiguilleur";
      text = "Mettre des dés différents. L'objet permet de donner un de ses dés à un autre membre."
    } else if(workshopName === "steam-regulator"){
      title = "Régulateur";
      text = "Pour avoir de quoi +1/-1, faire une suite de 3 dés."
    }
    this.alertService.udpate(title, text);
  }

  takeObject(objectName: string): void {
    this.takeObjectEmitter.emit(objectName);
  }

  isWorkshopUnfinished(index: number): boolean {
    let zeroCount = 0;
    for(let i = 0; i < this.expedition.pod.rooms[1].workshops[index].storedDice.length; i++){
      if(this.expedition.pod.rooms[1].workshops[index].storedDice[i] === 0) zeroCount ++;
    }
    if(zeroCount === 0) return false;
    return true;
  }

}
