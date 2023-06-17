import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title: string = 'Tona : Dig 4 Water';
  currentUrl: string = '';

  constructor(private router: Router){}

  ngOnInit(): void {
    this.router.events.subscribe(navInfo => {
      if(navInfo instanceof NavigationEnd){
        this.currentUrl = navInfo.url;
      }
    });
  }
}
