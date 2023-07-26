import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

    private _contents$: BehaviorSubject<any> = new BehaviorSubject({title:"", text:""});

    get(): BehaviorSubject<any> {
        return this._contents$;
    }

    udpate(title: string, text: string): void {
        this._contents$.next({title:title, text:text});
    }
}