import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PodRegister } from 'src/app/models/pod-register.model';
import { Popup } from 'src/app/models/popup.model';
import { PodSetupService } from 'src/app/shared/pod-setup.service';
import { PopupService } from 'src/app/shared/popup.service';

@Component({
  selector: 'app-hub-setup',
  templateUrl: './hub-setup.component.html',
  styleUrls: ['./hub-setup.component.scss']
})
export class HubSetupComponent {

  podAvailableList: PodRegister[] = [];

  constructor(
    private podSetupService: PodSetupService,
    private popup: PopupService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.displayPods();
  }

  displayPods(): void {
    this.podSetupService.getAllReadyPod().subscribe((podsReceived: PodRegister[]) => {
      console.log(podsReceived)
      this.podAvailableList = podsReceived;
    });
  }

  onPodReceive(newPod: PodRegister): void {
    this.podSetupService.postPodSetup(newPod).subscribe((response: Popup) => {
      this.popup.add(response);
      this.displayPods();
    });
  }

  onRefreshReceive(): void {
    this.displayPods();
  }

  onActionPodReceive(event: "destroy" | "launch"): void {
    if(event === "destroy"){
      this.podSetupService.deletePodSetup().subscribe((response: Popup) => {
        this.popup.add(response);
        if(response.type === "work"){
          this.displayPods();
        }
      });
    }else if(event === "launch"){
      this.podSetupService.launchPodSetup().subscribe((response: Popup) => {
        this.popup.add(response);
        if(response.type === "work"){
          this.router.navigate(['/game/overview']);
        }
      });
    }
  }
}
