import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { UserAuth } from '../models/user-auth.model';
import { TokenResponse } from '../models/token-response.model';
import { UserRegister } from '../models/user-register.model';
import { Router } from '@angular/router';
import { UtilsService } from './utils.service';
import { PopupService } from './popup.service';
import { LocalStorageService } from './local-storage.service';
import { Popup } from '../models/popup.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private localStorage: LocalStorageService,
    private utils: UtilsService,
    private router: Router,
    private popup: PopupService
  ) { }

  // Inscription
  register(userRegister: UserRegister): void {
    this.http.post<any>(this.utils.getBaseUrl()+'auth/register', userRegister)
        .pipe(tap((res: Popup) => {
        this.popup.add(new Popup(res.message, res.type));
        if(res.type === "work"){
          this.router.navigate(["/login"]);
        }
        }))
        .subscribe();
  }

  // Connexion
  login(userAuth: UserAuth): void {
    this.localStorage.clearToken();
    this.http.post<any>(this.utils.getBaseUrl()+'auth/authenticate', userAuth)
      .subscribe((tokenFromDB: TokenResponse) => {
        this.popup.add(new Popup(tokenFromDB.message as string, "work"));
        this.localStorage.setToken(tokenFromDB);
        this.router.navigate(['/hub']);
      });
  }
}