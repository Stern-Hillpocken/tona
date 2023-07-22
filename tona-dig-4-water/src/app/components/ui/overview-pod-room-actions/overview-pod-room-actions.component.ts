import { Component, Input } from '@angular/core';
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

}
