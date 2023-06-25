import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PodRegister } from 'src/app/models/pod-register.model';

@Component({
  selector: 'app-pod-register-card',
  templateUrl: './pod-register-card.component.html',
  styleUrls: ['./pod-register-card.component.scss']
})
export class PodRegisterCardComponent {

  @Input()
  pod!: PodRegister;

  @Output()
  actionPodEmitter: EventEmitter<"destroy" | "launch"> = new EventEmitter;

  onDestroyPod(): void {
    this.actionPodEmitter.emit("destroy");
  }

  onLaunchPod(): void {
    this.actionPodEmitter.emit("launch");
  }
}
