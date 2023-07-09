import { Injectable } from '@angular/core';
import { UtilsService } from './utils.service';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

    private readonly _sqlTable: string = 'users';

    constructor(
        private http: HttpClient,
        private utils: UtilsService
    ){}

    getMe(): Observable<User> {
        return this.http.get<User>(this.utils.getBaseUrl() + this._sqlTable + '/me');
    }

    changeProfilePicture(url: string): Observable<void> {
        return this.http.post<void>(this.utils.getBaseUrl() + this._sqlTable + '/profile-picture', url);
    }
}