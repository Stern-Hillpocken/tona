import { Component } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AlertService } from 'src/app/shared/alert.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent {

  allContents: any = {title:"", text:""};

  constructor(private alertService: AlertService){}

  ngOnInit(): void {
    this.alertService.get().subscribe((bhs: BehaviorSubject<any>) => {
      this.allContents = bhs;
    });
  }

  close(): void {
    this.alertService.udpate("","");
  }

}
