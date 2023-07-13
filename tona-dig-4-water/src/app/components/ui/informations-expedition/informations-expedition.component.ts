import { Component, Input } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';
import { UtilsService } from 'src/app/shared/utils.service';

@Component({
  selector: 'app-informations-expedition',
  templateUrl: './informations-expedition.component.html',
  styleUrls: ['./informations-expedition.component.scss']
})
export class InformationsExpeditionComponent {

  @Input()
  expedition!: Expedition;

  constructor(
    private utils: UtilsService
  ){}

  difficultyToString(value: number): string {
    return this.utils.difficultyToString(value);
  }

  getDateZero(time: number): string {
    return time < 10 ? '0'+time : time.toString();
  }

}
