import { Component } from '@angular/core';
import { Majagaba } from 'src/app/models/majagaba.model';
import { Expedition } from 'src/app/models/expedition.model';
import { Pod } from 'src/app/models/pod.model';
import { User } from 'src/app/models/user.model';
import { ExpeditionService } from 'src/app/shared/expedition.service';
import { MajagabaService } from 'src/app/shared/majagaba.service';

@Component({
  selector: 'app-overview-pod',
  templateUrl: './overview-pod.component.html',
  styleUrls: ['./overview-pod.component.scss']
})
export class OverviewPodComponent {

  expedition: Expedition = new Expedition(0,"",0,0,0,0,new Pod(0,[]),new User("","","",new Majagaba(0,0,0,"",[],[],0,"")),[],0,0,[],"");

  constructor(
    private expeditionService: ExpeditionService,
    private majagabanService: MajagabaService
  ){}

  ngOnInit(): void {
    this.expeditionService._getExpedition$().subscribe((expe: Expedition) => {
      this.expedition = expe;
    });
  }

  reloadExpedition(): void {
    this.expeditionService.getMy().subscribe((expe: Expedition) => {
      this.expeditionService._setExpedition$(expe);
    });
  }

  onDragDieReceive(event: {value:number, className:string}): void {
    if(event.className === "dice-stocked-zone"){
      this.majagabanService.stockDie(event.value).subscribe(() => {
        this.reloadExpedition();
      });
    }
  }

  onRerollReceive(event: boolean): void {
    this.majagabanService.reroll().subscribe(() => {
      this.reloadExpedition();
    });
  }

}
