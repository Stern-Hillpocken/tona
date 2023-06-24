import { Component, EventEmitter, Output } from '@angular/core';
import { PodRegister } from 'src/app/models/pod-register.model';

@Component({
  selector: 'app-hub-pod-creation',
  templateUrl: './hub-pod-creation.component.html',
  styleUrls: ['./hub-pod-creation.component.scss']
})
export class HubPodCreationComponent {

  @Output()
  podEmitter: EventEmitter<PodRegister> = new EventEmitter;

  pod: PodRegister = new PodRegister("",1,1,"");

  onSubmit(): void {
    this.podEmitter.emit(this.pod);
  }
}
