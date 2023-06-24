import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PodRegister } from '../models/pod-register.model';
import { UtilsService } from './utils.service';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class PodSetupService {

  private _sqlTableName = "pod-setup";

  constructor(
    private http: HttpClient,
    private utils: UtilsService
  ) {}

  getAllReadyPod(): Observable<PodRegister> {
    return this.http.get<PodRegister>(this.utils.getBaseUrl() + this._sqlTableName + "/all-ready");
  }
  
  postPodSetup(pod: PodRegister): Observable<PodRegister> {
    return this.http.post<PodRegister>(this.utils.getBaseUrl() + this._sqlTableName + "/prepare-new-one", pod);
  }
}