import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { TokenService } from 'src/app/shared/token.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent {

  urlIncludesGame: boolean = false;

  isConnected: boolean = false;

  constructor(
    private router: Router
  ){}

  ngOnInit(): void {
    this.router.events.subscribe(navInfo => {
      if(navInfo instanceof NavigationEnd){
        this.urlIncludesGame = navInfo.url.includes("/game");
      }
    });
  }

}
