import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SvgService {

  private _svgList: any[] = [
    {
      name: "ammo", path: "M20,15C20,15 18.6,16.3 21.1,17L18.3,19.8H15.5C15.5,19.8 13.6,19.7 15,22H11L9,20C9,20 7.7,18.6 7,21.1L4.2,18.3V15.5C4.2,15.5 4.3,13.6 2,15V11L4,9C4,9 5.4,7.7 2.8,7.1L5.6,4.2H8.5C8.5,4.2 10.4,4.3 9,2H13L15,4C15,4 16.3,5.4 17,2.8L19.8,5.6V8.5C19.8,8.5 19.7,10.4 22,9V13L20,15M14,12A2,2 0 0,0 12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12Z"
    },{
      name: "between", path: "M3,5A2,2 0 0,1 5,3H19A2,2 0 0,1 21,5V19A2,2 0 0,1 19,21H5C3.89,21 3,20.1 3,19V5M6,6V18H10V16H8V8H10V6H6M16,16H14V18H18V6H14V8H16V16Z"
    },{
      name: "crank", path: "M12 3C7.03 3 3 7.03 3 12S7.03 21 12 21C14 21 15.92 20.34 17.5 19.14L16.06 17.7C14.87 18.54 13.45 19 12 19C8.13 19 5 15.87 5 12S8.13 5 12 5 19 8.13 19 12H16L20 16L24 12H21C21 7.03 16.97 3 12 3M7.71 13.16C7.62 13.23 7.59 13.35 7.64 13.45L8.54 15C8.6 15.12 8.72 15.12 8.82 15.12L9.95 14.67C10.19 14.83 10.44 14.97 10.7 15.09L10.88 16.28C10.9 16.39 11 16.47 11.1 16.47H12.9C13 16.5 13.11 16.41 13.13 16.3L13.31 15.12C13.58 15 13.84 14.85 14.07 14.67L15.19 15.12C15.3 15.16 15.42 15.11 15.47 15L16.37 13.5C16.42 13.38 16.39 13.26 16.31 13.19L15.31 12.45C15.34 12.15 15.34 11.85 15.31 11.55L16.31 10.79C16.4 10.72 16.42 10.61 16.37 10.5L15.47 8.95C15.41 8.85 15.3 8.81 15.19 8.85L14.07 9.3C13.83 9.13 13.57 9 13.3 8.88L13.13 7.69C13.11 7.58 13 7.5 12.9 7.5H11.14C11.04 7.5 10.95 7.57 10.93 7.67L10.76 8.85C10.5 8.97 10.23 9.12 10 9.3L8.85 8.88C8.74 8.84 8.61 8.89 8.56 9L7.65 10.5C7.6 10.62 7.63 10.74 7.71 10.81L8.71 11.55C8.69 11.7 8.69 11.85 8.71 12C8.7 12.15 8.7 12.3 8.71 12.45L7.71 13.19M12 13.5H12C11.16 13.5 10.5 12.82 10.5 12C10.5 11.17 11.17 10.5 12 10.5S13.5 11.17 13.5 12 12.83 13.5 12 13.5"
    },{
      name: "d5", path: "M5,3H19A2,2 0 0,1 21,5V19A2,2 0 0,1 19,21H5A2,2 0 0,1 3,19V5A2,2 0 0,1 5,3M7,5A2,2 0 0,0 5,7A2,2 0 0,0 7,9A2,2 0 0,0 9,7A2,2 0 0,0 7,5M17,15A2,2 0 0,0 15,17A2,2 0 0,0 17,19A2,2 0 0,0 19,17A2,2 0 0,0 17,15M17,5A2,2 0 0,0 15,7A2,2 0 0,0 17,9A2,2 0 0,0 19,7A2,2 0 0,0 17,5M12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12A2,2 0 0,0 12,10M7,15A2,2 0 0,0 5,17A2,2 0 0,0 7,19A2,2 0 0,0 9,17A2,2 0 0,0 7,15Z"
    },{
      name: "d6", path: "M5,3H19A2,2 0 0,1 21,5V19A2,2 0 0,1 19,21H5A2,2 0 0,1 3,19V5A2,2 0 0,1 5,3M7,5A2,2 0 0,0 5,7A2,2 0 0,0 7,9A2,2 0 0,0 9,7A2,2 0 0,0 7,5M17,15A2,2 0 0,0 15,17A2,2 0 0,0 17,19A2,2 0 0,0 19,17A2,2 0 0,0 17,15M17,10A2,2 0 0,0 15,12A2,2 0 0,0 17,14A2,2 0 0,0 19,12A2,2 0 0,0 17,10M17,5A2,2 0 0,0 15,7A2,2 0 0,0 17,9A2,2 0 0,0 19,7A2,2 0 0,0 17,5M7,10A2,2 0 0,0 5,12A2,2 0 0,0 7,14A2,2 0 0,0 9,12A2,2 0 0,0 7,10M7,15A2,2 0 0,0 5,17A2,2 0 0,0 7,19A2,2 0 0,0 9,17A2,2 0 0,0 7,15Z"
    },{
      name: "different", path: "M6,15H8V17H6M11,13H18V15H11M11,9H18V11H11M6,7H8V13H6M5,3C3.89,3 3,3.9 3,5V19A2,2 0 0,0 5,21H19A2,2 0 0,0 21,19V5A2,2 0 0,0 19,3H5Z"
    },{
      name: "left-arrow", path: "M20,10V14H11L14.5,17.5L12.08,19.92L4.16,12L12.08,4.08L14.5,6.5L11,10H20Z"
    },{
      name: "order", path: "M4 3C2.89 3 2 3.89 2 5V9C2 10.11 2.89 11 4 11H8C9.11 11 10 10.11 10 9V5C10 3.89 9.11 3 8 3M8.2 4.5L9.26 5.55L5.27 9.5L2.74 6.95L3.81 5.9L5.28 7.39M4 13C2.89 13 2 13.89 2 15V19C2 20.11 2.89 21 4 21H8C9.11 21 10 20.11 10 19V15C10 13.89 9.11 13 8 13M4 15H8V19H4M12 5H22V7H12M12 19V17H22V19M12 11H22V13H12Z"
    },{
      name: "powercharge-empty", path: "M16,20H8V6H16M16.67,4H15V2H9V4H7.33A1.33,1.33 0 0,0 6,5.33V20.67C6,21.4 6.6,22 7.33,22H16.67A1.33,1.33 0 0,0 18,20.67V5.33C18,4.6 17.4,4 16.67,4Z"
    },{
      name: "powercharge-up", path: "M16.67,4H15V2H9V4H7.33A1.33,1.33 0 0,0 6,5.33V20.66C6,21.4 6.6,22 7.33,22H16.66C17.4,22 18,21.4 18,20.67V5.33C18,4.6 17.4,4 16.67,4M11,20V14.5H9L13,7V12.5H15"
    },{
      name: "right-arrow", path: "M4,10V14H13L9.5,17.5L11.92,19.92L19.84,12L11.92,4.08L9.5,6.5L13,10H4Z"
    },{
      name: "same", path: "M6,13H11V15H6M13,13H18V15H13M13,9H18V11H13M6,9H11V11H6M5,3C3.89,3 3,3.9 3,5V19A2,2 0 0,0 5,21H19A2,2 0 0,0 21,19V5A2,2 0 0,0 19,3H5Z"
    },{
      name: "scrap", path: "M21,16.5C21,16.88 20.79,17.21 20.47,17.38L12.57,21.82C12.41,21.94 12.21,22 12,22C11.79,22 11.59,21.94 11.43,21.82L3.53,17.38C3.21,17.21 3,16.88 3,16.5V7.5C3,7.12 3.21,6.79 3.53,6.62L11.43,2.18C11.59,2.06 11.79,2 12,2C12.21,2 12.41,2.06 12.57,2.18L20.47,6.62C20.79,6.79 21,7.12 21,7.5V16.5M12,4.15L10.11,5.22L16,8.61L17.96,7.5L12,4.15M6.04,7.5L12,10.85L13.96,9.75L8.08,6.35L6.04,7.5M5,15.91L11,19.29V12.58L5,9.21V15.91M19,15.91V9.21L13,12.58V19.29L19,15.91Z"
    },{
      name: "screw", path: "M13.5,17V19L12,22L10.5,19L13.5,17M14.5,6.3L13.5,7V6H10.5V9L9.5,9.7V10.7L14.5,7.4V6.3M14.5,10.3L13.5,11V9L10.5,11V13L9.5,13.7V14.7L14.5,11.4V10.3M14.5,14.3L13.5,15V13L10.5,15V17L9.5,17.7V18.7L14.5,15.4V14.3M7,5H17C17,5 16,2 12,2C8,2 7,5 7,5Z"
    },{
      name: "sequence", path: "M22,13.5C22,15.26 20.7,16.72 19,16.96V20A2,2 0 0,1 17,22H13.2V21.7A2.7,2.7 0 0,0 10.5,19C9,19 7.8,20.21 7.8,21.7V22H4A2,2 0 0,1 2,20V16.2H2.3C3.79,16.2 5,15 5,13.5C5,12 3.79,10.8 2.3,10.8H2V7A2,2 0 0,1 4,5H7.04C7.28,3.3 8.74,2 10.5,2C12.26,2 13.72,3.3 13.96,5H17A2,2 0 0,1 19,7V10.04C20.7,10.28 22,11.74 22,13.5M17,15H18.5A1.5,1.5 0 0,0 20,13.5A1.5,1.5 0 0,0 18.5,12H17V7H12V5.5A1.5,1.5 0 0,0 10.5,4A1.5,1.5 0 0,0 9,5.5V7H4V9.12C5.76,9.8 7,11.5 7,13.5C7,15.5 5.75,17.2 4,17.88V20H6.12C6.8,18.25 8.5,17 10.5,17C12.5,17 14.2,18.25 14.88,20H17V15Z"
    },{
      name: "step", path: "M7,5H10V19H7V5M12,5L23,12L12,19V5M2,5H5V19H2V5Z"
    },{
      name: "shield-full", path: "M12,1L3,5V11C3,16.55 6.84,21.74 12,23C17.16,21.74 21,16.55 21,11V5L12,1Z"
    },{
      name: "shield-empty", path: "M21,11C21,16.55 17.16,21.74 12,23C6.84,21.74 3,16.55 3,11V5L12,1L21,5V11M12,21C15.75,20 19,15.54 19,11.22V6.3L12,3.18L5,6.3V11.22C5,15.54 8.25,20 12,21Z"
    },{
      name: "spice-dose", path: "M7,2V4H8V18A4,4 0 0,0 12,22A4,4 0 0,0 16,18V4H17V2H7M11,16C10.4,16 10,15.6 10,15C10,14.4 10.4,14 11,14C11.6,14 12,14.4 12,15C12,15.6 11.6,16 11,16M13,12C12.4,12 12,11.6 12,11C12,10.4 12.4,10 13,10C13.6,10 14,10.4 14,11C14,11.6 13.6,12 13,12M14,7H10V4H14V7Z"
    },{
      name: "spice-dose-empty", path: "M7,2H17V4H16V18A4,4 0 0,1 12,22A4,4 0 0,1 8,18V4H7V2M14,4H10V18A2,2 0 0,0 12,20A2,2 0 0,0 14,18V4Z"
    },{
      name: "status-acid", path: "M16 14V12H17.61C17.85 12.71 18 13.39 18 14H16M15.58 8C15.12 7.29 14.65 6.61 14.2 6H14V8H15.58M16 12V10H14V12H16M16 8.68V10H16.74C16.5 9.56 16.26 9.11 16 8.68M12 16V14H14V12H12V10H14V8H12V6H14V5.73C12.9 4.26 12 3.25 12 3.25S6 10 6 14C6 17.31 8.69 20 12 20V18H14V16H12M14 19.65C14.75 19.39 15.42 19 16 18.46V18H14V19.65M14 16H16V14H14V16M16 18H16.46C17 17.42 17.39 16.75 17.65 16H16V18Z"
    },{
      name: "status-clear", path: "M10.35 17L16 11.35L14.55 9.9L10.33 14.13L8.23 12.03L6.8 13.45M6.5 20Q4.22 20 2.61 18.43 1 16.85 1 14.58 1 12.63 2.17 11.1 3.35 9.57 5.25 9.15 5.88 6.85 7.75 5.43 9.63 4 12 4 14.93 4 16.96 6.04 19 8.07 19 11 20.73 11.2 21.86 12.5 23 13.78 23 15.5 23 17.38 21.69 18.69 20.38 20 18.5 20M6.5 18H18.5Q19.55 18 20.27 17.27 21 16.55 21 15.5 21 14.45 20.27 13.73 19.55 13 18.5 13H17V11Q17 8.93 15.54 7.46 14.08 6 12 6 9.93 6 8.46 7.46 7 8.93 7 11H6.5Q5.05 11 4.03 12.03 3 13.05 3 14.5 3 15.95 4.03 17 5.05 18 6.5 18M12 12Z"
    },{
      name: "status-fan", path: "M10 11C9.43 11 9 11.45 9 12S9.43 13 10 13C10.54 13 11 12.55 11 12S10.54 11 10 11M10.5 2C15 2 15.09 5.57 12.73 6.75C11.74 7.24 11.3 8.29 11.11 9.22C11.59 9.42 12 9.73 12.33 10.13C16.03 8.13 20 8.92 20 12.5C20 17 16.43 17.1 15.26 14.73C14.76 13.74 13.7 13.3 12.77 13.11C12.57 13.59 12.26 14 11.86 14.34C13.85 18.03 13.06 22 9.5 22C5 22 4.9 18.42 7.26 17.24C8.24 16.75 8.68 15.71 8.88 14.79C8.39 14.59 7.96 14.27 7.64 13.87C3.95 15.85 0 15.07 0 11.5C0 7 3.56 6.89 4.73 9.26C5.23 10.25 6.28 10.68 7.21 10.87C7.4 10.39 7.72 9.97 8.13 9.65C6.14 5.96 6.93 2 10.5 2M22 13V7H24V13H22M22 17V15H24V17H22Z"
    },{
      name: "status-fire", path: "M17.66 11.2C17.43 10.9 17.15 10.64 16.89 10.38C16.22 9.78 15.46 9.35 14.82 8.72C13.33 7.26 13 4.85 13.95 3C13 3.23 12.17 3.75 11.46 4.32C8.87 6.4 7.85 10.07 9.07 13.22C9.11 13.32 9.15 13.42 9.15 13.55C9.15 13.77 9 13.97 8.8 14.05C8.57 14.15 8.33 14.09 8.14 13.93C8.08 13.88 8.04 13.83 8 13.76C6.87 12.33 6.69 10.28 7.45 8.64C5.78 10 4.87 12.3 5 14.47C5.06 14.97 5.12 15.47 5.29 15.97C5.43 16.57 5.7 17.17 6 17.7C7.08 19.43 8.95 20.67 10.96 20.92C13.1 21.19 15.39 20.8 17.03 19.32C18.86 17.66 19.5 15 18.56 12.72L18.43 12.46C18.22 12 17.66 11.2 17.66 11.2M14.5 17.5C14.22 17.74 13.76 18 13.4 18.1C12.28 18.5 11.16 17.94 10.5 17.28C11.69 17 12.4 16.12 12.61 15.23C12.78 14.43 12.46 13.77 12.33 13C12.21 12.26 12.23 11.63 12.5 10.94C12.69 11.32 12.89 11.7 13.13 12C13.9 13 15.11 13.44 15.37 14.8C15.41 14.94 15.43 15.08 15.43 15.23C15.46 16.05 15.1 16.95 14.5 17.5H14.5Z"
    },{
      name: "status-glue", path: "M18.14 16.7C17.23 18.21 16.08 17.73 15 17.09S12.9 15.68 12.25 16.59C11.54 17.37 12.09 18.62 12.37 19.72C12.65 20.83 12.67 21.79 10.9 22C9.5 21.81 9.58 20.65 9.81 19.42C10.04 18.19 10.4 16.89 9.5 16.43C8.78 15.95 8.28 16.78 7.65 17.6C7 18.41 6.26 19.2 5.04 18.62C3.94 17.71 4.36 17.18 4.94 16.5S6.27 14.91 5.84 13.31C5.66 12.66 4.76 12.81 3.87 12.79C3 12.77 2.12 12.59 2.03 11.29C1.96 10.5 2.55 10.18 3.16 9.93C3.78 9.68 4.41 9.5 4.42 8.87C4.45 8.26 4.04 7.83 3.78 7.38S3.41 6.46 4.03 5.76C5.08 4.9 5.92 5.63 6.76 6.42S8.43 8.04 9.46 7.39C10.28 6.85 9.53 5.9 8.95 4.97S7.96 3.15 9.46 2.74C10.76 2.38 11.26 3.27 11.71 4.3C12.17 5.33 12.57 6.5 13.67 6.71C15.24 7 16.38 5.16 17.47 3.7S19.63 1.15 21 2.95C22.5 4.84 21.07 5.72 19.4 6.5C17.73 7.23 15.81 7.87 16.27 9.28C16.54 10.1 17.42 9.65 18.35 9.34C19.27 9.03 20.26 8.86 20.74 10.27C21.25 11.76 20.04 12.1 18.68 12.24C17.32 12.38 15.8 12.32 15.7 13C15.59 13.71 16.5 14 17.29 14.42C18.08 14.85 18.75 15.42 18.14 16.7M20.5 19C19.55 19 19.06 18.26 19.06 17.5C19.06 16.74 19.54 16 20.5 16C21.5 16 22 16.74 22 17.5C22 18.26 21.5 19 20.5 19Z"
    },{
      name: "status-target", path: "M11,2V4.07C7.38,4.53 4.53,7.38 4.07,11H2V13H4.07C4.53,16.62 7.38,19.47 11,19.93V22H13V19.93C16.62,19.47 19.47,16.62 19.93,13H22V11H19.93C19.47,7.38 16.62,4.53 13,4.07V2M11,6.08V8H13V6.09C15.5,6.5 17.5,8.5 17.92,11H16V13H17.91C17.5,15.5 15.5,17.5 13,17.92V16H11V17.91C8.5,17.5 6.5,15.5 6.08,13H8V11H6.09C6.5,8.5 8.5,6.5 11,6.08M12,11A1,1 0 0,0 11,12A1,1 0 0,0 12,13A1,1 0 0,0 13,12A1,1 0 0,0 12,11Z"
    },{
      name: "sum", path: "M18,6H8.83L14.83,12L8.83,18H18V20H6V18L12,12L6,6V4H18V6Z"
    },{
      name: "transform-into", path: "M3,8H5V16H3V8M7,8H9V16H7V8M11,8H13V16H11V8M15,19.25V4.75L22.25,12L15,19.25Z"
    },{
      name: "warning", path: "M21,11C21,16.55 17.16,21.74 12,23C6.84,21.74 3,16.55 3,11V5L12,1L21,5V11M12,21C15.75,20 19,15.54 19,11.22V6.3L12,3.18L5,6.3V11.22C5,15.54 8.25,20 12,21M11,7H13V13H11V7M11,15H13V17H11V15Z"
    },{
      name: "water", path: "M18.32,8H5.67L5.23,4H18.77M12,19A3,3 0 0,1 9,16C9,14 12,10.6 12,10.6C12,10.6 15,14 15,16A3,3 0 0,1 12,19M3,2L5,20.23C5.13,21.23 5.97,22 7,22H17C18,22 18.87,21.23 19,20.23L21,2H3Z"
    }
  ];

  getNamed(named: string): string {
    for(let i = 0; i < this._svgList.length; i++){
        if(this._svgList[i].name === named) return this._svgList[i].path;
    }
    return '';
  }

}