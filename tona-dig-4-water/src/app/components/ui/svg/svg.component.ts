import { Component, Input } from '@angular/core';
import { SvgService } from 'src/app/shared/svg.service';

@Component({
  selector: 'app-svg',
  templateUrl: './svg.component.html',
  styleUrls: ['./svg.component.scss']
})
export class SvgComponent {

  @Input()
  named: string = "";

  path: string = "";

  constructor(private svgService: SvgService){}

  ngOnInit(): void {
    this.path = this.svgService.getNamed(this.named);
  }

}
