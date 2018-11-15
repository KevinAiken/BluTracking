import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {Log} from './log';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class LogService {
	private logUrl = 'http://localhost:8080/logs';

  constructor(private http: HttpClient) { }

  getLogs (): Observable<Log[]> {
    return this.http.get<Log[]>(this.logUrl)
      .pipe(
        tap(logs => this.log('fetched logs')),
        catchError(this.handleError('getLogs', []))
      );
  }

  getAssetLogs (assetID: string): Observable<Log[]> {
    return this.http.get<Log[]>(this.logUrl, { params: {deviceID: assetID}})
      .pipe(
        tap(logs => this.log('fetched asset logs')),
        catchError(this.handleError('getAssetLogs', []))
      );
  }
  
  isMissing (assetID: string): void{
	  const url = `$(this.logUrl)/deviceChance/$(deviceID)`;
	  this.http.get<void>(url, { params : {deviceID: assetID}}).
	  pipe(
        tap(logs => this.log('check missing')),
        catchError(this.handleError('isMissing', []))
    );
  }

  /** Handle failed Http request and let app continue
   * @param operation - name of op that failed
   * @param result - optional result to return as observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error)
      return of(result as T);
    };
  }
  /** Log a message */
  private log(message: string) {
    console.info(message);
  }
}
