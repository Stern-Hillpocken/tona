import { Component, Input } from '@angular/core';
import { Expedition } from 'src/app/models/expedition.model';

@Component({
  selector: 'app-overview-pod-map',
  templateUrl: './overview-pod-map.component.html',
  styleUrls: ['./overview-pod-map.component.scss']
})
export class OverviewPodMapComponent {

  @Input()
  expedition!: Expedition;

}
