import { Component, Input } from '@angular/core';
import { PodRegister } from 'src/app/models/pod-register.model';

@Component({
  selector: 'app-hub-pod-available',
  templateUrl: './hub-pod-available.component.html',
  styleUrls: ['./hub-pod-available.component.scss']
})
export class HubPodAvailableComponent {

  @Input()
  podAvailableList!: PodRegister[];

}
