import { Component, EventEmitter, Output } from '@angular/core';
import { Majagaba } from 'src/app/models/majagaba.model';
import { User } from 'src/app/models/user.model';
import { PodRegister } from 'src/app/models/pod-register.model';
import { UtilsService } from 'src/app/shared/utils.service';

@Component({
  selector: 'app-hub-pod-creation',
  templateUrl: './hub-pod-creation.component.html',
  styleUrls: ['./hub-pod-creation.component.scss']
})
export class HubPodCreationComponent {

  @Output()
  podEmitter: EventEmitter<PodRegister> = new EventEmitter;

  pod: PodRegister = new PodRegister("",1,new User("","","", new Majagaba(0,0,0,"",[],[],[],0,"",0,0,0,0,0)),1,"","");

  constructor(
    private utils: UtilsService
  ){}

  difficultyToString(value: number): string {
    return this.utils.difficultyToString(value);
  }

  setDifficultyTo(value: number): void {
    this.pod.difficulty = value;
  }

  onSubmit(): void {
    this.podEmitter.emit(this.pod);
  }

  difficultyInfo(label: string): number {
    let podLife: number[] = [10,8,7];
    let roomLife: number[] = [6,6,5];
    let majagabanLife: number[] = [6,5,5];
    let attackTurn: number[] = [6,4,3];
    let statusTurn: number[] = [4,3,2];
    let nextAttackVisionTurn: number[] = [8,5,4];
    let nextStatusVisionTurn: number[] = [6,4,3];
    let scrap: number[] = [25,20,20];
    let ammo: number[] = [8,6,4];
    let spiceDose: number[] = [4,2,2];
    let blast: number[] = [1,0,0];
    let regulator: number[] = [3,2,2];
    let switcher: number[] = [2,2,1];
    let index = this.pod.difficulty - 1;
    if(label === "podLife")return podLife[index];
    if(label === "roomLife")return roomLife[index];
    if(label === "majagabanLife")return majagabanLife[index];
    if(label === "attackTurn")return attackTurn[index];
    if(label === "statusTurn")return statusTurn[index];
    if(label === "nextAttackVisionTurn")return nextAttackVisionTurn[index];
    if(label === "nextStatusVisionTurn")return nextStatusVisionTurn[index];
    if(label === "scrap")return scrap[index];
    if(label === "ammo")return ammo[index];
    if(label === "spiceDose")return spiceDose[index];
    if(label === "blast")return blast[index];
    if(label === "regulator")return regulator[index];
    if(label === "switcher")return switcher[index];
    return -1;
  }
}
