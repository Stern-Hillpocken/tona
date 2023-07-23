import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-hold',
  templateUrl: './hold.component.html',
  styleUrls: ['./hold.component.scss']
})
export class HoldComponent {

  @Input()
  expedition!: Expedition;

  @Input()
  user!: User;

  @Output()
  dragEnterEmitter: EventEmitter<string> = new EventEmitter();

  onDragEnter(zoneName: DragEvent, zoneNumber: number): void {
    //
  }

}
