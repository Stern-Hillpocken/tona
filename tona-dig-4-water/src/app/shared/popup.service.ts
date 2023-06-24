import { Injectable } from '@angular/core';
import { Popup } from '../models/popup.model';

@Injectable({
  providedIn: 'root'
})
export class PopupService {

  private _popupList: Popup[] = [];

  getList(): Popup[] {
    return this._popupList;
  }

  add(newPopup: Popup): void {
    let displayTime = 3000;
    if(newPopup.type === "error"){
      displayTime = 4000;
    }
    this._popupList.push(newPopup);
    setTimeout(() => {
      this.removeFirst();
    }, displayTime);
  }

  private removeFirst(): void {
    this._popupList.shift();
  }

}