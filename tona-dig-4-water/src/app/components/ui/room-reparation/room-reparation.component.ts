import { Component, EventEmitter, Output } from '@angular/core';
import { AlertService } from 'src/app/shared/alert.service';

@Component({
  selector: 'app-room-reparation',
  templateUrl: './room-reparation.component.html',
  styleUrls: ['./room-reparation.component.scss']
})
export class RoomReparationComponent {

  @Output()
  reparationEmitter: EventEmitter<void> = new EventEmitter();

  constructor(private alertService: AlertService){}

  onReparationDragEnter(): void{
    this.reparationEmitter.emit();
  }

  alert(): void {
    this.alertService.udpate("Réparation", "Déposer une valeur de 5 ou 6 pour pouvoir enlever l'effet sur la salle, puis réparer.");
  }
}
