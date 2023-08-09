import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PopupService } from 'src/app/shared/popup.service';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent {

  time: number[] = [10,0]
  points: number = 0;

  constructor(private router: Router, private popup: PopupService){
    if(!this.router.url.endsWith('/error')){
      this.router.navigate([this.router.url+'/error']);
    }
  }

  ngOnInit(): void {
    this.clock();
  }

  ngAfterViewInit(): void {
    let svgList = document.getElementsByTagName('svg');
    for(let i = 0; i < svgList.length; i++){
      svgList[i].style.top = (Math.random() * 4200 + 256) + 'px';
      svgList[i].style.left = (Math.random() * (360 - 24)) + 'px';
    }
  }

  clock(): void {
    setTimeout(() => {
      if(this.time[1] === 0){
        this.time[0] --;
        this.time[1] = 9;
      }else{
        this.time[1] --;
      }

      if(this.time[0] <= 0 && this.time[1] <= 0){
        this.popup.add({message: this.points+" pts !",type: "work"});
        this.router.navigate(['/help']);
      }else{
        this.clock();
      }
    },100);
  }

  collect(div: any): void {
    let className: string = div.target.className.baseVal;
    if(className.length === 0) className = div.target.parentElement.className.baseVal;
    let classNameSpe: string = className.split(' ')[1];
    if(classNameSpe === 'droplet') this.points += 1;
    if(classNameSpe === 'water') this.points += 2;
    if(classNameSpe === 'pure-water') this.points += 5;
    div.target.innerHTML = "";
    div.target.outerHTML = "";
  }
}
