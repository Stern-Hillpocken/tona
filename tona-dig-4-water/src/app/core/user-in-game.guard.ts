import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from '../shared/token.service';
import { PopupService } from '../shared/popup.service';
import { Popup } from '../models/popup.model';
import { ExpeditionService } from '../shared/expedition.service';
import { Expedition } from '../models/expedition.model';

@Injectable({
  providedIn: 'root'
})
export class UserInGameGuard {

  constructor(
    private tokenService: TokenService,
    private popup: PopupService,
    private router: Router,
    private expeditionService: ExpeditionService
  ){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(this.tokenService.getTokenFromLocalStorageAndDecode()){
        // TODO
        return true;
      }else{
        this.popup.add(new Popup("Tu dois être connecté·e pour continuer 🔐", "error"));
        this.router.navigate(['/login']);
        return false;
      }
  }
  
}
