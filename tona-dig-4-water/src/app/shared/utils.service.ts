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
}