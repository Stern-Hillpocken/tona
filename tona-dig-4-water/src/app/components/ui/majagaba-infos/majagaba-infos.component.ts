import { Component, EventEmitter, Input, Output } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { AlertService } from 'src/app/shared/alert.service';

@Component({
  selector: 'app-majagaba-infos',
  templateUrl: './majagaba-infos.component.html',
  styleUrls: ['./majagaba-infos.component.scss']
})
export class MajagabaInfosComponent {

  @Input()
  user!: User;

  @Output()
  jobTriggerEmitter: EventEmitter<string> = new EventEmitter();

  isJobPanelOpen: boolean = false;

  jobTitle: string = "";
  jobDescription: string = "";

  switchJobPanel(): void {
    if(this.jobTitle === ""){
      if(this.user.majagaba.job === 'gunner'){ this.jobTitle = "Canonnier"; this.jobDescription = "Diminuer de 1 la valeur d'un de ses dés."; }
      else if(this.user.majagaba.job === 'miner'){ this.jobTitle = "Mineur"; this.jobDescription = "Augmenter de 1 la valeur d'un de ses dés."; }
    }
    this.isJobPanelOpen = !this.isJobPanelOpen;
  }

  jobTrigger(dieValue: number): void {
    this.jobTriggerEmitter.emit('job '+dieValue);
  }

}
