import { Injectable } from '@angular/core';
import { TokenResponse } from '../models/token-response.model';
import jwt_decode from 'jwt-decode';
import { BehaviorSubject, Observable } from 'rxjs';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private lsService: LocalStorageService) { }

  getTokenFromLocalStorageAndDecode(): any {
    const jwtToken: string | null = this.lsService.getToken();
    if(jwtToken) {
      return this._decodeToken({token: jwtToken});
    } else {
      return null;
    }
  }

  private _decodeToken(tokenFromDB: TokenResponse): any {
    return this._getDecodedTokenResponse(tokenFromDB.token);
  }

  private _getDecodedTokenResponse(token: string): any {
    return jwt_decode(token);
  }
}