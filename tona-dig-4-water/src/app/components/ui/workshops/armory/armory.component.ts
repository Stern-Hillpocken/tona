import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { AlertService } from 'src/app/shared/alert.service';

@Component({
  selector: 'app-armory',
  templateUrl: './armory.component.html',
  styleUrls: ['./armory.component.scss', '../workshops.scss']
})
export class ArmoryComponent {

  @Input()
  expedition!: Expedition;

  @Output()
  dragEnterEmitter: EventEmitter<string> = new EventEmitter();

  private _slideNumber: number = 2;

  slideDisplayed: number = 0;

  constructor(private alertService: AlertService){}

  onDragEnter(zoneName: DragEvent, zoneNumber: number): void {
    let realClassName = (zoneName.target as HTMLDivElement).className.substring("single-dice-storage-zone ".length);
    let spot = "";
    if(zoneNumber >= 0) spot += " " + zoneNumber;
    if(realClassName.startsWith("armory-shoot-enemy-")){
      this.dragEnterEmitter.emit(realClassName+spot);
    }
    
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
    if(workshopName === "cannon"){
      title = "Canon";
      text = "En fonction de la valeur, le canon tire sur une cible dans une zone précise. Une munition est utilisée."
    }else if(workshopName === "reload"){
      title = "Recharge";
      text = "Permet d'avoir de nouvelle munitions."
    }
    this.alertService.udpate(title, text);
  }

}
