import { Injectable } from '@angular/core';
import { TokenResponse } from '../models/token-response.model';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  getToken(): string | null {
    const jwtToken = localStorage.getItem("jwtToken");
    if (jwtToken) {
      return jwtToken;
    } else {
      return null;
    }
  }

  setToken(tokenFromDB: TokenResponse): void {
    localStorage.setItem("jwtToken", tokenFromDB.token);
  }

  clearToken(): void {
    localStorage.removeItem("jwtToken");
  }
}