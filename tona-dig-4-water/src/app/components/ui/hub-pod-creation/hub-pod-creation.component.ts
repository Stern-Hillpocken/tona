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

  pod: PodRegister = new PodRegister("",1,new User("","","", new Majagaba(0,0,0,"",[],[],0,"")),1,"","");

  constructor(
    private utils: UtilsService
  ){}

  difficultyToString(value: number): string {
    return this.utils.difficultyToString(value);
  }

  onSubmit(): void {
    this.podEmitter.emit(this.pod);
  }
}
