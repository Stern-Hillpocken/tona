import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Expedition } from '../models/expedition.model';
import { UtilsService } from './utils.service';
import { User } from '../models/user.model';
import { Majagaba } from '../models/majagaba.model';
import { ChatMessage } from '../models/chat-message.model';
import { Pod } from '../models/pod.model';

@Injectable({
  providedIn: 'root'
})
export class ExpeditionService {

    private readonly _sqlTable: string = "expedition";

    private readonly _expedition$: BehaviorSubject<Expedition> = new BehaviorSubject<Expedition>(new Expedition(0,"",0,0,0,0,new Pod(0,[]),new User("","","",new Majagaba(0,0,0,"",[],[],0,"")),[],0,0,[],""));

    constructor(
      private http: HttpClient,
      private utils: UtilsService
    ){}

    getMy(): Observable<Expedition> {
      return this.http.get<Expedition>(this.utils.getBaseUrl() + this._sqlTable + "/my");
    }

    _setExpedition$(expe: Expedition): void {
      this._expedition$.next(expe);
    }

    _getExpedition$(): Observable<Expedition> {
      return this._expedition$.asObservable();
    }

    endTurn(): Observable<Expedition> {
      return this.http.get<Expedition>(this.utils.getBaseUrl() + this._sqlTable + "/end-turn");
    }

    sendMessage(message: string): Observable<void> {
      return this.http.post<void>(this.utils.getBaseUrl() + this._sqlTable + "/send-message", message);
    }

    getAllChatMessages(): Observable<ChatMessage[]> {
      return this.http.get<ChatMessage[]>(this.utils.getBaseUrl() + this._sqlTable + "/all-chat-messages");
    }

    reroll(): Observable<Expedition> {
      return this.http.get<Expedition>(this.utils.getBaseUrl() + this._sqlTable + "/reroll");
    }
}