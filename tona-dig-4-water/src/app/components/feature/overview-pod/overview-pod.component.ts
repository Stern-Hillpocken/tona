import { Component } from '@angular/core';
import { Majagaba } from 'src/app/models/majagaba.model';
import { Expedition } from 'src/app/models/expedition.model';
import { Pod } from 'src/app/models/pod.model';
import { User } from 'src/app/models/user.model';
import { ExpeditionService } from 'src/app/shared/expedition.service';

@Component({
  selector: 'app-overview-pod',
  templateUrl: './overview-pod.component.html',
  styleUrls: ['./overview-pod.component.scss']
})
export class OverviewPodComponent {

  expedition: Expedition = new Expedition(0,"",0,0,0,0,new User("","","",new Majagaba(0,0,"",[],[],0,"")),0,0,[],"");

  constructor(
    private expeditionService: ExpeditionService
  ){}

  ngOnInit(): void {
    this.expeditionService.getMy().subscribe((expe: Expedition) => {
      this.expedition = expe;
    });
  }
}
