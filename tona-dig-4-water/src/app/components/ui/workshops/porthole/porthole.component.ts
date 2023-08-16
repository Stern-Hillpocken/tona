import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { AlertService } from 'src/app/shared/alert.service';

@Component({
  selector: 'app-porthole',
  templateUrl: './porthole.component.html',
  styleUrls: ['./porthole.component.scss', '../workshops.scss']
})
export class PortholeComponent {

  @Input()
  expedition!: Expedition;

  @Output()
  dragEnterEmitter: EventEmitter<string> = new EventEmitter();

  @Output()
  takeObjectEmitter: EventEmitter <string> = new EventEmitter();

  private _slideNumber: number = 3;

  slideDisplayed: number = 0;

  constructor(private alertService: AlertService){}

  ngOnInit(): void {
    if(this.expedition.pod.rooms[4].health < this.expedition.pod.rooms[4].health || this.expedition.pod.rooms[4].status !== ""){
      this._slideNumber ++;
    }
  }

  onDragEnter(zoneName: DragEvent, subName: string): void {
    let realClassName = (zoneName.target as HTMLDivElement).className.substring("single-dice-storage-zone ".length);
    this.dragEnterEmitter.emit(realClassName + " " + subName);
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
    if(workshopName === "radar"){
      title = "Radar à vibrations";
      text = "Mettre un dé de valeur 2 ou 3 pour remonter d'une dent l'une des roues crantées. Permet de connaître la position ou le type d'ennemi pour le ou les prochains tours."
    } else if(workshopName === "spice-dose"){
      title = "Dose d'épice";
      text = "Permet de se soigner."
    } else if(workshopName === "hull-diagnostic-panel"){
      title = "Stéthoscope";
      text = "Mettre un dé de valeur 4 ou 5 pour remonter d'une dent l'une des roues crantées. Permet de connatre les zones ou les effets des prochains évènements."
    }
    this.alertService.udpate(title, text);
  }

  takeObject(objectName: string): void {
    this.takeObjectEmitter.emit(objectName);
  }

  isWorkshopUnfinished(index: number): boolean {
    let zeroCount = 0;
    for(let i = 0; i < this.expedition.pod.rooms[4].workshops[index].storedDice.length; i++){
      if(this.expedition.pod.rooms[4].workshops[index].storedDice[i] === 0) zeroCount ++;
    }
    if(zeroCount === 0) return false;
    return true;
  }

  reparationReceive(): void {
    this.dragEnterEmitter.emit("porthole-reparation");
  }

}
