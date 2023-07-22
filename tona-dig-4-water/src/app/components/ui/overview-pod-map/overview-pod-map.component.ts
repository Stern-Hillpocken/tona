import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { User } from 'src/app/models/user.model';
import { UtilsService } from 'src/app/shared/utils.service';

@Component({
  selector: 'app-overview-pod-map',
  templateUrl: './overview-pod-map.component.html',
  styleUrls: ['./overview-pod-map.component.scss']
})
export class OverviewPodMapComponent {

  @Input()
  expedition!: Expedition;

  @Input()
  user!: User;

  @Output()
  dragEnterEmitter: EventEmitter<string> = new EventEmitter();

  constructor(
    private utils: UtilsService
  ){}

  translateName(dbName: string): string {
    switch(dbName){
      case "armory": return "Armurerie";
      case "drill": return "Foreuse";
      case "extractor": return "Extracteur";
      case "hoist": return "Treuillage";
      case "hold": return "Câle";
      case "porthole": return "Hublot";
      default: return "error";
    }
  }

  onDragEnter(zone: string): void {
    this.dragEnterEmitter.emit(zone);

    for(let room = 0; room < document.getElementsByClassName("room").length; room++){
      (document.getElementsByClassName("room")[room] as HTMLDivElement).style.backgroundColor = "inherit";
    }
    if(this.utils.isZoneAdjacent(zone, this.user.majagaba.room)){
      (document.getElementsByClassName(zone)[0] as HTMLDivElement).style.backgroundColor = "yellow";
    }
  }
}
