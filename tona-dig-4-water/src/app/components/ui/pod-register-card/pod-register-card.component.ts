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

  job = "";
  jobsName: string[] = ["gunner", "miner", "leader", "runner", "visionary", "strong", "repairer", "wind-sensor"];
  jobsFr: string[] = ["Canonnier", "Mineur", "Leader", "Runner", "Visionnaire", "Costaud", "Réparateur", "Capteur de vent"];
  jobsColor: string[] = ["black", "blue", "green", "yellow", "purple", "red", "orange", "gray"];
  jobsCharge: number[] = [3,4,3,4,3,4,3,4];
  jobsPower: string[] = ["Diminue de 1 la valeur d'un dé.","Augmente de 1 la valeur d'un dé.","Donne un de ses dés.","A un déplacement gratuit.","Connaît le résultat de son prochain lancé de dés.","+ 2 PV et ne subit pas l'effet des salles.","Peut utiliser n'importe quel dé pour réparer.","Obtient un dé supplémentaire."];

  onDestroyPod(): void {
    this.actionPodEmitter.emit("destroy");
  }

  onLaunchPod(): void {
    this.actionPodEmitter.emit("launch");
  }

  jobSelected(name: string): void {
    this.job = name;
  }

  jobsId(name: string): number{
    for(let i = 0; i < this.jobsName.length; i++){
      if(this.jobsName[i] === name) return i;
    }
    return -1;
  }
}
