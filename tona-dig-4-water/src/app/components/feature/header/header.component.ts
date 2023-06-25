import { Component } from '@angular/core';
import { Character } from 'src/app/models/character.model';
import { Expedition } from 'src/app/models/expedition.model';
import { User } from 'src/app/models/user.model';
import { ExpeditionService } from 'src/app/shared/expedition.service';
import { UtilsService } from 'src/app/shared/utils.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  expedition!: Expedition;

  constructor(
    private expeditionService: ExpeditionService,
    private utils: UtilsService
  ){}

  ngOnInit(): void {
    this.expeditionService._getExpedition$().subscribe((expe: Expedition) => {
      this.expedition = expe;
    });
  }

  difficultyToString(value: number): string {
    return this.utils.difficultyToString(value);
  }

  getDateZero(time: number): string {
    return time < 10 ? '0'+time : time.toString();
  }
  
  endTurn(): void {
    this.expeditionService.endTurn().subscribe((expe: Expedition) => {
      this.expeditionService._setExpedition$(expe);
    });
  }
}
