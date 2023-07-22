import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

    private readonly _baseUrl: string = 'http://localhost:8080/api/v1/';

    getBaseUrl(): string {
        return this._baseUrl;
    }

    difficultyToString(value: number): string {
      let contents: string = "?";
      if(value === 1){
        contents = "Facile";
      }else if(value === 2){
        contents = "Moyen";
      }else if(value === 3){
        contents = "Difficile";
      }
      return contents;
    }

    isZoneAdjacent(selectedRoom: any, currentRoom: any): boolean {
      if(currentRoom === "hoist" && (selectedRoom === "hold" || selectedRoom === "extractor")){
        return true;
      }else if(currentRoom === "hold" && (selectedRoom === "hoist" || selectedRoom === "extractor" || selectedRoom === "armory")){
        return true;
      }else if(currentRoom === "extractor" && (selectedRoom === "porthole" || selectedRoom === "hold" || selectedRoom === "hoist")){
        return true;
      }else if(currentRoom === "armory" && (selectedRoom === "hold" || selectedRoom === "porthole" || selectedRoom === "drill")){
        return true;
      }else if(currentRoom === "porthole" && (selectedRoom === "drill" || selectedRoom === "armory" || selectedRoom === "extractor")){
        return true;
      }else if(currentRoom === "drill" && (selectedRoom === "armory" || selectedRoom === "porthole")){
        return true;
      }else{
        return false;
      }
    }
}