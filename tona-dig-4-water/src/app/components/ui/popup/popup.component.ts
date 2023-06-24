import { Component } from '@angular/core';
import { Popup } from 'src/app/models/popup.model';
import { PopupService } from 'src/app/shared/popup.service';

@Component({
  selector: 'app-popup',
  templateUrl: './popup.component.html',
  styleUrls: ['./popup.component.scss']
})
export class PopupComponent {

  popupList: Popup[] = [];

  constructor(private popupService: PopupService){}

  ngOnInit(): void{
    this.popupList = this.popupService.getList();
  }
}
