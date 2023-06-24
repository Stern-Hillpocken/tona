import { Component } from '@angular/core';
import { PodRegister } from 'src/app/models/pod-register.model';
import { PodSetupService } from 'src/app/shared/pod-setup.service';

@Component({
  selector: 'app-hub-setup',
  templateUrl: './hub-setup.component.html',
  styleUrls: ['./hub-setup.component.scss']
})
export class HubSetupComponent {

  podAvailableList: PodRegister[] = [];

  constructor(
    private podSetupService: PodSetupService
  ){}

  onPodReceive(newPod: PodRegister): void {
    this.podSetupService.postPodSetup(newPod).subscribe(res => console.log(res));
  }
}
