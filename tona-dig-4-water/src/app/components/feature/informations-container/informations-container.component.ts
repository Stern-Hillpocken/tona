import { Component } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { Majagaba } from 'src/app/models/majagaba.model';
import { Pod } from 'src/app/models/pod.model';
import { User } from 'src/app/models/user.model';
import { ExpeditionService } from 'src/app/shared/expedition.service';

@Component({
  selector: 'app-informations-container',
  templateUrl: './informations-container.component.html',
  styleUrls: ['./informations-container.component.scss']
})
export class InformationsContainerComponent {

  expedition: Expedition = new Expedition("",0,0,0,0,new Pod(0,0,[]),new User("","","",new Majagaba(0,0,0,"",[],[],0,"",0,0,0)),[],0,0,0,[],[],0,0,0,[],[],[],[],"");

  constructor(
    private expeditionService: ExpeditionService
  ){}

  ngOnInit(): void {
    this.expeditionService.getMy().subscribe((expe: Expedition) => {
      this.expedition = expe;
    });
  }

}
