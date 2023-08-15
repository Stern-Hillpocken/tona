import { Injectable } from '@angular/core';
import { UtilsService } from './utils.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../models/user.model';
import { HttpClient } from '@angular/common/http';
import { Majagaba } from '../models/majagaba.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

    private readonly _sqlTable: string = 'users';

    private readonly _user$: BehaviorSubject<User> = new BehaviorSubject<User>(new User("","","",new Majagaba(0,0,0,"",[],[],0,"",0,0,0)));

    constructor(
        private http: HttpClient,
        private utils: UtilsService
    ){}

    getMe(): Observable<User> {
        return this.http.get<User>(this.utils.getBaseUrl() + this._sqlTable + '/me');
    }

    _setUser$(user: User): void {
        this._user$.next(user);
    }
  
    _getUser$(): Observable<User> {
        return this._user$.asObservable();
    }

    changeProfilePicture(url: string): Observable<void> {
        return this.http.post<void>(this.utils.getBaseUrl() + this._sqlTable + '/profile-picture', url);
    }
}