import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PodRegister } from '../models/pod-register.model';
import { UtilsService } from './utils.service';
import { Observable } from 'rxjs';
import { Popup } from '../models/popup.model';


@Injectable({
  providedIn: 'root'
})
export class PodSetupService {

  private readonly _sqlTableName = "pod-setup";

  constructor(
    private http: HttpClient,
    private utils: UtilsService
  ) {}

  getAllReadyPod(): Observable<PodRegister[]> {
    return this.http.get<PodRegister[]>(this.utils.getBaseUrl() + this._sqlTableName + "/all-ready");
  }
  
  postPodSetup(pod: PodRegister): Observable<Popup> {
    return this.http.post<Popup>(this.utils.getBaseUrl() + this._sqlTableName + "/prepare-new-one", pod);
  }

  deletePodSetup(): Observable<Popup> {
    return this.http.delete<Popup>(this.utils.getBaseUrl() + this._sqlTableName + "/destroy-own");
  }

  launchPodSetup(): Observable<Popup> {
    return this.http.delete<Popup>(this.utils.getBaseUrl() + this._sqlTableName + "/launch");
  }
}