import { Component } from '@angular/core';
import { Majagaba } from 'src/app/models/majagaba.model';
import { Expedition } from 'src/app/models/expedition.model';
import { User } from 'src/app/models/user.model';
import { ExpeditionService } from 'src/app/shared/expedition.service';
import { UtilsService } from 'src/app/shared/utils.service';
import { UserService } from 'src/app/shared/user.service';
import { MajagabaService } from 'src/app/shared/majagaba.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  expedition!: Expedition;

  user!: User;

  constructor(
    private expeditionService: ExpeditionService,
    private userService: UserService,
    private majagabaService: MajagabaService
  ){}

  ngOnInit(): void {
    this.expeditionService.getMy().subscribe((expe: Expedition) => {
      this.expeditionService._setExpedition$(expe);
    });
    this.expeditionService._getExpedition$().subscribe((expe: Expedition) => {
      this.expedition = expe;
    });
    this.userService.getMe().subscribe((user: User) => {
      this.userService._setUser$(user);
    });
    this.userService._getUser$().subscribe((user: User) => {
      this.user = user;
    });
  }

  getDateZero(time: number): string {
    return time < 10 ? '0'+time : time.toString();
  }
  
  endTurn(): void {
    this.expeditionService.endTurn().subscribe((expe: Expedition) => {
      console.log(expe)
      this.expeditionService._setExpedition$(expe);
      this.userService.getMe().subscribe((user: User) => {
        this.userService._setUser$(user);
      });
    });
  }

  jobReceive(dieValue: number): void {
    this.majagabaService.jobActivation(dieValue).subscribe(() => {
      this.userService.getMe().subscribe((user: User) => {
        this.userService._setUser$(user);
      });
    });
  }
}
