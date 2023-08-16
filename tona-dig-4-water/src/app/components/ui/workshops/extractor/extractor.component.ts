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

  ngOnInit(): void {
    if(this.expedition.pod.rooms[2].health < this.expedition.pod.rooms[2].health || this.expedition.pod.rooms[2].status !== ""){
      this._slideNumber ++;
    }
  }

  onDragEnter(zoneName: DragEvent, zoneNumber: number): void {
    let realClassName = (zoneName.target as HTMLDivElement).className.substring("single-dice-storage-zone ".length);
    let spot = "";
    if(zoneNumber >= 0) spot += " " + zoneNumber;
    this.dragEnterEmitter.emit(realClassName+spot);
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
      text = "Somme des valeurs déposées pour faire avancer la foreuse. Dois être entre X et X valeurs pour prosuire. Gain de minerais (dans les limites des stocks dispo) : Xd4 pour la zone exterieure, et Xd6 pour le coeur de la veine. Pour l'eau c'est Xd2 et Xd4. X = palier de profondeur."
    }else if(workshopName === "probe"){
      title = "Sonde";
      text = "Suite de valeur pour savoir où fouiller."
    }
    this.alertService.udpate(title, text);
  }

  reparationReceive(): void {
    this.dragEnterEmitter.emit("extractor-reparation");
  }

}
