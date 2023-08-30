import { Injectable } from '@angular/core';
import { UtilsService } from './utils.service';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Majagaba } from '../models/majagaba.model';

@Injectable({
  providedIn: 'root'
})
export class MajagabaService {

    private readonly _sqlTable: string = "majagaban";

    constructor(
        private utils: UtilsService,
        private http: HttpClient
    ){}

    reroll(): Observable<void> {
        return this.http.get<void>(this.utils.getBaseUrl() + this._sqlTable + '/reroll');
    }

    stockDie(value: number): Observable<void> {
        return this.http.put<void>(this.utils.getBaseUrl() + this._sqlTable + '/stock-die', value);
    }

    destockDie(value: number): Observable<void> {
        return this.http.put<void>(this.utils.getBaseUrl() + this._sqlTable + '/destock-die', value);
    }

    move(dieValue: number, startZone: string, endZone: string): Observable<void> {
        return this.http.put<void>(this.utils.getBaseUrl() + this._sqlTable + '/move', {dieValue: dieValue, startZone: startZone, endZone: endZone});
    }

    allocate(dieValue: number, startZone: string, endZone: string): Observable<void> {
        return this.http.put<void>(this.utils.getBaseUrl() + this._sqlTable + '/allocate', {dieValue: dieValue, startZone: startZone, endZone: endZone});
    }

    takeObject(objectName: string): Observable<void> {
        return this.http.put<void>(this.utils.getBaseUrl() + this._sqlTable + '/take-object', objectName);
    }

    jobActivation(dieValue: number): Observable<void> {
        return this.http.put<void>(this.utils.getBaseUrl() + this._sqlTable + '/job-activation', dieValue);
    }

}