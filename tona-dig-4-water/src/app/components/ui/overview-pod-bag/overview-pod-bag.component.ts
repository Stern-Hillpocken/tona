import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Subject } from 'rxjs';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-overview-pod-bag',
  templateUrl: './overview-pod-bag.component.html',
  styleUrls: ['./overview-pod-bag.component.scss']
})
export class OverviewPodBagComponent {

  @Input()
  requestIsSended!: Subject<boolean>;

  @Input()
  user!: User;

  @Output()
  dragEnterEmitter: EventEmitter<string> = new EventEmitter();

  objectClicked: "steam-blast" | "steam-regulator" | "steam-switcher" | "" = "";

  ngOnInit(): void {
    this.requestIsSended.subscribe(() => {
      this.cancelObjectClicked();
    });
  }

  onObjectClick(name: "steam-blast" | "steam-regulator" | "steam-switcher" | ""): void {
    this.objectClicked = name;
  }

  cancelObjectClicked(): void {
    this.objectClicked = "";
  }

  onDragEnter(zone: string): void {
    this.dragEnterEmitter.emit(zone);
  }
}
