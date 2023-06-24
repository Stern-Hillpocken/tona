import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

    private readonly _baseUrl: string = 'http://localhost:8080/api/v1/';

    getBaseUrl(): string {
        return this._baseUrl;
    }
}