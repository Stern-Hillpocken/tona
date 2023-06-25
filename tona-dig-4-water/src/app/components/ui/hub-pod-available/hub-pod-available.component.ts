import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PodRegister } from 'src/app/models/pod-register.model';

@Component({
  selector: 'app-hub-pod-available',
  templateUrl: './hub-pod-available.component.html',
  styleUrls: ['./hub-pod-available.component.scss']
})
export class HubPodAvailableComponent {

  @Input()
  podAvailableList!: PodRegister[];

  @Output()
  refreshEmitter: EventEmitter<null> = new EventEmitter<null>();

  @Output()
  actionPodEmitter: EventEmitter<"destroy" | "launch"> = new EventEmitter;

  onRefresh(): void {
    this.refreshEmitter.emit();
  }

  onActionPod(event: "destroy" | "launch"): void {
    this.actionPodEmitter.emit(event);
  }
}
