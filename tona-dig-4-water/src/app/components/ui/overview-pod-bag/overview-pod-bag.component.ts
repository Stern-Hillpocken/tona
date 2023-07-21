import { Component, EventEmitter, Input, Output } from '@angular/core';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-overview-pod-bag',
  templateUrl: './overview-pod-bag.component.html',
  styleUrls: ['./overview-pod-bag.component.scss']
})
export class OverviewPodBagComponent {

  @Input()
  user!: User;

  @Output()
  objectUseEmitter: EventEmitter<string> = new EventEmitter();

  objectClicked: "steam-blast" | "steam-regulator" | "steam-switcher" | "" = "";

  onObjectClick(name: "steam-blast" | "steam-regulator" | "steam-switcher" | ""): void {
    this.objectClicked = name;
  }

  cancelObjectClicked(): void {
    this.objectClicked = "";
  }
}
