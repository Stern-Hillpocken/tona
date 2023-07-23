import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-overview-pod-room-actions',
  templateUrl: './overview-pod-room-actions.component.html',
  styleUrls: ['./overview-pod-room-actions.component.scss']
})
export class OverviewPodRoomActionsComponent {

  @Input()
  expedition!: Expedition;

  @Input()
  user!: User;

  @Output()
  dragEnterEmitter: EventEmitter<string> = new EventEmitter();

  currentRoom(): number{
    switch (this.user.majagaba.room) {
      case "hoist": return 0;
      case "hold": return 1;
      case "extractor": return 2;
      case "armory": return 3;
      case "porthole": return 4;
      case "drill": return 5;
      default: return -1;
    }
  }

  onDragEnter(zoneName: DragEvent, zoneNumber: number): void {
    let realClassName = (zoneName.target as HTMLDivElement).className.substring("single-dice-storage-zone ".length);
    this.dragEnterEmitter.emit(realClassName+" "+zoneNumber);
  }

  onDragEnterReceive(zone: string): void {
    //
  }

}
