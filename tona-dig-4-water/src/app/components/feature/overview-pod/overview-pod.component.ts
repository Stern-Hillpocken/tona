import { Component } from '@angular/core';
import { Majagaba } from 'src/app/models/majagaba.model';
import { Expedition } from 'src/app/models/expedition.model';
import { Pod } from 'src/app/models/pod.model';
import { User } from 'src/app/models/user.model';
import { ExpeditionService } from 'src/app/shared/expedition.service';
import { MajagabaService } from 'src/app/shared/majagaba.service';
import { UserService } from 'src/app/shared/user.service';

@Component({
  selector: 'app-overview-pod',
  templateUrl: './overview-pod.component.html',
  styleUrls: ['./overview-pod.component.scss']
})
export class OverviewPodComponent {

  expedition: Expedition = new Expedition(0,"",0,0,0,0,new Pod(0,[]),new User("","","",new Majagaba(0,0,0,"",[],[],0,"")),[],0,0,[],"");

  user: User = new User("","","",new Majagaba(0,0,0,"",[],[],0,""));

  startDragedZoneName: string = "";
  lastDragedZoneName: string = "";
  valueDraged: number = 0;

  constructor(
    private expeditionService: ExpeditionService,
    private userService: UserService,
    private majagabanService: MajagabaService
  ){}

  ngOnInit(): void {
    this.reloadExpedition();
    this.expeditionService._getExpedition$().subscribe((expe: Expedition) => {
      this.expedition = expe;
    });
    this.reloadMe();
  }

  reloadExpedition(): void {
    this.expeditionService.getMy().subscribe((expe: Expedition) => {
      this.expeditionService._setExpedition$(expe);
    });
  }

  reloadMe(): void {
    this.userService.getMe().subscribe((me: User) => {
      this.user = me;
    });
  }

  onDragStartReceive(event: {value:number, startZone: string}): void {
    this.valueDraged = event.value;
    this.startDragedZoneName = event.startZone;
  }

  onDragEnterReceive(zoneName: string): void {
    this.lastDragedZoneName = zoneName;
  }

  onDragEndReceive(): void {
    console.log(this.startDragedZoneName + ' > ' + this.valueDraged + ' > ' + this.lastDragedZoneName)
    if(this.startDragedZoneName !== this.lastDragedZoneName){
      if(this.lastDragedZoneName === "dice-stocked-zone"){
        this.majagabanService.stockDie(this.valueDraged).subscribe(() => {
          this.reloadExpedition();
          this.reloadMe();
        });
      }else if(this.lastDragedZoneName === "dice-pool-zone"){
        this.majagabanService.destockDie(this.valueDraged).subscribe(() => {
          this.reloadExpedition();
          this.reloadMe();
        });
      }else if(this.lastDragedZoneName === "armory" || this.lastDragedZoneName === "drill" || this.lastDragedZoneName === "extractor" || this.lastDragedZoneName === "hoist" || this.lastDragedZoneName === "hold" || this.lastDragedZoneName === "???"){
        this.majagabanService.move(this.valueDraged, this.startDragedZoneName, this.lastDragedZoneName).subscribe(() => {
          this.reloadExpedition();
          this.reloadMe();
        });
      }
    }
  }

  onRerollReceive(): void {
    this.majagabanService.reroll().subscribe(() => {
      this.reloadExpedition();
      this.reloadMe();
    });
  }

}
