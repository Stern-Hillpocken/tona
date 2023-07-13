import { Component } from '@angular/core';
import { Majagaba } from 'src/app/models/majagaba.model';
import { Expedition } from 'src/app/models/expedition.model';
import { User } from 'src/app/models/user.model';
import { ExpeditionService } from 'src/app/shared/expedition.service';
import { UtilsService } from 'src/app/shared/utils.service';
import { UserService } from 'src/app/shared/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  expedition!: Expedition;

  user: User = new User("","","",new Majagaba(0,0,0,"",[],[],0,""));

  constructor(
    private expeditionService: ExpeditionService,
    private userService: UserService
  ){}

  ngOnInit(): void {
    this.expeditionService._getExpedition$().subscribe((expe: Expedition) => {
      this.expedition = expe;
    });
    this.expeditionService.getMy().subscribe((expe: Expedition) => {
      this.expedition = expe;
    });
    this.userService.getMe().subscribe((me: User) => {
      this.user = me;
    });
  }

  getDateZero(time: number): string {
    return time < 10 ? '0'+time : time.toString();
  }
  
  endTurn(): void {
    this.expeditionService.endTurn().subscribe((expe: Expedition) => {
      console.log(expe)
      this.expeditionService._setExpedition$(expe);
    });
  }
}
