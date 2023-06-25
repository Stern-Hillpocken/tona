import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { LocalStorageService } from '../shared/local-storage.service';
import { PopupService } from '../shared/popup.service';
import { Popup } from '../models/popup.model';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(
    private lsService: LocalStorageService,
    private popup: PopupService
  ) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    // On récupère le token du localStorage
    const idToken = this.lsService.getToken();

    if (idToken) {
      // Je crée le header ajouté à chaque requête HTTP envoyée vers le serveur
      const cloned = request.clone({
        headers: request.headers.set(
          "Authorization",
          "Bearer " + idToken
        )
      });

      return this.mapStream(cloned, next);
    } else {
      return  this.mapStream(request, next);
       
    }
  }

  mapStream(request: HttpRequest<unknown>, next: HttpHandler):  Observable<HttpEvent<unknown>> {
    return next.handle(request)
    .pipe(
      tap(incomingRequest => {
        // Statut 200
        if (incomingRequest instanceof HttpResponse) {
            //console.log(incomingRequest)
        }
      }),
      // Statut 400
      catchError((err: HttpErrorResponse) => {
        if(err.error.error_message != undefined){
          this.popup.add(new Popup(err.error.error_message, "error"));
        }
        console.log(err)
        return throwError(() => new Error("Une erreur est survenue"));
      })
    )
  }
}

